package com.juraj.hdbs.schemaManagement.metamodeling;

/**
 * Created by Juraj on 13.3.2018..
 */
public class Relationship {

    private String primaryKeyTableName;
    private String primaryKeyColumnName;
    private String foreignKeyTableName;
    private String foreignKeyColumnName;

    public Relationship(String primaryKeyTableName, String primaryKeyColumnName, String foreignKeyTableName, String foreignKeyColumnName) {
        this.primaryKeyTableName = primaryKeyTableName;
        this.primaryKeyColumnName = primaryKeyColumnName;
        this.foreignKeyTableName = foreignKeyTableName;
        this.foreignKeyColumnName = foreignKeyColumnName;
    }

    public String getPrimaryKeyTableName() {
        return primaryKeyTableName;
    }

    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnName;
    }

    public String getForeignKeyTableName() {
        return foreignKeyTableName;
    }

    public String getForeignKeyColumnName() {
        return foreignKeyColumnName;
    }

}
