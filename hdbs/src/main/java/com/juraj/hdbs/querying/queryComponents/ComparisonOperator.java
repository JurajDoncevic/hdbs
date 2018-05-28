package com.juraj.hdbs.querying.queryComponents;

/** Enum to establish what kind of comparison operator is being used
 * @author Juraj
 */
public enum ComparisonOperator {
    GREATER_OR_EQUAL(">="),
    LESSER_OR_EQUAL("<="),
    LESSER("<"),
    GREATER(">"),
    EQUAL("=="),
    NOT_EQUAL("!=");

    private final String value;

    ComparisonOperator(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
