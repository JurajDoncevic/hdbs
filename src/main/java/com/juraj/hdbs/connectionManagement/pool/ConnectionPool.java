package com.juraj.hdbs.connectionManagement.pool;

import org.apache.metamodel.jdbc.JdbcDataContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Juraj on 12.3.2018..
 */
public class ConnectionPool {

    private static HashMap<String, ConnectionContext> connectionPool = new HashMap<>();

    public static ConnectionContext getFromPool(String dbName){
        return connectionPool.get(dbName);
    }

    public static void addPostgresToPool(String dbName, JdbcDataContext dataContext){
        connectionPool.put(dbName, new ConnectionContextPostgres(dataContext));
    }

    public static void addMysqlToPool(String dbName, JdbcDataContext dataContext){
        connectionPool.put(dbName, new ConnectionContextMysql(dataContext, dbName));
    }

    public static boolean hasDbWithName(String dbName){
        return connectionPool.containsKey("dbName");
    }

    public static void removeFromPool(String dbName){
        ConnectionContext ctx = connectionPool.get(dbName);
        ctx.getDataContext().close(ctx.getDataContext().getConnection());
        connectionPool.remove(dbName);
    }

    public static Set<String> getConnectedDbNames(){
        return connectionPool.keySet();
    }

}
