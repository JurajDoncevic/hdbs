package com.juraj.hdbs.metadataDb;

import com.juraj.hdbs.metadataDb.Services.*;

import java.sql.*;


/** Service class to access a SQLite metadata database
 * @author Juraj
 */
public class MetadataDb {

    private String connectionUrl;
    private GlobalRelationshipService globalRelationshipService;
    private ConnectionService connectionService;
    private TypeService typeService;
    private TypeCompatibilityService typeCompatibilityService;


    /** Constructor
     * @param url Path to SQLite database file
     * @throws Exception Thrown in case of unsuccessful test connection establishment
     */
    public MetadataDb(String url) throws Exception{

        connectionUrl = "jdbc:sqlite:" + url;
        if(!isConnectionOK(connectionUrl))
            throw new Exception("Connection with metadata DB could not be established!");

        globalRelationshipService = new GlobalRelationshipService(connectionUrl);
        connectionService = new ConnectionService(connectionUrl);
        typeService = new TypeService(connectionUrl);
        typeCompatibilityService = new TypeCompatibilityService(connectionUrl);

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

    /** Gets a ConnectionService for this metadata database
     * @return A ConnectionService object
     */
    public ConnectionService getConnectionService() {
        return connectionService;
    }

    /** Gets a TypeService for this metadata database
     * @return A TypeService object
     */
    public TypeService getTypeService() {
        return typeService;
    }

    /** Gets a TypeCompatibilityService for this metadata database
     * @return A TypeCompatibilityService object
     */
    public TypeCompatibilityService getTypeCompatibilityService() {
        return typeCompatibilityService;
    }
}
