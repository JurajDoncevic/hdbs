package com.juraj.hdbs.connection.factory;

import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.jdbc.JdbcDataContextFactory;

/** Factory for creating Apache Metamodel DataContext objects
 * @author Juraj
 */
public class ConnectionFactory {

    /** Static method to create a PostgreSQL DataContext object
     * @see org.apache.metamodel.jdbc.JdbcDataContext
     * @param serverAddress Address for the database server
     * @param dbName Database name
     * @param username Username
     * @param password Password
     * @return Apache Metamodel JdbcDataContext
     * @throws Exception Thrown in case of failure;
     */
    public static JdbcDataContext createPostgresDataContext(String serverAddress, String dbName,
                                                        String username, String password) throws Exception{

        String url = String.format("jdbc:postgresql://%s/%s", serverAddress, dbName);

        DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
        properties.put("type", "jdbc");
        properties.put("url", url);
        properties.put("username", username);
        properties.put("password", password);
        properties.put("driver-class", "org.postgresql.Driver");



        JdbcDataContext dataContext = (JdbcDataContext) (new JdbcDataContextFactory()).create(properties, null);


        return dataContext;
    }

    /** Static method to create a MySQL DataContext object
     * @see org.apache.metamodel.jdbc.JdbcDataContext
     * @param serverAddress Address for the database server
     * @param dbName Database name
     * @param username Username
     * @param password Password
     * @return Apache Metamodel JdbcDataContext
     * @throws Exception Thrown in case of failure;
     */
    public static JdbcDataContext createMysqlDataContext(String serverAddress, String dbName,
                                                            String username, String password) throws Exception{

        String url = String.format("jdbc:mysql://%s/%s?%s", serverAddress, dbName, "useSSL=false&useLegacyDatetimeCode=false&serverTimezone=CET&nullNamePatternMatchesAll=true");


        DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
        properties.put("type", "jdbc");
        properties.put("url", url);
        properties.put("username", username);
        properties.put("password", password);
        properties.put("driver-class", "com.mysql.cj.jdbc.Driver");

        JdbcDataContext dataContext = (JdbcDataContext) (new JdbcDataContextFactory()).create(properties, null);

        return dataContext;
    }
}
