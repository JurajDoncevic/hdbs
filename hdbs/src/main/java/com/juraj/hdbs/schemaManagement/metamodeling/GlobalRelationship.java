package com.juraj.hdbs.schemaManagement.metamodeling;

/** Represents a global relationship between two tables from different databases (local schemas)
 * @author Juraj
 */
public class GlobalRelationship extends Relationship{

    /** Constructor
     * @param primaryKeyId Id of primary key column: dbName.tableName.columnName
     * @param foreignKeyId Id of foreign key column: dbName.tableName.columnName
     * @throws Exception When the given Ids are not in a valid format
     */
    public GlobalRelationship(String primaryKeyId, String foreignKeyId) throws Exception {
        super(primaryKeyId, foreignKeyId);
    }


}
