package com.juraj.hdbs.querying.globalQueries;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Juraj on 23.4.2018..
 */
public class GlobalCountAggregateQuery extends GlobalAggregateQuery {
    /**
     * Constructor
     *
     * @param aggregateColumnId Column id of aggregation column
     * @param fromTableIds      List of table ids in the FROM clause
     * @param joins             List of joins
     * @param whereClause       WHERE clause object
     */
    public GlobalCountAggregateQuery(String aggregateColumnId, List<String> fromTableIds, List<Join> joins, WhereClause whereClause) {
        super(aggregateColumnId, fromTableIds, joins, whereClause);
    }

    @Override
    public Function<List<Object>, Object> getAggregationFunction() {
        return aggregationFunction;
    }

    private final Function<List<Object>, Object> aggregationFunction = new Function<List<Object>, Object>() {
        @Override
        public Object apply(List<Object> input){
            if (input != null)
                return input.size();
            else
                return 0;
        }
    };
}
