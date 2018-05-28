package com.juraj.hdbs.querying.queryComponents;

import java.util.List;

/** Class representing a WHERE clause
 * @author Juraj
 */
public class WhereClause {

    private List<Condition> conditions;
    private List<LogicalOperator> logicalOperators;

    /** Constructor
     * @param conditions Conditions in a where clause
     * @param logicalOperators Logical operators between the conditions
     */
    public WhereClause(List<Condition> conditions, List<LogicalOperator> logicalOperators) {
        this.conditions = conditions;
        this.logicalOperators = logicalOperators;
    }

    /** Gets the conditions from the clause
     * @return List of conditions
     */
    public List<Condition> getConditions() {
        return conditions;
    }

    /** Gets the logical operators from the clause
     * @return List of logical operators
     */
    public List<LogicalOperator> getLogicalOperators() {
        return logicalOperators;
    }
}
