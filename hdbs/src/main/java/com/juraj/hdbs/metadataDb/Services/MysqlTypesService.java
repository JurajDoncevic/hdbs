package com.juraj.hdbs.metadataDb.Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Service class used to manage recognized data types for a MySQL database in a metadata database
 * @author Juraj
 */
public class MysqlTypesService {

    private String connectionUrl;

    /** Constructor
     * @param connectionUrl Connection url in format of jdbc:sqlite:path_to_db
     */
    public MysqlTypesService(String connectionUrl){
        this.connectionUrl = connectionUrl;
    }

    /** Gets all data type names
     * @return List of String of data type names
     */
    public List<String> getAllTypes(){

        List<String> types = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute("SELECT type_name FROM 'mysql_types';");

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                types.add(resultSet.getString(1));

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return types;
    }

    /** Gets a specified data type name - used to check if it exists
     * @param typeName Name od data type
     * @return String of data type name; null on failure
     */
    public String getType(String typeName){

        String type = null;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT type_name FROM mysql_types WHERE type_name = '%s';",  typeName));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                type = resultSet.getString(1);

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }

    /** Inserts a new data type name
     * @param typeName Name of data type to be inserted
     * @param aggregationAllowed Is aggregation allowed for type
     * @return true on success; false on failure
     */
    public boolean insertType(String typeName, boolean aggregationAllowed){

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("INSERT INTO mysql_types(type_name, aggregation_allowed) VALUES('%s', %s);", typeName, aggregationAllowed));

            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Deletes a data type name of specified name
     * @param typeName Data type name to be deleted
     * @return true on success; false on failure
     */
    public boolean deleteType(String typeName){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("DELETE FROM mysql_types WHERE type_name = '%s';", typeName));

            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Determines if a given data type is allowed to be used in aggregation functions
     * @param typeName Data type name
     * @return True or false
     */
    public boolean isAggregationAllowed(String typeName){
        boolean agg_allowed = false;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT aggregation_allowed FROM mysql_types WHERE type_name = '%s';",  typeName));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                agg_allowed = resultSet.getBoolean(1);

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agg_allowed;
    }

    /** Gets the data type names of all types that are allowed in aggregation functions
     * @return List of allowed data type names
     */
    public List<String> getAggregationAllowedTypes(){
        List<String> agg_allowed = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT type_name FROM mysql_types WHERE aggregation_allowed = %s;",  1));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                agg_allowed.add(resultSet.getString(1));

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return agg_allowed;
    }
}
