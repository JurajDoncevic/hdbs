package com.juraj.hdbs.connection.credentials;

import com.juraj.hdbs.connection.factory.ConnectionFactory;

/** Class for database log in credentials for a PostgreSQL database
 * @author Juraj
 */
public class DBCredentialsPostgres implements DBCredentials {

    private String serverURL;
    private String dbName;
    private String username;
    private String password;

    /** Constructor
     * @param serverURL URL to server
     * @param dbName Database name
     * @param username Username
     * @param password Password
     */
    public DBCredentialsPostgres(String serverURL, String dbName, String username, String password){

        this.serverURL = serverURL;
        this.dbName = dbName;
        this.username = username;
        this.password = password;

    }

    /** Checks if credentials are valid
     * @return true on validity; false on invalidity
     */
    @Override
    public boolean isValid(){

        try{
            ConnectionFactory.createPostgresDataContext(serverURL, dbName, username, password);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    /** Gets the server url
     * @return String of server url
     */
    @Override
    public String getServerURL() {
        return serverURL;
    }

    /** Gets the database name
     * @return String of database name
     */
    @Override
    public String getDbName() {
        return dbName;
    }

    /** Gets username
     * @return String of username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /** Gets password
     * @return String of password
     */
    @Override
    public String getPassword() {
        return password;
    }
}
