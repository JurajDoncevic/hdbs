package com.juraj.hdbs.schemaManagement.metamodeling;

import java.util.List;

/** Represents a global schema of the HDBS
 * @author Juraj
 */
public class GlobalSchema {

    private List<Database> databases;
    private List<GlobalRelationship> globalRelationships;

    /** Constructor
     * @param databases List of databases (local schemas) in the global schema
     * @param globalRelationships List of global relationships among the databases (local schemas)
     */
    public GlobalSchema(List<Database> databases, List<GlobalRelationship> globalRelationships) {
        this.databases = databases;
        this.globalRelationships = globalRelationships;
    }

    /** Gets all the databases in the global schema
     * @return List of Database
     */
    public List<Database> getDatabases() {
        return databases;
    }

    /** Gets a database by name
     * @param databaseName Name of database
     * @return Database object or null if none exists
     */
    public Database getDatabaseByName(String databaseName){
        for(Database db : databases){
            if(db.getName().equals(databaseName)){
                return db;
            }
        }

        return null;
    }

    /** Gets all the global relationships in the global schema
     * @return List of GlobalRelationship
     */
    public List<GlobalRelationship> getGlobalRelationships(){
        return globalRelationships;
    }



}
