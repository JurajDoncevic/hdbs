package com.juraj.hdbs.connectionManagement.pool;

import org.apache.metamodel.jdbc.JdbcDataContext;

/**
 * Created by Juraj on 12.3.2018..
 */
public class ConnectionContextPostgres implements ConnectionContext {

    private JdbcDataContext dataContext;
    private String defaulSchemaName;

    public ConnectionContextPostgres(JdbcDataContext dataContext){
        this.dataContext = dataContext;
        this.defaulSchemaName = "public";
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
