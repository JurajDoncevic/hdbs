package com.juraj.hdbs.metadataDb.Services;

import com.juraj.hdbs.utils.DBVendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Service class used to manage recognized data types for supported databases in a metadata database
 * @author Juraj
 */
public class TypeService {

    private String connectionUrl;

    public TypeService(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }


    /** Determines if a given data type is allowed to be used in aggregation functions
     * @param typeName Data type name
     * @param dbVendor Database vendor of data type
     * @return True if allowed; else False
     */
    public boolean isAggregationAllowed(String typeName, DBVendor dbVendor){

        boolean agg_allowed = false;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT aggregation_allowed" +
                                            " FROM db_types, db_vendors" +
                                            " WHERE db_types.vendor_id = db_vendors.vendor_id" +
                                            " AND type_name = '%s'" +
                                            " AND vendor_name = '%s';",  typeName, dbVendor.toString()));

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

    /** Gets all data types that are allowed to be used in aggregation functions
     * @return List of typeNames
     */
    public List<String> getAggregationAllowedTypes(){
        List<String> agg_allowed = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT type_name FROM db_types WHERE aggregation_allowed = %s;",  1));

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

    /** Determines if a data type exits in the metadata database
     * @param typeName Data type name
     * @param dbVendor Database Vendor of data type
     * @return True if exists; else False
     */
    public boolean typeExists(String typeName, DBVendor dbVendor){

        boolean typeExists = false;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT COUNT(type_id)" +
                    " FROM db_types, db_vendors" +
                    " WHERE db_types.vendor_id = db_vendors.vendor_id" +
                    " AND type_name = '%s'" +
                    " AND vendor_name = '%s';",  typeName, dbVendor.toString()));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                typeExists = resultSet.getInt(1) > 0;

            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeExists;
    }
}
