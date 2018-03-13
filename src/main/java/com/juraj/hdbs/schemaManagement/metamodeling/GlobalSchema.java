package com.juraj.hdbs.schemaManagement.metamodeling;

import java.util.List;

/**
 * Created by Juraj on 13.3.2018..
 */
public class GlobalSchema {

    private List<Database> databases;

    public GlobalSchema(List<Database> databases) {
        this.databases = databases;
    }

    public List<Database> getDatabases() {
        return databases;
    }

    public Database getDatabaseByName(String databaseName){
        for(Database db : databases){
            if(db.getName().equals(databaseName)){
                return db;
            }
        }

        return null;
    }

}
