package com.juraj.hdbs.querying.queryComponents;

/** Condition of a WHERE clause
 * @author Juraj
 */
public class Condition {

    private String variable;
    private ComparisonOperator comparisonOperator;
    private String value;

    /** Constructor
     * @param variable A variable (ex. column id)
     * @param comparisonOperator Comparison operator for the condition
     * @param value Value that the variable is being compared to
     */
    public Condition(String variable, ComparisonOperator comparisonOperator, String value) {
        this.variable = variable;
        this.comparisonOperator = comparisonOperator;
        this.value = value;
    }

    /** Gets the variable
     * @return Variable
     */
    public String getVariable() {
        return variable;
    }

    /** Gets the comparison operator
     * @return Comparison operator
     */
    public ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    /** Gets the value
     * @return String of value
     */
    public String getValue() {
        return value;
    }
}
