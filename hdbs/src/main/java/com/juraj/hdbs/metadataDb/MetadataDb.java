package com.juraj.hdbs.metadataDb;

import com.juraj.hdbs.metadataDb.Services.*;

import java.sql.*;


/** Service class to access a SQLite metadata database
 * @author Juraj
 */
public class MetadataDb {

    private String connectionUrl;
    private GlobalRelationshipService globalRelationshipService;
    private MysqlTypesService mysqlTypesService;
    private PostgresTypesService postgresTypesService;
    private PostgresMysqlTypeCompatibilityService postgresMysqlTypeCompatibilityService;
    private MysqlReverseTypeCompatibilityService mysqlReverseTypeCompatibilityService;
    private PostgresReverseTypeCompatibilityServie postgresReverseTypeCompatibilityServie;
    private ConnectionService connectionService;


    /** Constructor
     * @param url Path to SQLite database file
     * @throws Exception Thrown in case of unsuccessful test connection establishment
     */
    public MetadataDb(String url) throws Exception{

        connectionUrl = "jdbc:sqlite:" + url;
        if(!isConnectionOK(connectionUrl))
            throw new Exception("Connection with metadata DB could not be established!");

        globalRelationshipService = new GlobalRelationshipService(connectionUrl);
        mysqlTypesService = new MysqlTypesService(connectionUrl);
        postgresTypesService = new PostgresTypesService(connectionUrl);
        postgresMysqlTypeCompatibilityService = new PostgresMysqlTypeCompatibilityService(connectionUrl);
        mysqlReverseTypeCompatibilityService = new MysqlReverseTypeCompatibilityService(connectionUrl);
        postgresReverseTypeCompatibilityServie = new PostgresReverseTypeCompatibilityServie(connectionUrl);
        connectionService = new ConnectionService(connectionUrl);

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

    /** Gets a GlobalRelationshipService for this metadata database
     * @return A GlobalRelationshipService object
     */
    public GlobalRelationshipService getGlobalRelationshipService() {
        return globalRelationshipService;
    }

    /** Gets a MysqlTypesService for this metadata database
     * @return A MysqlTypesService object
     */
    public MysqlTypesService getMysqlTypesService() {
        return mysqlTypesService;
    }

    /** Gets a PostgresTypesService for this metadata database
     * @return A PostgresTypesService object
     */
    public PostgresTypesService getPostgresTypesService() {
        return postgresTypesService;
    }

    /** Gets a PostgresMysqlTypeCompatibilityService for this metadata database
     * @return A PostgresMysqlTypeCompatibilityService object
     */
    public PostgresMysqlTypeCompatibilityService getPostgresMysqlTypeCompatibilityService() {
        return postgresMysqlTypeCompatibilityService;
    }

    /** Gets a MysqlReverseTypeCompatibilityService for this metadata database
     * @return A MysqlReverseTypeCompatibilityService object
     */
    public MysqlReverseTypeCompatibilityService getMysqlReverseTypeCompatibilityService() {
        return mysqlReverseTypeCompatibilityService;
    }

    /** Gets a PostgresReverseTypeCompatibilityServie for this metadata database
     * @return A PostgresReverseTypeCompatibilityServie object
     */
    public PostgresReverseTypeCompatibilityServie getPostgresReverseTypeCompatibilityServie() {
        return postgresReverseTypeCompatibilityServie;
    }

    public ConnectionService getConnectionService() {
        return connectionService;
    }
}
