package com.juraj.hdbs.schemaManagement.metamodeling;

import com.juraj.hdbs.Utils.DBVendor;

import java.util.List;

/** Represents an abstract definition of a database. It equals as a local schema.
 * @author Juraj
 */
public abstract class Database {

    private String name;
    private List<Table> tables;
    private List<Relationship> relationships;

    /** Constructor
     * @param name Name of the database
     * @param tables List of tables in the database
     * @param relationships List of relationships among the tables in the database
     */
    public Database(String name, List<Table> tables, List<Relationship> relationships) {
        this.name = name;
        this.tables = tables;
        this.relationships = relationships;
    }

    /** Gets the database name
     * @return String of database name
     */
    public String getName() {
        return name;
    }

    /** Gets all the tables in the database
     * @return List of Table
     */
    public List<Table> getTables() {
        return tables;
    }

    /** Gets all the relationships in the database
     * @return List of Relationship
     */
    public List<Relationship> getRelationships() {
        return relationships;
    }

    /** Gets a table with specified name
     * @param tableName Name of a table
     * @return Table object or null if none exist
     */
    public Table getTableByName(String tableName) {
        for(Table t : tables){
            if(t.getName().equals(tableName)){
                return t;
            }
        }

        return null;
    }

    /** Gets the database vendor
     * @return Value of DBVendor enum
     */
    public DBVendor getDbVendor(){
        return DBVendor.NONE;
    }
}
