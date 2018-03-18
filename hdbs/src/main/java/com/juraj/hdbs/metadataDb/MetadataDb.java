package com.juraj.hdbs.metadataDb;

import com.juraj.hdbs.metadataDb.DAO.*;

import java.sql.*;


/** Service class to access a SQLite metadata database
 * @author Juraj
 */
public class MetadataDb {

    private String connectionUrl;
    private GlobalRelationshipDAO globalRelationshipDAO;
    private MysqlTypesDAO mysqlTypesDAO;
    private PostgresTypesDAO postgresTypesDAO;
    private PostgresMysqlTypeCompatibilityDAO postgresMysqlTypeCompatibilityDAO;
    private MysqlReverseTypeCompatibilityDAO mysqlReverseTypeCompatibilityDAO;
    private PostgresReverseTypeCompatibilityDAO postgresReverseTypeCompatibilityDAO;


    /** Constructor
     * @param url Path to SQLite database file
     * @throws Exception Thrown in case of unsuccessful test connection establishment
     */
    public MetadataDb(String url) throws Exception{

        connectionUrl = "jdbc:sqlite:" + url;
        if(!isConnectionOK(connectionUrl))
            throw new Exception("Connection with metadata DB could not be established!");

        globalRelationshipDAO = new GlobalRelationshipDAO(connectionUrl);
        mysqlTypesDAO = new MysqlTypesDAO(connectionUrl);
        postgresTypesDAO = new PostgresTypesDAO(connectionUrl);
        postgresMysqlTypeCompatibilityDAO = new PostgresMysqlTypeCompatibilityDAO(connectionUrl);
        mysqlReverseTypeCompatibilityDAO = new MysqlReverseTypeCompatibilityDAO(connectionUrl);
        postgresReverseTypeCompatibilityDAO = new PostgresReverseTypeCompatibilityDAO(connectionUrl);

    }

    /** Tests connection for given connection url
     * @param connectionUrl Url of format: jdbc:sqlite:path_to_db_file
     * @return true on successful connect; false on failure
     */
    private boolean isConnectionOK(String connectionUrl) {
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Gets connection url
     * @return String of connection url
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }

    /** Gets a GlobalRelationshipDAO for this metadata database
     * @return A GlobalRelationshipDAO object
     */
    public GlobalRelationshipDAO getGlobalRelationshipDAO() {
        return globalRelationshipDAO;
    }

    /** Gets a MysqlTypesDAO for this metadata database
     * @return A MysqlTypesDAO object
     */
    public MysqlTypesDAO getMysqlTypesDAO() {
        return mysqlTypesDAO;
    }

    /** Gets a PostgresTypesDAO for this metadata database
     * @return A PostgresTypesDAO object
     */
    public PostgresTypesDAO getPostgresTypesDAO() {
        return postgresTypesDAO;
    }

    /** Gets a PostgresMysqlTypeCompatibilityDAO for this metadata database
     * @return A PostgresMysqlTypeCompatibilityDAO object
     */
    public PostgresMysqlTypeCompatibilityDAO getPostgresMysqlTypeCompatibilityDAO() {
        return postgresMysqlTypeCompatibilityDAO;
    }

    /** Gets a MysqlReverseTypeCompatibilityDAO for this metadata database
     * @return A MysqlReverseTypeCompatibilityDAO object
     */
    public MysqlReverseTypeCompatibilityDAO getMysqlReverseTypeCompatibilityDAO() {
        return mysqlReverseTypeCompatibilityDAO;
    }

    /** Gets a PostgresReverseTypeCompatibilityDAO for this metadata database
     * @return A PostgresReverseTypeCompatibilityDAO object
     */
    public PostgresReverseTypeCompatibilityDAO getPostgresReverseTypeCompatibilityDAO() {
        return postgresReverseTypeCompatibilityDAO;
    }
}
