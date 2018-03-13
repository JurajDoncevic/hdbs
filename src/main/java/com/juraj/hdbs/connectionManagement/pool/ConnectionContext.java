package com.juraj.hdbs.connectionManagement.pool;

import org.apache.metamodel.jdbc.JdbcDataContext;

/**
 * Created by Juraj on 12.3.2018..
 */
public interface ConnectionContext {
    JdbcDataContext getDataContext();

    String getDefaultSchemaName();

}
