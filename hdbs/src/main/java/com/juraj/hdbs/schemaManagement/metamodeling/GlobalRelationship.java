package com.juraj.hdbs.schemaManagement.metamodeling;


/** Represents a global relationship between two tables from different databases (local schemas)
 * @author Juraj
 */
public class GlobalRelationship {


    private String primaryKeyId;
    private String foreignKeyId;

    /** Constructor
     * @param primaryKeyId Id of primary key column: dbName.tableName.columnName
     * @param foreignKeyId Id of foreign key column: dbName.tableName.columnName
     * @throws Exception When the given Ids are not in a valid format
     */
    public GlobalRelationship(String primaryKeyId, String foreignKeyId) throws Exception {

        if (!primaryKeyId.matches("\\w+\\.\\w+\\.\\w+") || !foreignKeyId.matches("\\w+\\.\\w+\\.\\w+")){
            throw new Exception("Global relationship IDs are not valid");
        }
        this.primaryKeyId = primaryKeyId;
        this.foreignKeyId = foreignKeyId;
    }

    /** Gets the primary key column id
     * @return String of the id
     */
    public String getPrimaryKeyId() {
        return primaryKeyId;
    }

    /** Gets the foreign key column id
     * @return String of the id
     */
    public String getForeignKeyId() {
        return foreignKeyId;
    }

    /** Gets the primary key's database name
     * @return String of the database name
     */
    public String getPrimaryKeyDB(){
        return primaryKeyId.split( "\\.")[0];
    }

    /** Gets the primary key's table name
     * @return String of the table name
     */
    public String getPrimaryKeyTable(){
        return primaryKeyId.split( "\\.")[1];
    }

    /** Gets the primary key's column name
     * @return String of the column name
     */
    public String getPrimaryKeyColumn(){
        return primaryKeyId.split( "\\.")[2];
    }

    /** Gets the foreign key's database name
     * @return String of the database name
     */
    public String getForeignKeyDB(){
        return foreignKeyId.split( "\\.")[0];
    }

    /** Gets the foreign key's table name
     * @return String of the table name
     */
    public String getForeignKeyTable(){
        return foreignKeyId.split( "\\.")[1];
    }

    /** Gets the foreign key's column name
     * @return String of the column name
     */
    public String getForeignKeyColumn(){
        return foreignKeyId.split( "\\.")[2];
    }


}
