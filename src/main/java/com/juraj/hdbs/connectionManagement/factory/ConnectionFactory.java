package com.juraj.hdbs.connectionManagement.factory;

import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.jdbc.JdbcDataContextFactory;

/**
 * Created by Juraj on 12.11.2017..
 */


public class ConnectionFactory {

    public static JdbcDataContext createPostgresDataContext(String serverAddress, String dbName,
                                                        String username, String password) throws Exception{

        String url = String.format("jdbc:postgresql://%s/%s", serverAddress, dbName);

        DataContextPropertiesImpl properties = new DataContextPropertiesImpl();
        properties.put("type", "jdbc");
        properties.put("url", url);
        properties.put("username", username);
        properties.put("password", password);
        properties.put("driver-class", "org.postgres.Driver");



        JdbcDataContext dataContext = (JdbcDataContext) (new JdbcDataContextFactory()).create(properties, null);


        return dataContext;
    }

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
