package com.juraj.hdbs.schemaManagement.metamodeling;

/** Represents a PK-FK relationship
 * @author Juraj
 */
public class LocalRelationship extends Relationship {

    /** Constructor
     * @param primaryKeyId Id of primary key column: dbName.tableName.columnName
     * @param foreignKeyId Id of foreign key column: dbName.tableName.columnName
     * @throws Exception When the given Ids are not in a valid format
     */
    public LocalRelationship(String primaryKeyId, String foreignKeyId) throws Exception {
        super(primaryKeyId, foreignKeyId);

    }

}
