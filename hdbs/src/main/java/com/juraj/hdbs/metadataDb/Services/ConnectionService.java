package com.juraj.hdbs.metadataDb.Services;


import com.juraj.hdbs.connection.credentials.DBCredentials;
import com.juraj.hdbs.connection.credentials.DBCredentialsMysql;
import com.juraj.hdbs.connection.credentials.DBCredentialsPostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Service class for the connection table in the metadata database
 * @author Juraj
 */
public class ConnectionService {

    private String connectionUrl;

    /** Constructor
     * @param connectionUrl Connection url in format of jdbc:sqlite:path_to_db
     */
    public ConnectionService(String connectionUrl){
        this.connectionUrl = connectionUrl;
    }

    /** Returns all the database credentials stored in the metadata database
     * @return List of DBCredentials
     */
    public List<DBCredentials> getAll() {

        List<DBCredentials> credentials = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute("SELECT server_url, db_name, user_name, password, db_vendor FROM connections;");

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                if (resultSet.getString("db_vendor").equals("MYSQL"))
                    credentials.add(new DBCredentialsMysql(resultSet.getString(1), resultSet.getString(2),
                                                            resultSet.getString(3), resultSet.getString(4)));
                if (resultSet.getString("db_vendor").equals("POSTGRESQL"))
                    credentials.add(new DBCredentialsPostgres(resultSet.getString(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4)));

            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return credentials;
    }

    /** Inserts new database credentials
     * @param credentials New database credentials
     */
    public void insert(DBCredentials credentials){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            String queryText = "";
            if (credentials.getClass().equals(DBCredentialsMysql.class))
                queryText = String.format("INSERT INTO connections(server_url, db_name, user_name, password, db_vendor) VALUES ('%s', '%s', '%s', '%s', '%s')", credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword(), "MYSQL");
            if (credentials.getClass().equals(DBCredentialsPostgres.class))
                queryText = String.format("INSERT INTO connections(server_url, db_name, user_name, password, db_vendor) VALUES ('%s', '%s', '%s', '%s', '%s')", credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword(), "POSTGRESQL");
            statement.execute(queryText);

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Deletes given credentials
     * @param credentials Existing database credentials
     */
    public void delete(DBCredentials credentials){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            String queryText = "";
            if (credentials.getClass().equals(DBCredentialsMysql.class))
                queryText = String.format("DELETE FROM connections WHERE server_url='%s' AND db_name='%s' AND user_name='%s' AND password='%s' AND db_vendor='%s';", credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword(), "MYSQL");
            if (credentials.getClass().equals(DBCredentialsPostgres.class))
                queryText = String.format("DELETE FROM connections WHERE server_url='%s' AND db_name='%s' AND user_name='%s' AND password='%s' AND db_vendor='%s';", credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword(), "POSTGRESQL");
            statement.execute(queryText);

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
