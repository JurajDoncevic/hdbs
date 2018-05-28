package com.juraj.hdbs.metadataDb.Services;

import com.juraj.hdbs.schemaManagement.metamodeling.GlobalRelationship;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Service class used to manage global relationships in the metadata database
 * @author Juraj
 */
public class GlobalRelationshipService {

    private String connectionUrl;

    /** Constructor
     * @param connectionUrl Connection url in format of jdbc:sqlite:path_to_db
     */
    public GlobalRelationshipService(String connectionUrl){
        this.connectionUrl = connectionUrl;
    }

    /** Gets all the global relationships from the metadata database
     * @return List of GlobalRelationship
     */
    public List<GlobalRelationship> getAll() {

        List<GlobalRelationship> globalRelationships = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

        Statement statement = connection.createStatement();
        statement.execute("SELECT primaryKeyId, foreignKeyId FROM global_relationships;");

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()){

            globalRelationships.add(new GlobalRelationship(resultSet.getString(1), resultSet.getString(2)));

        }

        connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return globalRelationships;
    }

    /** Gets all the global relationships from metadata database by a primary key id
     * @param primaryKeyId Primary key id used to globally describe a pk column
     *                     @see GlobalRelationship
     * @return List of GlobalRelationship
     */
    public List<GlobalRelationship> getByPrimaryKeyId(String primaryKeyId){
        List<GlobalRelationship> globalRelationships = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT primaryKeyId, foreignKeyId FROM global_relationships WHERE primaryKedId = '%s';", primaryKeyId));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                globalRelationships.add(new GlobalRelationship(resultSet.getString(1), resultSet.getString(2)));

            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return globalRelationships;
    }

    /** Gets all the global relationships from metadata database by a foreign key id
     * @param foreignKeyId Foreign key id used to globally describe a fk column
     *                     @see GlobalRelationship
     * @return List of GlobalRelationship
     */
    public List<GlobalRelationship> getByForeignKeyId(String foreignKeyId){
        List<GlobalRelationship> globalRelationships = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT primaryKeyId, foreignKeyId FROM global_relationships WHERE primaryKedId = '%s';", foreignKeyId));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                globalRelationships.add(new GlobalRelationship(resultSet.getString(1), resultSet.getString(2)));

            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return globalRelationships;
    }

    /** Gets a global relationship from metadata database by both primary and foreign keys
     * @param primaryKeyId Primary key id used to globally describe a pk column
     *                     @see GlobalRelationship
     * @param foreignKeyId Foreign key id used to globally describe a fk column
     *                     @see  GlobalRelationship
     * @return A GlobalRelationship object; null on failure
     */
    public GlobalRelationship getByPrimaryAndForeignKeyId(String primaryKeyId, String foreignKeyId){
        GlobalRelationship GlobalRelationship = null;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();
            statement.execute(String.format("SELECT primaryKeyId, foreignKeyId FROM global_relationships WHERE primaryKedId = '%s' AND foreignKeyId = '%s';", primaryKeyId, foreignKeyId));

            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()){

                GlobalRelationship = new GlobalRelationship(resultSet.getString(1), resultSet.getString(2));

            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return GlobalRelationship;
    }

    /** Stores a global relationship into the metadata database
      * @param globalRelationship Global relationship to be stored
     * @return true on success; false on failure
     */
    public boolean insert(GlobalRelationship globalRelationship){
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();

            statement.execute(String.format("INSERT INTO global_relationships(primaryKeyId, foreignKeyId) VALUES('%s','%s');", globalRelationship.getPrimaryKeyId(), globalRelationship.getForeignKeyId()));


            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /** Deletes a global relationship from the metadata database
     * @param globalRelationship Global relationship to be deleted
     * @return true on success; false on failure
     */
    public boolean delete(GlobalRelationship globalRelationship){
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();

            statement.execute(String.format("DELETE FROM global_relationships WHERE primaryKeyId = '%s' AND foreignKeyId = '%s';)", globalRelationship.getPrimaryKeyId(), globalRelationship.getForeignKeyId()));


            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Deletes all global relationships tied to a database with given name
     * @param dbName Database whose global relationships are to be deleted
     * @return true on success; false on failure
     */
    public boolean deleteRelationshipsForDB(String dbName){
        try {
            Connection connection = DriverManager.getConnection(connectionUrl);

            Statement statement = connection.createStatement();

            statement.execute(String.format("DELETE FROM global_relationships WHERE primaryKeyId = '%%.%s.%%' OR foreignKeyId = '%%.%s.%%';)", dbName, dbName));


            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
