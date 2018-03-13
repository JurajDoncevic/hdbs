package com.juraj.hdbs.connectionManagement.pool;

import org.apache.metamodel.jdbc.JdbcDataContext;

/**
 * Created by Juraj on 12.3.2018..
 */
public class ConnectionContextMysql implements ConnectionContext {

    private JdbcDataContext dataContext;
    private String defaulSchemaName;

    public ConnectionContextMysql(JdbcDataContext dataContext, String dbName){
        this.dataContext = dataContext;
        this.defaulSchemaName = dbName;
    }

    @Override
    public JdbcDataContext getDataContext() {
        return dataContext;
    }


    @Override
    public String getDefaultSchemaName() {
        return defaulSchemaName;
    }

}
