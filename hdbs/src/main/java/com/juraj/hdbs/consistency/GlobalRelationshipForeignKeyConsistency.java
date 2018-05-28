package com.juraj.hdbs.consistency;

import com.juraj.hdbs.connection.pool.ConnectionPool;
import com.juraj.hdbs.querying.dataSets.ResultDataSet;

import java.sql.SQLException;
import java.util.List;

/** Class that holds methods for foreign key consistency checks
 * @author Juraj
 */
public class GlobalRelationshipForeignKeyConsistency {

    /** Checks if the given global relationship is consistent
     * @param primaryKeyId Id of the relationships primary key column
     * @param foreignKeyId Id of the relationships foreign key column
     * @param connectionPool Current connection pool
     * @return True if the relationship is consistent; else false
     * @throws SQLException In case of a connection problem
     */
    public static boolean isConsistent(String primaryKeyId, String foreignKeyId, ConnectionPool connectionPool) throws SQLException {
        String[] split = primaryKeyId.split("\\.");
        String pkDb = split[0];
        String pkTable = split[1];
        String pkCol = split[2];

        String pkQuery = String.format("SELECT %s FROM %s;", pkCol, pkTable);

        split = foreignKeyId.split("\\.");
        String fkDb = split[0];
        String fkTable = split[1];
        String fkCol = split[2];

        String fkQuery = String.format("SELECT %s FROM %s;", fkCol, fkTable);


        ResultDataSet pkDs = new ResultDataSet(connectionPool.getFromPool(pkDb).runQueryWithResult(pkQuery), pkDb);
        ResultDataSet fkDs = new ResultDataSet(connectionPool.getFromPool(fkDb).runQueryWithResult(fkQuery), fkDb);

        String pkColName = pkDs.getColumnNames().toArray()[0].toString();
        String fkColName = fkDs.getColumnNames().toArray()[0].toString();

        List<Object> pks = pkDs.getColumn(pkColName);
        List<Object> fks = fkDs.getColumn(fkColName);

        for (Object fk : fks){
            if (fk != null){
                if (!pks.contains(fk))
                    return false;
            }
        }

        return true;
    }
}
