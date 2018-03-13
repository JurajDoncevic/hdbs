package com.juraj.hdbs.connectionManagement.credentials;

import com.juraj.hdbs.connectionManagement.factory.ConnectionFactory;

/**
 * Created by Juraj on 12.11.2017..
 */
public class DBCredentialsPostgres implements DBCredentials {

    private String serverURL;
    private String dbName;
    private String username;
    private String password;

    public DBCredentialsPostgres(String serverURL, String dbName, String username, String password){

        this.serverURL = serverURL;
        this.dbName = dbName;
        this.username = username;
        this.password = password;

    }

    @Override
    public boolean isValid(){

        try{
            ConnectionFactory.createPostgresDataContext(serverURL, dbName, username, password);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public String getServerURL() {
        return serverURL;
    }

    @Override
    public String getDbName() {
        return dbName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
