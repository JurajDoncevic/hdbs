package com.juraj.hdbs.connection.pool;

import com.juraj.hdbs.connection.credentials.DBCredentials;
import com.juraj.hdbs.connection.credentials.DBCredentialsPostgres;
import org.apache.metamodel.jdbc.JdbcDataContext;

/** Connection context for a PostgreSQL database
 * @author Juraj
 */
public class ConnectionContextPostgres implements ConnectionContext {

    private JdbcDataContext dataContext;
    private String defaultSchemaName;
    private DBCredentialsPostgres dbCredentials;

    /** Constructor
     * @param dataContext A valid data context
     * @param dbCredentials Database credentials used to create the data context
     */
    public ConnectionContextPostgres(JdbcDataContext dataContext, DBCredentialsPostgres dbCredentials){
        this.dataContext = dataContext;
        this.dbCredentials = dbCredentials;
        this.defaultSchemaName = "public";
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
