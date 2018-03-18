package com.juraj.hdbs.connection.pool;

import com.juraj.hdbs.connection.credentials.DBCredentials;
import org.apache.metamodel.jdbc.JdbcDataContext;

/** Interface for a connection context to be stored in a connection pool. It stores the JdbcDataContext, default schema name and credentials for connection.
 * @see ConnectionPool
 * @author Juraj
 */
public interface ConnectionContext {

    /** Gets the JdbcDataContext
     * @see org.apache.metamodel.jdbc.JdbcDataContext
     * @return JdbcDataContext
     */
    JdbcDataContext getDataContext();

    /** Gets the determined default schema name - the one to be referenced as a local schema
     * @return String of default schema name
     */
    String getDefaultSchemaName();

    /** Gets the database credentials for a connection
     * @return DBCredentials object
     */
    DBCredentials getCredentials();

    /** Closes the current connection
     */
    void closeConnection();

}
