package com.juraj.hdbs.schemaManagement.metamodeling;

import java.util.ArrayList;
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

    public boolean doesDatabaseExist(String dbName){
        if(getDatabaseByName(dbName) != null){
            return true;
        }
        return false;
    }

    public boolean doesTableExist(String tableId){
        if(tableId.matches("\\w+\\.\\w+")){
            String[] splits = tableId.split("\\.");
            if(getDatabaseByName(splits[0]) != null){
                if(getDatabaseByName(splits[0]).getTableByName(splits[1]) != null){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean doesColumnExist(String columnId){
        if(columnId.matches("\\w+\\.\\w+\\.\\w+")){
            String[] splits = columnId.split("\\.");
            if(getDatabaseByName(splits[0]) != null){
                if(getDatabaseByName(splits[0]).getTableByName(splits[1]) != null){
                    if(getDatabaseByName(splits[0]).getTableByName(splits[1]).getColumnByName(splits[2]) != null){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Table getTableById(String tableId){
        if(tableId.matches("\\w+\\.\\w+")){
            String[] splits = tableId.split("\\.");
            if(getDatabaseByName(splits[0]) != null){
                if(getDatabaseByName(splits[0]).getTableByName(splits[1]) != null){
                    return getDatabaseByName(splits[0]).getTableByName(splits[1]);
                }
            }
        }
        return null;
    }

    public Column getColumnById(String columnId){
        if(columnId.matches("\\w+\\.\\w+\\.\\w+")){
            String[] splits = columnId.split("\\.");
            if(getDatabaseByName(splits[0]) != null){
                if(getDatabaseByName(splits[0]).getTableByName(splits[1]) != null){
                    if(getDatabaseByName(splits[0]).getTableByName(splits[1]).getColumnByName(splits[2]) != null){
                        return getDatabaseByName(splits[0]).getTableByName(splits[1]).getColumnByName(splits[2]);
                    }
                }
            }
        }
        return null;
    }

    public boolean doesGlobalRelationshipExist(String primaryKeyId, String foreignKeyId){
        List<Relationship> relationships = new ArrayList<>();
        relationships.addAll(globalRelationships);

        if (relationships.stream().filter(r -> r.primaryKeyId.equals(primaryKeyId) && r.foreignKeyId.equals(foreignKeyId)).count() != 0){
            return true;
        }


        return false;
    }

    public boolean doesRelationshipExist(String primaryKeyId, String foreignKeyId){
        List<Relationship> relationships = new ArrayList<>();
        relationships.addAll(globalRelationships);
        for (Database db : databases){
            relationships.addAll(db.getLocalRelationships());
        }

        if (relationships.stream().filter(r -> r.primaryKeyId.equals(primaryKeyId) && r.foreignKeyId.equals(foreignKeyId)).count() != 0){
            return true;
        }


        return false;
    }


}
