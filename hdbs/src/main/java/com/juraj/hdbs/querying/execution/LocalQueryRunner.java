package com.juraj.hdbs.querying.execution;

import com.juraj.hdbs.connection.pool.ConnectionContext;
import com.juraj.hdbs.connection.pool.ConnectionPool;
import com.juraj.hdbs.querying.dataSets.ResultDataSet;
import com.juraj.hdbs.querying.localization.LocalDeleteQuery;
import com.juraj.hdbs.querying.localization.LocalSelectQuery;
import com.juraj.hdbs.querying.localization.LocalUpdateQuery;
import com.juraj.hdbs.utils.DBVendor;
import com.juraj.hdbs.utils.customExceptions.LocalQueryExecutionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Class that holds methods for running local queries
 * @author Juraj
 */
public class LocalQueryRunner {

    /** Runs given select queries on their respective connections
     * @param localQueries List of local queries
     * @param connectionPool Current connection pool
     * @return List of ResultDataSets
     * @throws LocalQueryExecutionException Thrown in case of an error in local execution
     */
    public static List<ResultDataSet> runLocalSelectQueries(List<LocalSelectQuery> localQueries, ConnectionPool connectionPool) throws LocalQueryExecutionException {
        List<ResultDataSet> resultDataSets = new ArrayList<>();
        try {
            for (LocalSelectQuery localSelectQuery : localQueries){
                if(localSelectQuery.getDbVendor() == DBVendor.POSTGRESQL){
                    ResultDataSet resultDataSet = new ResultDataSet(connectionPool.getFromPool(localSelectQuery.getDbName()).runQueryWithResult(localSelectQuery.toPostgreSQLTextQuery()), localSelectQuery.getDbName());
                    resultDataSets.add(resultDataSet);
                } else if (localSelectQuery.getDbVendor() == DBVendor.MYSQL){
                    ResultDataSet resultDataSet = new ResultDataSet(connectionPool.getFromPool(localSelectQuery.getDbName()).runQueryWithResult(localSelectQuery.toPostgreSQLTextQuery()), localSelectQuery.getDbName());
                    resultDataSets.add(resultDataSet);
            } else {
                throw new LocalQueryExecutionException("Invalid DB Vendor in local query.");
            }
        }
        } catch (SQLException e) {
            throw new LocalQueryExecutionException("Error during local query execution: " + e.getLocalizedMessage());
        }

        return resultDataSets;

    }

    /** Runs a local delete query on a connection from a given connection context
     * @param localDeleteQuery A local delete query
     * @param connectionContext Connection context for the local query
     * @throws LocalQueryExecutionException Thrown in case of an error in local execution
     */
    public static void runLocalDeleteQuery(LocalDeleteQuery localDeleteQuery, ConnectionContext connectionContext) throws LocalQueryExecutionException {
        try {
            String queryText = "";
            if (localDeleteQuery.getDbVendor() == DBVendor.MYSQL) {
                queryText = localDeleteQuery.toMysqlTextQuery();
            }
            else if (localDeleteQuery.getDbVendor() == DBVendor.POSTGRESQL){
                queryText = localDeleteQuery.toPostgreSQLTextQuery();
            }
            else
                throw new LocalQueryExecutionException("Invalid DB Vendor in local query.");

            //connectionContext.runQueryWithoutResult(queryText);
            System.out.println(queryText);
            connectionContext.runQueryWithoutResult("");
        } catch (SQLException e) {
            throw new LocalQueryExecutionException("Error during local query execution: " + e.getLocalizedMessage());
        }
    }

    /** Runs a local update query on a connection from a given connection context
     * @param localUpdateQuery A local update query
     * @param connectionContext Connection context for the local query
     * @throws LocalQueryExecutionException Thrown in case of an error in local execution
     */
    public static void runLocalUpdateQuery(LocalUpdateQuery localUpdateQuery, ConnectionContext connectionContext) throws LocalQueryExecutionException {
        try {
            String queryText = "";
            if (localUpdateQuery.getDbVendor() == DBVendor.MYSQL) {
                queryText = localUpdateQuery.toMysqlTextQuery();
            }
            else if (localUpdateQuery.getDbVendor() == DBVendor.POSTGRESQL){
                queryText = localUpdateQuery.toPostgreSQLTextQuery();
            }
            else
                throw new LocalQueryExecutionException("Invalid DB Vendor in local query.");

            //connectionContext.runQueryWithoutResult(queryText);
            System.out.println(queryText);
            connectionContext.runQueryWithoutResult("");
        } catch (SQLException e) {
            throw new LocalQueryExecutionException("Error during local query execution: " + e.getLocalizedMessage());
        }
    }
}
