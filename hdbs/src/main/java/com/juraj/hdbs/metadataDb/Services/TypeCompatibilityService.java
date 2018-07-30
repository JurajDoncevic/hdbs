package com.juraj.hdbs.metadataDb.Services;

import com.juraj.hdbs.utils.DBVendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj on 11.6.2018..
 */
public class TypeCompatibilityService {

    private String connectionUrl;

    public TypeCompatibilityService(String connectionUrl){
        this.connectionUrl = connectionUrl;
    }

    /** Gets all data type names compatible with a provided data type name
     * @param typeName A data type name for any supported db
     * @return List of String of data type names
     */
    public List<String> getAllTypesCompatibleWithType(String typeName){
        List<String> types = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT types1.type_name" +
                                            " FROM type_compatibility, db_types AS types1, db_types AS types2" +
                                            " WHERE type_compatibility.type_id_src = types1.type_id" +
                                            " AND type_compatibility.type_id_dest = types2.type_id" +
                                            " AND types2.type_name = '%s'" +
                                            " UNION" +
                                            " SELECT types2.type_name" +
                                            " FROM type_compatibility, db_types AS types1, db_types AS types2" +
                                            " WHERE type_compatibility.type_id_src = types1.type_id" +
                                            " AND type_compatibility.type_id_dest = types2.type_id" +
                                            " AND types1.type_name = '%s';" , typeName, typeName));

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
}
