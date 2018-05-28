package com.juraj.hdbs.querying.globalQueries;


import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.LogicalOperator;
import com.juraj.hdbs.querying.queryComponents.WhereClause;

import java.util.List;
import java.util.stream.Collectors;

/** Global query class
 * @author Juraj
 */
public abstract class GlobalQuery {

    private List<String> fromTableIds;
    private List<Join> joins;
    private WhereClause whereClause;

    /** Constructor
     * @param fromTableIds List of table ids in the FROM clause
     * @param joins List of joins
     * @param whereClause WHERE clause object
     */
    public GlobalQuery(List<String> fromTableIds, List<Join> joins, WhereClause whereClause) {
        this.fromTableIds = fromTableIds;
        this.joins = joins;
        this.whereClause = whereClause;
    }

    /** Gets the table ids in the FROM clause of the query
     * @return List of strings with the table ids
     */
    public List<String> getFromTableIds() {
        return fromTableIds;
    }

    /** Gets the joins of the query
     * @return List of Join objects
     */
    public List<Join> getJoins() {
        return joins;
    }

    /** Gets the WHERE clause
     * @return WhereClause object
     */
    public WhereClause getWhereClause() {
        return whereClause;
    }

    /** Gets the names of databases included in the global query
     * @return List of database names as strings
     */
    public List<String> getIncludedDbNames(){
        return getFromTableIds().stream().map(t -> t.split("\\.")[0]).distinct().collect(Collectors.toList());
    }

    /** Determines if the WHERE clause has a conjunctive expression
     * @return True if conjunctive; else False
     */
    public boolean isWhereConjuctive(){
        for (LogicalOperator logicalOperator : whereClause.getLogicalOperators()){
            if (logicalOperator == LogicalOperator.OR){
                return false;
            }
        }
        return true;
    }


}
