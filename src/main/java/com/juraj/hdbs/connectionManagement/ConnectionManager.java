package com.juraj.hdbs.connectionManagement;

import com.juraj.hdbs.connectionManagement.credentials.DBCredentials;
import com.juraj.hdbs.connectionManagement.credentials.DBCredentialsMysql;
import com.juraj.hdbs.connectionManagement.credentials.DBCredentialsPostgres;
import com.juraj.hdbs.connectionManagement.factory.ConnectionFactory;
import com.juraj.hdbs.connectionManagement.pool.ConnectionContext;
import com.juraj.hdbs.connectionManagement.pool.ConnectionPool;
import org.apache.metamodel.jdbc.JdbcDataContext;

import java.util.HashMap;

/**
 * Created by Juraj on 13.3.2018..
 */
public class ConnectionManager {

    public static HashMap<String, DBCredentials> credentialsPool = new HashMap<>();

    public static boolean addDbConnection(DBCredentials credentials){

        ConnectionContext connectionContext;
        JdbcDataContext dataContext;

        if(credentials.isValid()) {

            if (credentials.getClass() == DBCredentialsPostgres.class) {
                try {
                    dataContext = ConnectionFactory.createPostgresDataContext(credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                ConnectionPool.addPostgresToPool(credentials.getDbName(), dataContext);

            }
            if (credentials.getClass() == DBCredentialsMysql.class) {
                try {
                    dataContext = ConnectionFactory.createMysqlDataContext(credentials.getServerURL(), credentials.getDbName(), credentials.getUsername(), credentials.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                ConnectionPool.addMysqlToPool(credentials.getDbName(), dataContext);
            }
        }
        credentialsPool.put(credentials.getDbName(), credentials);
        return true;
    }

    public static void removeDbConnection(String dbName){

        if(credentialsPool.containsKey(dbName)) {
            ConnectionPool.removeFromPool(dbName);
            credentialsPool.remove(dbName);
        }

    }

    public static void restartDbConnection(String dbName){

        if(credentialsPool.containsKey((dbName))) {
            DBCredentials credentials = credentialsPool.get(dbName);
            credentialsPool.remove(dbName);
            removeDbConnection(dbName);
            addDbConnection(credentials);
        }
    }
}
