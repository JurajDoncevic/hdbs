package com.juraj.hdbs;

import com.juraj.hdbs.Utils.DBVendor;
import com.juraj.hdbs.connection.credentials.DBCredentialsMysql;
import com.juraj.hdbs.connection.credentials.DBCredentialsPostgres;
import com.juraj.hdbs.connection.pool.ConnectionPool;
import com.juraj.hdbs.metadataDb.MetadataDb;
import com.juraj.hdbs.schemaManagement.GlobalSchemaToXMLConverter;
import com.juraj.hdbs.schemaManagement.SchemaLoader;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalRelationship;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalSchema;

import java.util.Set;


/** Class for user access to the HDBS
 * @author Juraj
 */
public class HeterogeneousDatabaseSystem {

    private GlobalSchema currentGlobalSchema;
    private ConnectionPool connectionPool;
    private String metadataDbUrl;
    private MetadataDb metadataDb;


    /** Constructor
     * @param metadataDbUrl Path to the SQLite database file to store the metadata
     * @throws Exception Thrown in case of failure
     */
    public HeterogeneousDatabaseSystem(String metadataDbUrl) throws Exception {
        connectionPool = new ConnectionPool();
        currentGlobalSchema = null;
        this.metadataDbUrl = metadataDbUrl;
        metadataDb = new MetadataDb(metadataDbUrl);

    }

    /** Adds a database connection to pool
     * @param serverURL Database server url
     * @param dbName Database name
     * @param username Username
     * @param password Password
     * @param vendor Enum value of supported vendor
     * @return true on success; false on failure
     */
    public boolean addConnectionToPool(String serverURL, String dbName, String username, String password, DBVendor vendor) {
        boolean successfulAdd = false;
        switch(vendor){
            case POSTGRESQL:successfulAdd = connectionPool.addPostgresToPool(new DBCredentialsPostgres(serverURL, dbName, username, password));
                            break;
            case MYSQL:successfulAdd = connectionPool.addMysqlToPool(new DBCredentialsMysql(serverURL, dbName, username, password));
                        break;
            default:break;
        }

        if(successfulAdd){
            currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);
        }

        return successfulAdd;

    }

    public void removeConnectionFromPool(String dbName){
        connectionPool.removeFromPool(dbName);
        metadataDb.getGlobalRelationshipDAO().deleteRelationshipsForDB(dbName);
        currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);
    }

    /** Gets all the connected databases' names
     * @return Set of String of names
     */
    public Set<String> getConnectedDbNames(){
        return connectionPool.getConnectedDbNames();
    }

    /** Gets the global schema in a XML document content
     * @return String of XML document content
     */
    public String getGlobalSchemaXML(){
        return GlobalSchemaToXMLConverter.convert(currentGlobalSchema);
    }

    /** Adds a global relationship to the system
     * @param primaryKeyColumnId Column id for the primary key column
     * @param foreignKeyColumnId Column id for the foreign key column
     * @return true on success; false on failure
     */
    public boolean addGlobalRelationship(String primaryKeyColumnId, String foreignKeyColumnId){
        try {
            GlobalRelationship globalRelationship = new GlobalRelationship(primaryKeyColumnId, foreignKeyColumnId);

            //this throws exception if such columns do not exist
            String typeName1 = currentGlobalSchema.getDatabaseByName(globalRelationship.getPrimaryKeyDB())
                    .getTableByName(globalRelationship.getPrimaryKeyTable())
                    .getColumnByName(globalRelationship.getPrimaryKeyColumn()).getDataType();
            String typeName2 = currentGlobalSchema.getDatabaseByName(globalRelationship.getForeignKeyDB())
                    .getTableByName(globalRelationship.getForeignKeyTable())
                    .getColumnByName(globalRelationship.getForeignKeyColumn()).getDataType();

            DBVendor pkDbVendor = currentGlobalSchema.getDatabaseByName(globalRelationship.getPrimaryKeyDB()).getDbVendor();
            DBVendor fkDbVendor = currentGlobalSchema.getDatabaseByName(globalRelationship.getForeignKeyDB()).getDbVendor();




            if(pkDbVendor == fkDbVendor){
                if(pkDbVendor == DBVendor.MYSQL){
                    if(metadataDb.getMysqlReverseTypeCompatibilityDAO().getCompatibleTypesForType(typeName1).contains(typeName2)){
                        metadataDb.getGlobalRelationshipDAO().insert(globalRelationship);
                        //currentGlobalSchema.getGlobalRelationships().add(globalRelationship);

                    }
                }
                if(pkDbVendor == DBVendor.POSTGRESQL){
                    if(metadataDb.getPostgresReverseTypeCompatibilityDAO().getCompatibleTypesForType(typeName1).contains(typeName2)){
                        metadataDb.getGlobalRelationshipDAO().insert(globalRelationship);

                    }
                }
            }else {
                if(metadataDb.getPostgresMysqlTypeCompatibilityDAO().getAllTypesCompatibleWithType(typeName1).contains(typeName2)){
                    metadataDb.getGlobalRelationshipDAO().insert(globalRelationship);

                }
            }

            currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** Removes a global relationship from the system
     * @param primaryKeyColumnId Column id for the primary key column
     * @param foreignKeyColumnId Column id for the foreign key column
     * @return true on success; false on failure
     */
    public boolean removeGlobalRelationship(String primaryKeyColumnId, String foreignKeyColumnId){
        try {
            metadataDb.getGlobalRelationshipDAO().delete(new GlobalRelationship(primaryKeyColumnId, foreignKeyColumnId));
            currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void executeGlobalQuery(String queryText){

    }
}
