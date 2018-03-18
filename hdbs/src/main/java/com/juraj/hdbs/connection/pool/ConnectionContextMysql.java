package com.juraj.hdbs.connection.pool;

import com.juraj.hdbs.connection.credentials.DBCredentials;
import com.juraj.hdbs.connection.credentials.DBCredentialsMysql;
import org.apache.metamodel.jdbc.JdbcDataContext;

/** Connection context for a MySQL database
 * @author Juraj
 */
public class ConnectionContextMysql implements ConnectionContext {

    private JdbcDataContext dataContext;
    private String defaultSchemaName;
    private DBCredentialsMysql dbCredentials;

    /** Constructor
     * @param dataContext A valid data context
     * @param dbCredentials Database credentials used to create the data context
     */
    public ConnectionContextMysql(JdbcDataContext dataContext, DBCredentialsMysql dbCredentials){
        this.dataContext = dataContext;
        this.dbCredentials = dbCredentials;
        this.defaultSchemaName = dbCredentials.getDbName();
    }

    /** Gets the JdbcDataContext
     * @see org.apache.metamodel.jdbc.JdbcDataContext
     * @return JdbcDataContext
     */
    @Override
    public JdbcDataContext getDataContext() {
        return dataContext;
    }

    /** Gets the determined default schema name - the one to be referenced as a local schema
     * @return String of default schema name
     */
    @Override
    public String getDefaultSchemaName() {
        return defaultSchemaName;
    }

    /** Gets the database credentials for a connection
     * @return DBCredentials object
     */
    @Override
    public DBCredentials getCredentials() {
        return dbCredentials;
    }

    /** Closes the current connection
     */
    @Override
    public void closeConnection() {
        dataContext.close(dataContext.getConnection());
    }

}
