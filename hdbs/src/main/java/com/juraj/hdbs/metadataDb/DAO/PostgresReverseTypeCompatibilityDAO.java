package com.juraj.hdbs.metadataDb.DAO;

import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data access class used to manage data type compatibilities for a PostgreSQL database stored in a metadata database
 * @author Juraj
 */
public class PostgresReverseTypeCompatibilityDAO {

    private String connectionUrl;

    /** Constructor
     * @param connectionUrl Connection url in format of jdbc:sqlite:path_to_db
     */
    public PostgresReverseTypeCompatibilityDAO(String connectionUrl){
        this.connectionUrl = connectionUrl;
    }

    /** Gets all compatibilities
     * @return List of Pair of (String, String) with data type name compatibility pairs
     */
    public List<Pair<String, String>> getAllCompatibilities(){

        List<Pair<String, String>> compatibilities = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute("SELECT * FROM 'pg_reverse_type_compatibility';");

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                compatibilities.add(new Pair<>(resultSet.getString(1), resultSet.getString(2)));

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compatibilities;
    }

    /** Gets all compatible data type names for a given data type name
     * @param typeName Data type name
     * @return List of String with data type names
     */
    public List<String> getCompatibleTypesForType(String typeName){

        List<String> types = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT type_name2 " +
                                            "FROM pg_reverse_type_compatibility " +
                                            "WHERE type_name1 = '%s' " +
                                            "UNION " +
                                            "SELECT type_name1 " +
                                            "FROM pg_reverse_type_compatibility " +
                                            "WHERE type_name2 = '%s';",  typeName, typeName));

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

    /** Inserts a compatibility for two given data type names
     * @param typeName1 data type name
     * @param typeName2 data type name
     * @return true on success; false on failure
     */
    public boolean insertCompatibility(String typeName1, String typeName2){

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("INSERT INTO pg_reverse_type_compatibility(type_name1, type_name2) VALUES('%s', '%s');", typeName1, typeName2));

            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Deletes a compatibility for two given data type names
     * @param typeName1 data type name
     * @param typeName2 data type name
     * @return true on success; false on failure
     */
    public boolean deleteCompatibility(String typeName1, String typeName2){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("DELETE FROM pg_reverse_type_compatibility WHERE type_name1 = '%s' AND type_name2 = '%s' OR type_name1 = '%s' AND type_name2 = '%s';", typeName1, typeName2, typeName2, typeName1));

            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
