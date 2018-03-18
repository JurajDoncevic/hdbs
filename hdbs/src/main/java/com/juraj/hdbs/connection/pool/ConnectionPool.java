package com.juraj.hdbs.connection.pool;

import com.juraj.hdbs.connection.credentials.DBCredentialsMysql;
import com.juraj.hdbs.connection.credentials.DBCredentialsPostgres;
import com.juraj.hdbs.connection.factory.ConnectionFactory;
import org.apache.metamodel.jdbc.JdbcDataContext;
import java.util.HashMap;
import java.util.Set;

/** Class used to keep all the open connection contexts of the HDBS
*/
public class ConnectionPool {

    private HashMap<String, ConnectionContext> connectionPool = new HashMap<>();

    /** Constructor
     */
    public ConnectionPool(){

    }

    /** Gets a connection context from the pool with given database name
     * @param dbName Database name
     * @return ConnectionContext object; null if none exists
     */
    public ConnectionContext getFromPool(String dbName){
        return connectionPool.get(dbName);
    }

    /** Creates a connection and adds a PostgreSQL connection context to connection pool
     * @param dbCredentials Database credentials for the database
     * @return true on success; false on failure
     */
    public boolean addPostgresToPool(DBCredentialsPostgres dbCredentials) {
        JdbcDataContext dataContext = null;
        try {
            dataContext = setupPostgresDbConnection(dbCredentials);
            connectionPool.put(dbCredentials.getDbName(), new ConnectionContextPostgres(dataContext, dbCredentials));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /** Creates a connection and adds a MySQL connection context to connection pool
     * @param dbCredentials Database credentials for the database
     * @return true on success; false on failure
     */
    public boolean addMysqlToPool(DBCredentialsMysql dbCredentials) {
        JdbcDataContext dataContext = null;
        try {
            dataContext = setupMysqlDbConnection(dbCredentials);
            connectionPool.put(dbCredentials.getDbName(), new ConnectionContextMysql(dataContext, dbCredentials));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** Checks if there is a database in the pool with a given name
     * @param dbName Database name
     * @return true if the database is in the pool; false if none such exists
     */
    public boolean hasDbWithName(String dbName){
        return connectionPool.containsKey("dbName");
    }


    /** Removes a connection context with a given database name
     * @param dbName Database name
     */
    public void removeFromPool(String dbName){
        connectionPool.get(dbName).closeConnection();
        connectionPool.remove(dbName);
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
     * @throws Exception Thrown in case of an failure
     */
    private JdbcDataContext setupPostgresDbConnection(DBCredentialsPostgres credentials) throws Exception {

        JdbcDataContext dataContext;

        if(credentials.isValid()) {

            dataContext = ConnectionFactory.createPostgresDataContext(credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword());

            return dataContext;
        }else {
            throw new Exception("Credentials not valid");
        }


    }

    /** Helper method that sets up a MySQL connection
     * @param credentials Database credentials
     * @return JdbcDataContext object
     * @throws Exception Thrown in case of an failure
     */
    private JdbcDataContext setupMysqlDbConnection(DBCredentialsMysql credentials) throws Exception {

        JdbcDataContext dataContext;

        if(credentials.isValid()) {

            dataContext = ConnectionFactory.createMysqlDataContext(credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword());

            return dataContext;


        }else {
            throw new Exception("Credentials not valid");
        }


    }

}
