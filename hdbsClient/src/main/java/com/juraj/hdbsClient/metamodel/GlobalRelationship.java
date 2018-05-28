package com.juraj.hdbsClient.metamodel;

/**
 * Created by Juraj on 24.4.2018..
 */
public class GlobalRelationship {

    private String primaryKeyId;
    private String foreignKeyId;

    public GlobalRelationship(String primaryKeyId, String foreignKeyId) {
        this.primaryKeyId = primaryKeyId;
        this.foreignKeyId = foreignKeyId;
    }

    public String getPrimaryKeyId() {
        return primaryKeyId;
    }

    public String getForeignKeyId() {
        return foreignKeyId;
    }

    public String toString(){
        return  primaryKeyId + "-" + foreignKeyId;
    }
}
