package com.juraj.hdbs.querying.globalQueries;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Juraj on 23.4.2018..
 */
public class GlobalMinAggregateQuery extends GlobalAggregateQuery {

    /**
     * Constructor
     *
     * @param aggregateColumnId Column id of aggregation column
     * @param fromTableIds      List of table ids in the FROM clause
     * @param joins             List of joins
     * @param whereClause       WHERE clause object
     */
    public GlobalMinAggregateQuery(String aggregateColumnId, List<String> fromTableIds, List<Join> joins, WhereClause whereClause) {
        super(aggregateColumnId, fromTableIds, joins, whereClause);
    }

    private final Function<List<Object>, Object> aggregationFunction = new Function<List<Object>, Object>() {
        @Override
        public Object apply(List<Object> input){

            if (input != null && input.size() != 0){
                try {
                    Float.parseFloat(String.valueOf(input.get(0)));

                    return input.stream().map(x -> Float.parseFloat(String.valueOf(x))).min(Float::compareTo).get();
                }catch (Exception e){

                }

                try {
                    Integer.parseInt(String.valueOf(input.get(0)));

                    return input.stream().map(x -> Integer.parseInt(String.valueOf(x))).min(Integer::compareTo).get();
                }catch (Exception e){
                    return null;
                }
            } else {
                return null;
            }
        }
    };

    @Override
    public Function<List<Object>, Object> getAggregationFunction(){
        return aggregationFunction;
    }
}
