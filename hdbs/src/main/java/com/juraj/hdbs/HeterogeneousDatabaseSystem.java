package com.juraj.hdbs;

import com.juraj.hdbs.consistency.GlobalRelationshipForeignKeyConsistency;
import com.juraj.hdbs.querying.dataSets.DataSet;
import com.juraj.hdbs.querying.dataSets.ResultDataSet;
import com.juraj.hdbs.querying.execution.LocalQueryRunner;
import com.juraj.hdbs.querying.globalQueries.GlobalAggregateQuery;
import com.juraj.hdbs.querying.globalQueries.GlobalDeleteQuery;
import com.juraj.hdbs.querying.globalQueries.GlobalSelectQuery;
import com.juraj.hdbs.querying.globalQueries.GlobalUpdateQuery;
import com.juraj.hdbs.querying.globalization.ResultGlobalizer;
import com.juraj.hdbs.querying.localization.LocalDeleteQuery;
import com.juraj.hdbs.querying.localization.LocalSelectQuery;
import com.juraj.hdbs.querying.localization.LocalUpdateQuery;
import com.juraj.hdbs.querying.localization.QueryLocalizer;
import com.juraj.hdbs.querying.queryComponents.*;
import com.juraj.hdbs.utils.DBVendor;
import com.juraj.hdbs.connection.credentials.DBCredentialsMysql;
import com.juraj.hdbs.connection.credentials.DBCredentialsPostgres;
import com.juraj.hdbs.connection.pool.ConnectionPool;
import com.juraj.hdbs.metadataDb.MetadataDb;
import com.juraj.hdbs.querying.GlobalQueryFactory;
import com.juraj.hdbs.schemaManagement.GlobalSchemaToXMLConverter;
import com.juraj.hdbs.schemaManagement.SchemaLoader;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalRelationship;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalSchema;
import com.juraj.hdbs.utils.customExceptions.LocalQueryExecutionException;
import com.juraj.hdbs.utils.customExceptions.QueryCreationException;
import com.juraj.hdbs.utils.customExceptions.ResultGlobalizationException;
import com.juraj.hdbs.utils.results.ActionResult;
import com.juraj.hdbs.utils.results.ActionResultType;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


/** Class for user access to the HDBS
 * @author Juraj
 */
public class HeterogeneousDatabaseSystem {

    private GlobalSchema currentGlobalSchema;
    private ConnectionPool connectionPool;
    private String metadataDbUrl;
    private MetadataDb metadataDb;


