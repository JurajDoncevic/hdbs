package com.juraj.hdbs.querying.optimization;

import com.juraj.hdbs.querying.globalQueries.GlobalQuery;
import com.juraj.hdbs.querying.globalQueries.GlobalSelectQuery;
import com.juraj.hdbs.querying.localization.LocalSelectQuery;
import com.juraj.hdbs.querying.queryComponents.Condition;
import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.LogicalOperator;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalSchema;

import java.util.List;
import java.util.stream.Collectors;

/** Holds local query optimization methods
 * @author Juraj
 */
public class LocalQueryOptimizer {

    /** Entry point method for optimization; starts the optimization process
     * @param localQueries List of local select queries
     * @param globalQuery Global query that was localized
     * @param globalSchema Current global schema
     * @return List of optimal local select queries
     */
    public static List<LocalSelectQuery> optimizeLocalQueries(List<LocalSelectQuery> localQueries, GlobalQuery globalQuery, GlobalSchema globalSchema){

        //optimization by localizing local joins
        localQueries = LocalQueryOptimizer.optimizeLocalQueriesByLocalJoins(globalQuery, localQueries, globalSchema);

        //optimization by select if it is a select query
        if (globalQuery.getClass().equals(GlobalSelectQuery.class))
            localQueries = LocalQueryOptimizer.optimizeLocalQueriesBySelectColumns(localQueries, (GlobalSelectQuery) globalQuery);

        //optimization by join columns
        localQueries = LocalQueryOptimizer.optimizeLocalQueriesByJoinColumns(globalQuery, localQueries);

        //optimization by where clause variables
        localQueries = LocalQueryOptimizer.optimizeLocalQueryByWhereColumns(globalQuery, localQueries);

        return localQueries;
    }

    /** Joins that reference tables of the same schema are pushed down the query tree to be executed locally
     * @param globalQuery Global query
     * @param localQueries List of local select queries
     * @param globalSchema Current global schema
     * @return List of optimized local select queries
     */
    private static List<LocalSelectQuery> optimizeLocalQueriesByLocalJoins(GlobalQuery globalQuery, List<LocalSelectQuery> localQueries, GlobalSchema globalSchema){
        for (Join join : globalQuery.getJoins()){
            if (join.isLocal()){
                LocalSelectQuery pkTableQuery = localQueries.stream().filter(q -> q.getFromTableIds().contains(join.getPrimaryKeyTableLocalized()) && q.getDbName().equals(join.getPrimaryKeyDbName())).findFirst().get();
                LocalSelectQuery fkTableQuery = localQueries.stream().filter(q -> q.getFromTableIds().contains(join.getForeignKeyTableLocalized()) && q.getDbName().equals(join.getForeignKeyDbName())).findFirst().get();

                fkTableQuery.getFromTableIds().addAll(pkTableQuery.getFromTableIds());
                fkTableQuery.getJoins().add(join);
                fkTableQuery.getJoins().addAll(pkTableQuery.getJoins());


                localQueries.remove(pkTableQuery);

            }
        }
        localQueries.forEach(LocalSelectQuery::cleanQuery);
        return localQueries;
    }

    /** Projections are added by a global select clause to minimize network traffic when receiving local results
     * @param localQueries Local select queries
     * @param globalSelectQuery Global select query
     * @return List of optimized local select queries
     */
    private static List<LocalSelectQuery> optimizeLocalQueriesBySelectColumns(List<LocalSelectQuery> localQueries, GlobalSelectQuery globalSelectQuery){
        if (!((GlobalSelectQuery)globalSelectQuery).getSelectColumnIds().isEmpty()){
            //select
            for (String selectedColId : ((GlobalSelectQuery)globalSelectQuery).getSelectColumnIds()){
                String[] split = selectedColId.split("\\.");
                String localizedTableId = split[1];
                String dbName = split[0];
                String localizedColumnId = split[1] + "." + split[2];

                localQueries.stream()
                        .filter(q -> q.getFromTableIds().contains(localizedTableId) && q.getDbName().equals(dbName))
                        .findFirst().ifPresent(q -> q.getSelectedColumnIds().add(localizedColumnId));
            }

        }
        localQueries.forEach(LocalSelectQuery::cleanQuery);
        return localQueries;
    }

    /** Projection is added for columns that are mentioned in global joins
     * @param globalQuery Global query
     * @param localQueries Local select queries
     * @return List of optimized local queries
     */
    private static List<LocalSelectQuery> optimizeLocalQueriesByJoinColumns(GlobalQuery globalQuery, List<LocalSelectQuery> localQueries){
        for (Join join : globalQuery.getJoins()){

            String fkId = join.getForeignKeyId();
            String[] fksplit = fkId.split("\\.");
            String fkLocalizedTableId = fksplit[1];
            String fkDbName = fksplit[0];
            String fkLocalizedColumnId = fksplit[1] + "." + fksplit[2];

            localQueries.stream()
                    .filter(q -> q.getFromTableIds().contains(fkLocalizedTableId) && q.getDbName().equals(fkDbName))
                    .findFirst().ifPresent(q -> q.getSelectedColumnIds().add(fkLocalizedColumnId));

            String pkId = join.getPrimaryKeyId();
            String[] pksplit = pkId.split("\\.");
            String pkLocalizedTableId = pksplit[1];
            String pkDbName = pksplit[0];
            String pkLocalizedColumnId = pksplit[1] + "." + pksplit[2];

            localQueries.stream()
                    .filter(q -> q.getFromTableIds().contains(pkLocalizedTableId) && q.getDbName().equals(pkDbName))
                    .findFirst().ifPresent(q -> q.getSelectedColumnIds().add(pkLocalizedColumnId));
        }
        localQueries.forEach(LocalSelectQuery::cleanQuery);
        return localQueries;
    }

    /** Projection is added for columns that are mentioned in the global WHERE clause
     * @param globalQuery Global query
     * @param localQueries List of local select queries
     * @return List of optimized local queries
     */
    private static List<LocalSelectQuery> optimizeLocalQueryByWhereColumns(GlobalQuery globalQuery, List<LocalSelectQuery> localQueries){
        List<String> variables = globalQuery.getWhereClause().getConditions().stream().map(c -> c.getVariable()).collect(Collectors.toList());
        for (String colId : variables){
            String[] split = colId.split("\\.");
            String tableName = split[1];
            String dbName = split[0];
            String columnName = split[2];

            String localizedColumnId = tableName + "." +columnName;

            localQueries.stream()
                    .filter(q -> q.getDbName().equals(dbName) && q.getFromTableIds().contains(tableName))
                    .findFirst().ifPresent(q -> q.getSelectedColumnIds().add(localizedColumnId));
        }
        localQueries.forEach(LocalSelectQuery::cleanQuery);
        return localQueries;
    }

}
