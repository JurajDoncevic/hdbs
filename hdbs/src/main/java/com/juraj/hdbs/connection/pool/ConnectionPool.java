package com.juraj.hdbs.connection.pool;

import com.juraj.hdbs.connection.credentials.DBCredentials;
import com.juraj.hdbs.connection.credentials.DBCredentialsMysql;
import com.juraj.hdbs.connection.credentials.DBCredentialsPostgres;
import com.juraj.hdbs.connection.factory.ConnectionFactory;
import com.juraj.hdbs.metadataDb.MetadataDb;
import com.juraj.hdbs.utils.customExceptions.ConnectionSetupException;
import com.juraj.hdbs.utils.customExceptions.PersistentConnectionRetrievalException;
import org.apache.metamodel.jdbc.JdbcDataContext;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/** Class used to keep all the open connection contexts of the HDBS
*/
public class ConnectionPool {

    private HashMap<String, ConnectionContext> connectionPool = new HashMap<>();
    private MetadataDb metadataDb;

    /** Constructor
     */
    @Deprecated
    public ConnectionPool(){

    }

    /** Constructor
     * @param metadataDb metadata databse service object to be used by the pool
     * @throws PersistentConnectionRetrievalException Thrown in case of failure to retrieve credentials from a metadata database
     */
    public ConnectionPool(MetadataDb metadataDb) throws PersistentConnectionRetrievalException {
        this.metadataDb = metadataDb;
        try {
            loadConnectionsFromPersistence();
        } catch (ConnectionSetupException e) {
            connectionPool.clear();
            this.metadataDb = null;
            throw new PersistentConnectionRetrievalException(e.getMessage());
        }
    }

    /** Helper method that loads connections from a metadata database into the connection pool
     * @throws ConnectionSetupException Thrown in case of a failure in setting up a connection
     */
    private void loadConnectionsFromPersistence() throws ConnectionSetupException {
        List<DBCredentials> credentials = metadataDb.getConnectionService().getAll();

        for (DBCredentials c : credentials){
            if (c.getClass().equals(DBCredentialsMysql.class)){
                JdbcDataContext dataContext = setupMysqlDbConnection((DBCredentialsMysql) c);
                connectionPool.put(c.getDbName(), new ConnectionContextMysql(dataContext, (DBCredentialsMysql) c));
            }
            if (c.getClass().equals(DBCredentialsPostgres.class)){
                JdbcDataContext dataContext = setupPostgresDbConnection((DBCredentialsPostgres) c);
                connectionPool.put(c.getDbName(), new ConnectionContextPostgres(dataContext, (DBCredentialsPostgres) c));
            }
        }
    }

    /** Gets a connection context from the pool with given database name
     * @param dbName Database name
     * @return ConnectionContext object; null if none exists
     */
    public ConnectionContext getFromPool(String dbName){
        return connectionPool.get(dbName);
    }

    /** Creates a connection and adds a PostgreSQL connection context to connection pool and persists the credentials in the metadata database
     * @param dbCredentials Database credentials for the database
     * @throws ConnectionSetupException on failure
     */
    public void addPostgresToPool(DBCredentialsPostgres dbCredentials) throws ConnectionSetupException {
        JdbcDataContext dataContext = null;
        dataContext = setupPostgresDbConnection(dbCredentials);
        connectionPool.put(dbCredentials.getDbName(), new ConnectionContextPostgres(dataContext, dbCredentials));
        if (metadataDb != null){

            metadataDb.getConnectionService().insert(dbCredentials);
        }
    }

    /** Creates a connection and adds a MySQL connection context to connection pool and persists the credentials in the metadata database
     * @param dbCredentials Database credentials for the database
     * @throws ConnectionSetupException on failure
     */
    public void addMysqlToPool(DBCredentialsMysql dbCredentials) throws ConnectionSetupException {
        JdbcDataContext dataContext = null;
        dataContext = setupMysqlDbConnection(dbCredentials);
        connectionPool.put(dbCredentials.getDbName(), new ConnectionContextMysql(dataContext, dbCredentials));
        if (metadataDb != null){

            metadataDb.getConnectionService().insert(dbCredentials);
        }
    }

    /** Checks if there is a database in the pool with a given name
     * @param dbName Database name
     * @return true if the database is in the pool; false if none such exists
     */
    public boolean hasDbWithName(String dbName){
        return connectionPool.containsKey("dbName");
    }


    /** Removes a connection context with a given database name from pool and metadata database
     * @param dbName Database name
     */
    public void removeFromPool(String dbName){
        DBCredentials credentials = connectionPool.get(dbName).getCredentials();
        connectionPool.get(dbName).closeConnection();
        connectionPool.remove(dbName);
        if (metadataDb != null){

            metadataDb.getConnectionService().delete(credentials);
        }
    }

    /** Gets all the database names in the connection pool
     * @return Set of String of database names
     */
    public Set<String> getConnectedDbNames(){
        return connectionPool.keySet();
    }

    /** Helper method that sets up a PostgreSQL connection
     * @param credentials Database credentials
     * @return JdbcDataContext object
     * @throws ConnectionSetupException Thrown in case of an failure
     */
    private JdbcDataContext setupPostgresDbConnection(DBCredentialsPostgres credentials) throws ConnectionSetupException {

        JdbcDataContext dataContext;

        if(credentials.isValid()) {

            try {
                dataContext = ConnectionFactory.createPostgresDataContext(credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword());
            } catch (Exception e) {
                throw new ConnectionSetupException(String.format("Connection setup to: %s/%s failed.", credentials.getServerURL(), credentials.getDbName()));
            }

            return dataContext;
        }else {
            throw new ConnectionSetupException("Credentials not valid.");
        }


    }

    /** Helper method that sets up a MySQL connection
     * @param credentials Database credentials
     * @return JdbcDataContext object
     * @throws ConnectionSetupException Thrown in case of an failure
     */
    private JdbcDataContext setupMysqlDbConnection(DBCredentialsMysql credentials) throws ConnectionSetupException {

        JdbcDataContext dataContext;

        if(credentials.isValid()) {

            try {
                dataContext = ConnectionFactory.createMysqlDataContext(credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword());
            } catch (Exception e) {
                throw new ConnectionSetupException(String.format("Connection setup to: %s/%s failed.", credentials.getServerURL(), credentials.getDbName()));
            }

            return dataContext;


        }else {
            throw new ConnectionSetupException("Credentials not valid");
        }


    }

}