    /** Constructor
     * @param metadataDbUrl Path to the SQLite database file to store the metadata
     * @throws Exception Thrown in case of failure
     */
    public HeterogeneousDatabaseSystem(String metadataDbUrl) throws Exception {
        currentGlobalSchema = null;
        this.metadataDbUrl = metadataDbUrl;
        metadataDb = new MetadataDb(metadataDbUrl);
        connectionPool = new ConnectionPool(metadataDb);
        currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);

    }

    /** Adds a database connection to pool
     * @param serverURL Database server url
     * @param dbName Database name
     * @param username Username
     * @param password Password
     * @param vendor Enum value of supported vendor
     * @return ActionResult
     */
    public ActionResult addConnectionToPool(String serverURL, String dbName, String username, String password, DBVendor vendor) {
        try {
            switch (vendor) {
                case POSTGRESQL:
                    connectionPool.addPostgresToPool(new DBCredentialsPostgres(serverURL, dbName, username, password));
                    break;
                case MYSQL:
                    connectionPool.addMysqlToPool(new DBCredentialsMysql(serverURL, dbName, username, password));
                    break;
                default:
                    break;
            }

            currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);
        }catch (Exception e){
            return new ActionResult(ActionResultType.FAILURE, e.getMessage());
        }

        return new ActionResult(ActionResultType.SUCCESS, "Connection added.");
    }

    /** Removes database with given name from connection pool
     * @param dbName Database name
     * @return ActionResult
     */
    public ActionResult removeConnectionFromPool(String dbName){
        connectionPool.removeFromPool(dbName);
        metadataDb.getGlobalRelationshipService().deleteRelationshipsForDB(dbName);
        try {
            currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);
        } catch (Exception e) {
            return new ActionResult(ActionResultType.FAILURE, e.getMessage());
        }

        return new ActionResult(ActionResultType.SUCCESS, "Connection removal successful.");
    }

    /** Gets all the connected databases' names
     * @return Set of String of names
     */
    public Set<String> getConnectedDbNames(){
        return connectionPool.getConnectedDbNames();
    }

    /** Gets the global schema in a XML document content
     * @return String of XML document content
     */
    public String getGlobalSchemaXML(){
        return GlobalSchemaToXMLConverter.convert(currentGlobalSchema);
    }

    /** Adds a global relationship to the system
     * @param primaryKeyColumnId Column id for the primary key column
     * @param foreignKeyColumnId Column id for the foreign key column
     * @return ActionResult
     */
    public ActionResult addGlobalRelationship(String primaryKeyColumnId, String foreignKeyColumnId){
        boolean operationSuccess = false;
        try {
            GlobalRelationship globalRelationship = new GlobalRelationship(primaryKeyColumnId, foreignKeyColumnId);

            //this throws exception if such columns do not exist
            String typeName1 = currentGlobalSchema.getDatabaseByName(globalRelationship.getPrimaryKeyDB())
                    .getTableByName(globalRelationship.getPrimaryKeyTable())
                    .getColumnByName(globalRelationship.getPrimaryKeyColumn()).getDataType();
            String typeName2 = currentGlobalSchema.getDatabaseByName(globalRelationship.getForeignKeyDB())
                    .getTableByName(globalRelationship.getForeignKeyTable())
                    .getColumnByName(globalRelationship.getForeignKeyColumn()).getDataType();

            DBVendor pkDbVendor = currentGlobalSchema.getDatabaseByName(globalRelationship.getPrimaryKeyDB()).getDbVendor();
            DBVendor fkDbVendor = currentGlobalSchema.getDatabaseByName(globalRelationship.getForeignKeyDB()).getDbVendor();




            if(pkDbVendor == fkDbVendor){
                if(pkDbVendor == DBVendor.MYSQL){
                    if(metadataDb.getMysqlReverseTypeCompatibilityService().getCompatibleTypesForType(typeName1).contains(typeName2)){
                        operationSuccess = metadataDb.getGlobalRelationshipService().insert(globalRelationship);
                        //currentGlobalSchema.getGlobalRelationships().add(globalRelationship);

                    }
                }
                if(pkDbVendor == DBVendor.POSTGRESQL){
                    if(metadataDb.getPostgresReverseTypeCompatibilityServie().getCompatibleTypesForType(typeName1).contains(typeName2)){
                        operationSuccess = metadataDb.getGlobalRelationshipService().insert(globalRelationship);

                    }
                }
            }else {
                if(metadataDb.getPostgresMysqlTypeCompatibilityService().getAllTypesCompatibleWithType(typeName1).contains(typeName2)){
                    operationSuccess = metadataDb.getGlobalRelationshipService().insert(globalRelationship);

                }
            }

            currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);


        } catch (Exception e) {
            return new ActionResult(ActionResultType.FAILURE, e.getMessage());
        }
        if (operationSuccess)
            return new ActionResult(ActionResultType.SUCCESS, "Global relationship added.");
        else
            return new ActionResult(ActionResultType.FAILURE, "Failed to add relationship");
    }

    /** Removes a global relationship from the system
     * @param primaryKeyColumnId Column id for the primary key column
     * @param foreignKeyColumnId Column id for the foreign key column
     * @return ActionResult
     */
    public ActionResult removeGlobalRelationship(String primaryKeyColumnId, String foreignKeyColumnId){
        boolean operationSuccess = false;
        try {
            operationSuccess = metadataDb.getGlobalRelationshipService().delete(new GlobalRelationship(primaryKeyColumnId, foreignKeyColumnId));
            currentGlobalSchema = SchemaLoader.loadGlobalSchemaOnConnectionPool(connectionPool, metadataDb);

        } catch (Exception e) {
            return new ActionResult(ActionResultType.FAILURE, e.getMessage());
        }
        if (operationSuccess)
            return new ActionResult(ActionResultType.SUCCESS, "Global relationship removed.");
        else
            return new ActionResult(ActionResultType.FAILURE, "Global relationship not removed.");
    }

    /**
     * Executes global query given as text
     * @param queryText String containing the global query
     * @return ActionResult
     */
    public ActionResult executeGlobalQuery(String queryText){
        DataSet result = null;
        try {
            if(queryText.startsWith("SELECT"))
                result = executeGlobalSelectQuery(queryText);
            else if(queryText.startsWith("DELETE"))
                executeGlobalDeleteQuery(queryText);
            else if(queryText.startsWith("UPDATE"))
                executeGlobalUpdateQuery(queryText);
            else if(queryText.split("\r")[0].matches("(SUM|AVG|MIN|MAX|COUNT).*"))
                result = executeGlobalAggregateQuery(queryText);
            else
                return new ActionResult(ActionResultType.FAILURE, "Unknown query type.");

        } catch (LocalQueryExecutionException|QueryCreationException|ResultGlobalizationException e) {
            return new ActionResult(ActionResultType.FAILURE, e.getMessage());
        }
        if (result != null) {
            return new ActionResult(ActionResultType.SUCCESS, "Query successfully executed.", result.getCSV());
        }
        else{
            return new ActionResult(ActionResultType.SUCCESS, "Query successfully executed.", "");
        }
    }

    /** Executes global SELECT query given as text
     * @param queryText String containing a SELECT query
     * @throws QueryCreationException Thrown in case of failure to create query
     * @throws LocalQueryExecutionException Thrown in case of failure to execute local query
     * @throws ResultGlobalizationException Thrown in case of failure to globalize local results
     * @return DataSet containing the query results
     */
    private DataSet executeGlobalSelectQuery(String queryText) throws QueryCreationException, LocalQueryExecutionException, ResultGlobalizationException {
        GlobalSelectQuery globalSelectQuery = GlobalQueryFactory.createSelect(queryText, currentGlobalSchema, metadataDb);
        List<Join> globalJoins = globalSelectQuery.getJoins().stream().filter(q -> !q.isLocal()).collect(Collectors.toList());
        List<LocalSelectQuery> localizedQueries = QueryLocalizer.localizeGlobalQuery(globalSelectQuery, currentGlobalSchema);

        //just for testing verbose
        /*localizedQueries.forEach(q -> {
            if (q.getDbVendor() == DBVendor.POSTGRESQL)
                System.out.println(q.toPostgreSQLTextQuery());
            else
                System.out.println(q.toMysqlTextQuery());
        });*/


        List<ResultDataSet> results = LocalQueryRunner.runLocalSelectQueries(localizedQueries, connectionPool);

        //results.forEach(r -> System.out.println(r.getCSV()));


        ResultDataSet globalJoinedResult = ResultGlobalizer.joinByGlobal(results, globalJoins);

        //System.out.println(globalJoinedResult.getCSV());

        DataSet globalFilteredResult = ResultGlobalizer.globalFilter(globalJoinedResult, globalSelectQuery.getWhereClause());

        //System.out.println(globalFilteredResult.getCSV());

        DataSet globalProjectedResult = ResultGlobalizer.getGlobalProjection(globalFilteredResult, globalSelectQuery.getSelectColumnIds());

        //System.out.println(globalProjectedResult.getCSV());

        return globalProjectedResult;

    }

    /** Executes global DELETE query given as text
     * @param queryText String containing a DELETE query
     * @throws QueryCreationException Thrown in case of failure to create query
     * @throws LocalQueryExecutionException Thrown in case of failure to execute local query
     * @throws ResultGlobalizationException Thrown in case of failure to globalize local results
     */
    private void executeGlobalDeleteQuery(String queryText) throws QueryCreationException, LocalQueryExecutionException, ResultGlobalizationException {
        GlobalDeleteQuery globalDeleteQuery = GlobalQueryFactory.createDelete(queryText, currentGlobalSchema, metadataDb);
        List<Join> globalJoins = globalDeleteQuery.getJoins().stream().filter(q -> !q.isLocal()).collect(Collectors.toList());
        List<LocalSelectQuery> localizedQueries = QueryLocalizer.localizeGlobalQuery(globalDeleteQuery, currentGlobalSchema);

        //just for testing verbose
        /*localizedQueries.forEach(q -> {
            if (q.getDbVendor() == DBVendor.POSTGRESQL)
                System.out.println(q.toPostgreSQLTextQuery());
            else
                System.out.println(q.toMysqlTextQuery());
        });*/


        List<ResultDataSet> results = LocalQueryRunner.runLocalSelectQueries(localizedQueries, connectionPool);

        //results.forEach(r -> System.out.println(r.getCSV()));


        ResultDataSet globalJoinedResult = ResultGlobalizer.joinByGlobal(results, globalJoins);

        //System.out.println(globalJoinedResult.getCSV());

        DataSet globalFilteredResult = ResultGlobalizer.globalFilter(globalJoinedResult, globalDeleteQuery.getWhereClause());


        //create local delete and run
        String deleteTableId = globalDeleteQuery.getDeleteTableId();
        String dbName = globalDeleteQuery.getDeleteTableId().split("\\.")[0];
        String tableName = globalDeleteQuery.getDeleteTableId().split("\\.")[1];
        String deleteTablePkId = deleteTableId + "." + currentGlobalSchema.getTableById(deleteTableId).getPrimaryKey().getName();
        String localTablePkId = currentGlobalSchema.getTableById(deleteTableId).getName() + "." + currentGlobalSchema.getTableById(deleteTableId).getPrimaryKey().getName();

        List<Object> deleteRowsPks = globalFilteredResult.getColumn(deleteTablePkId).stream().distinct().collect(Collectors.toList());
        List<Condition> conditions = new ArrayList<>();
        List<LogicalOperator> logicalOperators = new ArrayList<>();
        for (Object deleteRowPk : deleteRowsPks){
            conditions.add(new Condition(localTablePkId, ComparisonOperator.EQUAL, deleteRowPk.toString()));
            logicalOperators.add(LogicalOperator.OR);

        }
        if (!logicalOperators.isEmpty())
            logicalOperators.remove(logicalOperators.size()-1);

        WhereClause whereClause = new WhereClause(conditions, logicalOperators);

        //no need to run query if nothing is found in select
        if (whereClause.getConditions().size() > 0) {
            LocalDeleteQuery localDeleteQuery = new LocalDeleteQuery(tableName, whereClause, dbName, currentGlobalSchema.getDatabaseByName(dbName).getDbVendor());
            LocalQueryRunner.runLocalDeleteQuery(localDeleteQuery, connectionPool.getFromPool(dbName));
        }



    }

    /** Executes global UPDATE query given as text
     * @param queryText String containing a UPDATE query
     * @throws QueryCreationException Thrown in case of failure to create query
     * @throws LocalQueryExecutionException Thrown in case of failure to execute local query
     * @throws ResultGlobalizationException Thrown in case of failure to globalize local results
     */
    private void executeGlobalUpdateQuery(String queryText) throws QueryCreationException, LocalQueryExecutionException, ResultGlobalizationException {
        GlobalUpdateQuery globalUpdateQuery = GlobalQueryFactory.createUpdate(queryText, currentGlobalSchema, metadataDb);
        List<Join> globalJoins = globalUpdateQuery.getJoins().stream().filter(q -> !q.isLocal()).collect(Collectors.toList());
        List<LocalSelectQuery> localizedQueries = QueryLocalizer.localizeGlobalQuery(globalUpdateQuery, currentGlobalSchema);

        //just for testing verbose
        /*localizedQueries.forEach(q -> {
            if (q.getDbVendor() == DBVendor.POSTGRESQL)
                System.out.println(q.toPostgreSQLTextQuery());
            else
                System.out.println(q.toMysqlTextQuery());
        });*/


        List<ResultDataSet> results = LocalQueryRunner.runLocalSelectQueries(localizedQueries, connectionPool);

        //results.forEach(r -> System.out.println(r.getCSV()));


        ResultDataSet globalJoinedResult = ResultGlobalizer.joinByGlobal(results, globalJoins);

        //System.out.println(globalJoinedResult.getCSV());

        DataSet globalFilteredResult = ResultGlobalizer.globalFilter(globalJoinedResult, globalUpdateQuery.getWhereClause());

        //create local update and run

        //get primary key values of row that need to be changed - create where clause
        String pkColumnId = globalUpdateQuery.getUpdateClause().getTableId() + "." + currentGlobalSchema.getTableById(globalUpdateQuery.getUpdateClause().getTableId()).getPrimaryKey().getName();
        String localTablePkId = pkColumnId.split("\\.",2)[1];
        List<Object> updateRowsPks = globalFilteredResult.getColumn(pkColumnId).stream().distinct().collect(Collectors.toList());
        List<Condition> conditions = new ArrayList<>();
        List<LogicalOperator> logicalOperators = new ArrayList<>();
        for (Object deleteRowPk : updateRowsPks){
            conditions.add(new Condition(localTablePkId, ComparisonOperator.EQUAL, deleteRowPk.toString()));
            logicalOperators.add(LogicalOperator.OR);

        }
        if (!logicalOperators.isEmpty())
            logicalOperators.remove(logicalOperators.size()-1);

        WhereClause whereClause = new WhereClause(conditions, logicalOperators);

        //create localized update clause
        String tableName = globalUpdateQuery.getUpdateClause().getTableId().split("\\.")[1];
        Map<String, String> columnValues = new HashMap<>();
        for (String columnId : globalUpdateQuery.getUpdateClause().getColumnValues().keySet()){
            String columnName = columnId.split("\\.")[2];
            columnValues.put(columnName, globalUpdateQuery.getUpdateClause().getColumnValues().get(columnId));
        }

        UpdateClause updateClause = new UpdateClause(tableName, columnValues);
        String dbName = globalUpdateQuery.getUpdateClause().getTableId().split("\\.")[0];

        //no need to run query if nothing is found in select
        if (whereClause.getConditions().size() > 0) {
            LocalUpdateQuery localUpdateQuery = new LocalUpdateQuery(updateClause, new ArrayList<>(), new ArrayList<>(), whereClause, dbName, currentGlobalSchema.getDatabaseByName(dbName).getDbVendor());
            LocalQueryRunner.runLocalUpdateQuery(localUpdateQuery, connectionPool.getFromPool(dbName));
        }
    }

    /** Executes global Aggregate query given as text
     * @param queryText String containing a UPDATE query
     * @throws QueryCreationException Thrown in case of failure to create query
     * @throws LocalQueryExecutionException Thrown in case of failure to execute local query
     * @throws ResultGlobalizationException Thrown in case of failure to globalize local results
     * @return DataSet with single aggregate column and single result row
     */
    private DataSet executeGlobalAggregateQuery(String queryText) throws QueryCreationException, LocalQueryExecutionException, ResultGlobalizationException{
        GlobalAggregateQuery globalAggregateQuery = GlobalQueryFactory.createAggregate(queryText, currentGlobalSchema, metadataDb);

        List<String> selectList = new ArrayList<>();
        selectList.add(globalAggregateQuery.getAggregateColumnId());
        GlobalSelectQuery globalSelectQuery = new GlobalSelectQuery(selectList, globalAggregateQuery.getFromTableIds(), globalAggregateQuery.getJoins(), globalAggregateQuery.getWhereClause());


        List<Join> globalJoins = globalAggregateQuery.getJoins().stream().filter(q -> !q.isLocal()).collect(Collectors.toList());
        List<LocalSelectQuery> localizedQueries = QueryLocalizer.localizeGlobalQuery(globalSelectQuery, currentGlobalSchema);

        //just for testing verbose
        /*
        localizedQueries.forEach(q -> {
            if (q.getDbVendor() == DBVendor.POSTGRESQL)
                System.out.println(q.toPostgreSQLTextQuery());
            else
                System.out.println(q.toMysqlTextQuery());
        });
        */

        List<ResultDataSet> results = LocalQueryRunner.runLocalSelectQueries(localizedQueries, connectionPool);

        //results.forEach(r -> System.out.println(r.getCSV()));


        ResultDataSet globalJoinedResult = ResultGlobalizer.joinByGlobal(results, globalJoins);

        //System.out.println(globalJoinedResult.getCSV());

        DataSet globalFilteredResult = ResultGlobalizer.globalFilter(globalJoinedResult, globalAggregateQuery.getWhereClause());


        Object aggResult = globalFilteredResult.runAggregationFunctionOnColumn(globalAggregateQuery.getAggregateColumnId(), globalAggregateQuery.getAggregationFunction());
        Map<String, Object> aggResultMap = new HashMap<>();
        aggResultMap.put(String.format("AGG(%s)", globalAggregateQuery.getAggregateColumnId()), aggResult);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        data.add(aggResultMap);

        return new DataSet(data);
    }

    /** Checks global relationship foreign key consistency
     * @param primaryKeyId Primary key column id
     * @param foreignKeyId Foreing key column id
     * @return ActionResult
     */
    public ActionResult checkGlobalRelationshipFkConsistency(String primaryKeyId, String foreignKeyId){
        if (currentGlobalSchema.doesGlobalRelationshipExist(primaryKeyId, foreignKeyId)){
            try {
                if (GlobalRelationshipForeignKeyConsistency.isConsistent(primaryKeyId, foreignKeyId, connectionPool)){
                    return new ActionResult(ActionResultType.SUCCESS, "Relationship is consistent");
                } else {
                    return new ActionResult(ActionResultType.FAILURE, "Relationship is inconsistent");
                }
            } catch (SQLException e) {
                return new ActionResult(ActionResultType.FAILURE, "Connection error during check.");
            }
        } else {
            return new ActionResult(ActionResultType.FAILURE, "No such global relationship exists");
        }


    }
}
