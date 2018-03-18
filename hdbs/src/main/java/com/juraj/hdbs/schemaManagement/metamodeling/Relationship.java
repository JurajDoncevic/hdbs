package com.juraj.hdbs.schemaManagement.metamodeling;

/** Represents a PK-FK relationship
 * @author Juraj
 */
public class Relationship {

    private String primaryKeyTableName;
    private String primaryKeyColumnName;
    private String foreignKeyTableName;
    private String foreignKeyColumnName;

    /** Constructor
     * @param primaryKeyTableName Name of the table where the primary key column is
     * @param primaryKeyColumnName  Name of the primary key column
     * @param foreignKeyTableName Name of the table where the foreign key column is
     * @param foreignKeyColumnName Name of the foreign key column
     */
    public Relationship(String primaryKeyTableName, String primaryKeyColumnName, String foreignKeyTableName, String foreignKeyColumnName) {
        this.primaryKeyTableName = primaryKeyTableName;
        this.primaryKeyColumnName = primaryKeyColumnName;
        this.foreignKeyTableName = foreignKeyTableName;
        this.foreignKeyColumnName = foreignKeyColumnName;
    }

    /** Gets the name of a table that has the primary key
     * @return String of the table name
     */
    public String getPrimaryKeyTableName() {
        return primaryKeyTableName;
    }

    /** Gets the primary key column name
     * @return String of the column name
     */
    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnName;
    }

    /** Gets the name of a table that has the foreign key
     * @return String of the table name
     */
    public String getForeignKeyTableName() {
        return foreignKeyTableName;
    }

    /** Gets the foreign key column name
     * @return String of the column name
     */
    public String getForeignKeyColumnName() {
        return foreignKeyColumnName;
    }

}
