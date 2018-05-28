package com.juraj.hdbs.querying.globalQueries;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Juraj on 23.4.2018..
 */
public abstract class GlobalAggregateQuery extends GlobalQuery{

    private String aggregateColumnId;

    /** Constructor
     * @param aggregateColumnId  Column id of aggregation column
     * @param fromTableIds List of table ids in the FROM clause
     * @param joins        List of joins
     * @param whereClause  WHERE clause object
     */
    public GlobalAggregateQuery(String aggregateColumnId, List<String> fromTableIds, List<Join> joins, WhereClause whereClause) {
        super(fromTableIds, joins, whereClause);
        this.aggregateColumnId = aggregateColumnId;
    }

    public String getAggregateColumnId() {
        return aggregateColumnId;
    }

    public abstract Function<List<Object>, Object> getAggregationFunction();
}
