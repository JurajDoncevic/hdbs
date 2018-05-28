package com.juraj.hdbs.querying.queryComponents;

/** Class that represents a local or global join of a query
 * @author Juraj
 */
public class Join {

    private String primaryKeyId;
    private String foreignKeyId;

    /** Constructor
     * @param primaryKeyId Primary key column id
     * @param foreignKeyId Foreign key column id
     */
    public Join(String primaryKeyId, String foreignKeyId) {
        this.primaryKeyId = primaryKeyId;
        this.foreignKeyId = foreignKeyId;
    }

    /** Gets the primary key column id
     * @return Primary key column id
     */
    public String getPrimaryKeyId() {
        return primaryKeyId;
    }

    /** Gets the foreign key column id
     * @return Foreign key column id
     */
    public String getForeignKeyId() {
        return foreignKeyId;
    }

    /** Gets the database name for the primary key id
     * @return Name of database
     */
    public String getPrimaryKeyDbName(){
        String[] split = primaryKeyId.split("\\.");
        return split[0];
    }

    /** Gets the database name for the foreign key id
     * @return Name of database
     */
    public String getForeignKeyDbName(){
        String[] split = foreignKeyId.split("\\.");
        return split[0];
    }

    /** Gets the table id of a primary key id
     * @return Table id: "dbName.tableName"
     */
    public String getPrimaryKeyTableId(){
        String[] split = primaryKeyId.split("\\.");
        return split[0]+"."+split[1];
    }

    /** Gets the table id of a foreign key id
     * @return Table id: "dbName.tableName"
     */
    public String getForeignKeyTableId(){
        String[] split = foreignKeyId.split("\\.");
        return split[0]+"."+split[1];
    }

    /** Gets a localized primary key id
     * @return Localized column id: "tableName.columnName"
     */
    public String getPrimaryKeyLocalized(){
        String[] split = primaryKeyId.split("\\.");
        return split[1]+"."+split[2];
    }

    /** Gets a localized foreign key id
     * @return Localized column id: "tableName.columnName"
     */
    public String getForeignKeyLocalized(){
        String[] split = foreignKeyId.split("\\.");
        return split[1]+"."+split[2];
    }

    /** Gets the name of the table of the foreign key column
     * @return Table name
     */
    public String getForeignKeyTableLocalized(){
        String[] split = foreignKeyId.split("\\.");
        return split[1];
    }

    /** Gets the name of the table of the primary key column
     * @return Table name
     */
    public String getPrimaryKeyTableLocalized(){
        String[] split = primaryKeyId.split("\\.");
        return split[1];
    }

    /** Determines if 2 joins are identical
     * @param join Join to be compared to
     * @return True on equal; False on not equal
     */
    public boolean equals(Join join){
        if(join.getPrimaryKeyId().equals(primaryKeyId) && join.getForeignKeyId().equals(foreignKeyId)){
            return true;
        }

        return false;
    }

    /** Returns showcase name for the join
     * @return String: "pkId-fkId"
     */
    public String toString(){
        return getPrimaryKeyId()+"-"+getForeignKeyId();
    }

    /** Determines if a join is local in regards to a schema
     * @return True if local; False if global
     */
    public boolean isLocal(){
        if (getPrimaryKeyDbName().equals(getForeignKeyDbName()))
            return true;
        else
            return false;
    }

}
