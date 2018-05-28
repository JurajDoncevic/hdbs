package com.juraj.hdbs.querying.queryComponents;

/** Enum of a logical operator in a WHERE clause
 * @author Juraj
 */
public enum LogicalOperator {
    AND("&&"),
    OR("||");

    private final String value;

    private LogicalOperator(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
