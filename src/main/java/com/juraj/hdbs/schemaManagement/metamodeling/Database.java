package com.juraj.hdbs.schemaManagement.metamodeling;

import java.util.List;

/**
 * Created by Juraj on 13.3.2018..
 */
public class Database {

    private String name;
    private List<Table> tables;
    private List<Relationship> relationships;

    public Database(String name, List<Table> tables, List<Relationship> relationships) {
        this.name = name;
        this.tables = tables;
        this.relationships = relationships;
    }

    public String getName() {
        return name;
    }

    public List<Table> getTables() {
        return tables;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public Table getTableByName(String tableName) {
        for(Table t : tables){
            if(t.getName().equals(tableName)){
                return t;
            }
        }

        return null;
    }
}
