package com.juraj.hdbs.schemaManagement.metamodeling;

/**
 * Created by Juraj on 22.3.2018..
 */
public abstract class Relationship {
    protected String primaryKeyId;
    protected String foreignKeyId;

    public Relationship(String primaryKeyId, String foreignKeyId) throws Exception {
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

    /** Gets the local id of a primary key column: tableId.columnId
     * @return String of local Id
     */
    public String getPrimaryKeyLocalId(){
        return primaryKeyId.split( "\\.")[1] + "." + primaryKeyId.split( "\\.")[2];
    }

    /** Gets the local id of a foreign key column: tableId.columnId
     * @return String of local Id
     */
    public String getForeignKeyLocalId(){
        return foreignKeyId.split( "\\.")[1] + "." + primaryKeyId.split( "\\.")[2];
    }
}
