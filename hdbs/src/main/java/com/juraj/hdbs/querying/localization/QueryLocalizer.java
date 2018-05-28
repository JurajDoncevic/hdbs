package com.juraj.hdbs.querying.localization;

import com.juraj.hdbs.querying.globalQueries.GlobalQuery;
import com.juraj.hdbs.querying.globalQueries.GlobalSelectQuery;
import com.juraj.hdbs.querying.optimization.LocalQueryOptimizer;
import com.juraj.hdbs.querying.queryComponents.Condition;
import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.LogicalOperator;
import com.juraj.hdbs.querying.queryComponents.WhereClause;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Holds methods for localization of global queries
 * @author Juraj
 */
public class QueryLocalizer {

    /** Initializes the localization and calls local query optimization
     * @param globalQuery Global query
     * @param globalSchema Current global schema
     * @return List of localized and optimized local queries
     */
    public static List<LocalSelectQuery> localizeGlobalQuery(GlobalQuery globalQuery, GlobalSchema globalSchema){

        List<String> queriedTableIds = globalQuery.getFromTableIds();
        List<LocalSelectQuery> localQueries = new ArrayList<>();

        localQueries = getLocalQueriesForTables(globalQuery, globalSchema);

        //optimize queries
        localQueries = LocalQueryOptimizer.optimizeLocalQueries(localQueries, globalQuery, globalSchema);

        //include table pks
        localQueries = includePrimaryKeyInLocalQueries(globalQuery, globalSchema, localQueries);

        //just to be sure
        localQueries.forEach(LocalSelectQuery::cleanQuery);
        return localQueries;
    }

    @Deprecated
    public static List<LocalSelectQuery> localizeGlobalSelectQuery(GlobalSelectQuery globalSelectQuery, GlobalSchema globalSchema){

        List<String> queriedTableIds = globalSelectQuery.getFromTableIds();
        List<LocalSelectQuery> localQueries = new ArrayList<>();


        for (String tableId : queriedTableIds){
            List<String> fromTableIds = new ArrayList<>();
            String localizedTableId = tableId.split("\\.")[1];
            String dbName = tableId.split("\\.")[0];
            fromTableIds.add(localizedTableId);
            LocalSelectQuery localSelectQuery = new LocalSelectQuery(fromTableIds, new ArrayList<Join>(), new WhereClause(new ArrayList<Condition>(), new ArrayList<LogicalOperator>()), new ArrayList<String>(), dbName, globalSchema.getDatabaseByName(dbName).getDbVendor());

            localQueries.add(localSelectQuery);
        }

        //optimization by localizing local joins
        for (Join join : globalSelectQuery.getJoins()){
            if (join.isLocal()){
                LocalSelectQuery pkTableQuery = localQueries.stream().filter(q -> q.getFromTableIds().contains(join.getPrimaryKeyTableLocalized()) && q.getDbName().equals(join.getPrimaryKeyDbName())).findFirst().get();
                LocalSelectQuery fkTableQuery = localQueries.stream().filter(q -> q.getFromTableIds().contains(join.getForeignKeyTableLocalized()) && q.getDbName().equals(join.getForeignKeyDbName())).findFirst().get();

                fkTableQuery.getFromTableIds().addAll(pkTableQuery.getFromTableIds());
                fkTableQuery.getJoins().add(join);
                fkTableQuery.getJoins().addAll(pkTableQuery.getJoins());


                localQueries.remove(pkTableQuery);

            }
        }


        if (globalSelectQuery.getClass().equals(GlobalSelectQuery.class)){
            //if * is not used - specific columns are mentioned -> select mentioned in select, joins, where and table pks
            if (!globalSelectQuery.getSelectColumnIds().isEmpty()){
                //select
                for (String selectedColId : globalSelectQuery.getSelectColumnIds()){
                    String[] split = selectedColId.split("\\.");
                    String localizedTableId = split[1];
                    String dbName = split[0];
                    String localizedColumnId = split[1] + "." + split[2];

                    localQueries.stream()
                            .filter(q -> q.getFromTableIds().contains(localizedTableId) && q.getDbName().equals(dbName))
                            .findFirst().ifPresent(q -> q.getSelectedColumnIds().add(localizedColumnId));
                }
                //joins
                for (Join join : globalSelectQuery.getJoins()){

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
                //table pks
                for (String tableId : globalSelectQuery.getFromTableIds()){
                    String[] split = tableId.split("\\.");
                    String localizedTableId = split[1];
                    String dbName = split[0];

                    String primaryKeyLocalizedId = localizedTableId + "." + globalSchema.getTableById(tableId).getPrimaryKey().getName();

                    localQueries.stream()
                            .filter(q -> q.getDbName().equals(dbName) && q.getFromTableIds().contains(localizedTableId))
                            .findFirst().ifPresent(q -> q.getSelectedColumnIds().add(primaryKeyLocalizedId));
                }

                //where clause variables
                List<String> variables = globalSelectQuery.getWhereClause().getConditions().stream().map(c -> c.getVariable()).collect(Collectors.toList());
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
            }
        }

        localQueries.forEach(q -> q.cleanQuery());
        return localQueries;
    }

    /** Returns a starting list of local queries by taking table names from the global query FROM clause
     * @param globalQuery Global query
     * @param globalSchema Current global shcema
     * @return List of local queries
     */
    private static List<LocalSelectQuery> getLocalQueriesForTables(GlobalQuery globalQuery, GlobalSchema globalSchema){
        List<String> queriedTableIds = globalQuery.getFromTableIds();
        List<LocalSelectQuery> localQueries = new ArrayList<>();


        //get local queries for tables
        for (String tableId : queriedTableIds){
            List<String> fromTableIds = new ArrayList<>();
            String localizedTableId = tableId.split("\\.")[1];
            String dbName = tableId.split("\\.")[0];
            fromTableIds.add(localizedTableId);
            LocalSelectQuery localSelectQuery = new LocalSelectQuery(fromTableIds, new ArrayList<Join>(), new WhereClause(new ArrayList<Condition>(), new ArrayList<LogicalOperator>()), new ArrayList<String>(), dbName, globalSchema.getDatabaseByName(dbName).getDbVendor());

            localQueries.add(localSelectQuery);
        }
        localQueries.forEach(LocalSelectQuery::cleanQuery);
        return localQueries;
    }

    /** Includes primary key columns for tables in all queries (in case a prequery does not require one, but a final query does - update, delete global queries)
     * @param globalQuery Global query
     * @param globalSchema Current global schema
     * @param localQueries Local queries
     * @return Local queries with included primary key columns of queried tables
     */
    private static List<LocalSelectQuery> includePrimaryKeyInLocalQueries(GlobalQuery globalQuery, GlobalSchema globalSchema, List<LocalSelectQuery> localQueries){
        for (String tableId : globalQuery.getFromTableIds()){
            String[] split = tableId.split("\\.");
            String localizedTableId = split[1];
            String dbName = split[0];

            String primaryKeyLocalizedId = localizedTableId + "." + globalSchema.getTableById(tableId).getPrimaryKey().getName();

            localQueries.stream()
                    .filter(q -> q.getDbName().equals(dbName) && q.getFromTableIds().contains(localizedTableId))
                    .findFirst().ifPresent(q -> q.getSelectedColumnIds().add(primaryKeyLocalizedId));
        }
        localQueries.forEach(LocalSelectQuery::cleanQuery);
        return localQueries;
    }

}
