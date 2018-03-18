package com.juraj.hdbs.metadataDb.DAO;


import com.juraj.hdbs.metadataDb.Models.PostgresMysqlTypeCompatibilityModel;
import javafx.util.Pair;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data acces class used to manage data type compatibilities for a PostgreSQL and MySQL database stored in a metadata database
 */
public class PostgresMysqlTypeCompatibilityDAO {

    private String connectionUrl;

    /** Constructor
     * @param connectionUrl Connection url in format of jdbc:sqlite:path_to_db
     */
    public PostgresMysqlTypeCompatibilityDAO(String connectionUrl){
        this.connectionUrl = connectionUrl;
    }

    /** Gets all data type compatibilities for PostgreSQL and MySQL
     * @return List of PostgresMysqlTypeCompatibilityModel
     */
    public List<PostgresMysqlTypeCompatibilityModel> getAllCompatibilites() {

        List<PostgresMysqlTypeCompatibilityModel> compatibilities = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute("SELECT pg_type_name, mysql_type_name FROM pg_mysql_type_compatibility;");

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                compatibilities.add(new PostgresMysqlTypeCompatibilityModel(resultSet.getString(1), resultSet.getString(2)));

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compatibilities;
    }

    /** Gets all MySQL data type names compatible with a given PostgreSQL data type name
     * @param postgresType PostgreSQL data type name
     * @return List of String of MySQL data type names
     */
    public List<String> getAllMysqlForPostgresTypeCompatibilities(String postgresType){
        List<String> mysqlTypes = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT mysql_type_name FROM pg_mysql_type_compatibility WHERE pg_type_name = '%s';", postgresType));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                mysqlTypes.add(resultSet.getString(1));

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mysqlTypes;
    }

    /** Gets all PostgreSQL data type names compatible with a given MySQL data type name
     * @param mysqlType MySQL data type name
     * @return List of String of PostgreSQL data type names
     */
    public List<String> getAllPostgresForMysqlTypeCompatibilities(String mysqlType){
        List<String> postgresTypes = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT pg_type_name FROM pg_mysql_type_compatibility WHERE mysql_type_name = '%s';", mysqlType));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                postgresTypes.add(resultSet.getString(1));

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postgresTypes;
    }

    /** Gets a compatibility for two specified data type names
     * @param postgresType PostgreSQL data type name
     * @param mysqlType MySQL data type name
     * @return PostgresMySQLTypeCompatibilityModel object; returns null on failure
     */
    public PostgresMysqlTypeCompatibilityModel getCompatibility(String postgresType, String mysqlType){

        PostgresMysqlTypeCompatibilityModel compatibility = null;
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();

            statement.execute(String.format("SELECT pg_type_name, mysql_type_name FROM pg_mysql_type_compatibility WHERE pg_type_name = '%s' AND mysql_type_name'%s');", postgresType, mysqlType));

            ResultSet resultSet = statement.getResultSet();

            if(resultSet.next()){
                compatibility = new PostgresMysqlTypeCompatibilityModel(resultSet.getString(1), resultSet.getString(2));
            }

            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return compatibility;
    }

    /** Gets all data type names compatible with a provided data type name
     * @param typeName A data type name from MySQL or PostgreSQL
     * @return List of String of data type names
     */
    public List<String> getAllTypesCompatibleWithType(String typeName){
        List<String> types = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT mysql_type_name " +
                                            "FROM pg_mysql_type_compatibility " +
                                            "WHERE pg_type_name = '%s' " +
                                            "UNION " +
                                            "SELECT pg_type_name " +
                                            "FROM pg_mysql_type_compatibility " +
                                            "WHERE mysql_type_name = '%s';", typeName, typeName));

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

    /** Inserts a data type compatibility
     * @param compatibility A compatibility to be inserted
     * @return true on success; false on failure
     */
    public boolean insertCompatibility(PostgresMysqlTypeCompatibilityModel compatibility){
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();

            statement.execute(String.format("INSERT INTO pg_mysql_type_compatibility(pg_type_name, mysql_type_name) VALUES('%s','%s');", compatibility.getPostgresTypeName(), compatibility.getMysqlTypeName()));


            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Deletes a data type compatibility
     * @param compatibility A compatibility to be deleted
     * @return true on success; false on failure
     */
    public boolean deleteCompatibility(PostgresMysqlTypeCompatibilityModel compatibility){
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();

            statement.execute(String.format("DELETE FROM pg_mysql_type_compatibility WHERE pg_type_name = '%s' AND mysql_type_name = '%s';)", compatibility.getPostgresTypeName(), compatibility.getMysqlTypeName()));

            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
