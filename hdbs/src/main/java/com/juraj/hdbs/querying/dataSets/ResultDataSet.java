package com.juraj.hdbs.querying.dataSets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/** Data set class used to store data recovered by queries
 * @author Juraj
 */
public class ResultDataSet extends DataSet{

    private List<String> containedTableIds;

    /** Constructor - creates data set populated with given data
     * @param data Data in a List of Maps
     * @param containedTableIds List of table ids contained in this data set
     */
    public ResultDataSet(List<Map<String, Object>> data, List<String> containedTableIds) {
        super(data);
        this.containedTableIds = containedTableIds;
    }

    /** Constructor - creates empty data set with given column names and that references given table ids
     * @param colNames Column names in the data set
     * @param containedTableIds Ids of tables contained in this data set
     */
    public ResultDataSet(Set<String> colNames, List<String> containedTableIds) {
        super(colNames);
        this.containedTableIds = containedTableIds;
    }

    /** Constructor - creates a data set from a java.sql.ResultSet
     * @param resultSet The given ResultSet
     * @param databaseName Name of database that the data has been retrieved from
     * @throws SQLException Thrown in case of a sql error on the ResultSet
     */
    public ResultDataSet(ResultSet resultSet, String databaseName) throws SQLException {
        super();

        int numCols = resultSet.getMetaData().getColumnCount();

        Map<String, Integer> colNamesIdx = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();

        for (int i = 1; i <= numCols; i++){
            colNamesIdx.put(databaseName + "." + resultSet.getMetaData().getTableName(i) + "." + resultSet.getMetaData().getColumnName(i), i);
        }

        while (resultSet.next()){
            Map<String, Object> row = new HashMap<>();
            for (String colName : colNamesIdx.keySet()){
                row.put(colName, resultSet.getObject(colNamesIdx.get(colName)));
            }

            data.add(row);
        };

        this.data = data;
        this.columnNames = colNamesIdx.keySet();
        this.containedTableIds = columnNames.stream().map(c -> c.split("\\.")[0] + c.split("\\.")[1]).collect(Collectors.toList());
    }

    /** Gets contained table ids
     * @return List of strings of contained table ids
     */
    public List<String> getContainedTableIds() {
        return containedTableIds;
    }

    /** Equi-joins current ResultDataSet (this one containing the foreign key) with another into one ResultDataSet
     * @param foreignKeyColumnName Foreign key column name
     * @param primaryKeyColumnName Primary key column name
     * @param primaryKeyResultSet Result set containing the primary key
     * @return Joined ResultDataSet
     */
    public ResultDataSet joinWithResultDataSet(String foreignKeyColumnName, String primaryKeyColumnName, ResultDataSet primaryKeyResultSet){

        ResultDataSet resultDataSet = null;
        if (columnNames.contains(foreignKeyColumnName) && primaryKeyResultSet.getColumnNames().contains(primaryKeyColumnName)){
            List<String> joinedContainedTables = new ArrayList<>();
            joinedContainedTables.addAll(containedTableIds);
            joinedContainedTables.addAll(primaryKeyResultSet.getContainedTableIds());

            Set<String> joinedColNames = new HashSet<>();
            joinedColNames.addAll(getColumnNames());
            joinedColNames.addAll(primaryKeyResultSet.getColumnNames());

            List<Map<String, Object>> joinedData = new ArrayList<>();

            for (Map<String, Object> row1 : data){
                boolean joinedRowExists = false;
                if(row1.get(foreignKeyColumnName) != null){
                    for (Map<String, Object> row2 : primaryKeyResultSet.getData()){
                        if (row1.get(foreignKeyColumnName).equals(row2.get(primaryKeyColumnName))){
                            joinedRowExists = true;
                            Map<String, Object> newRow = new HashMap<>();
                            for (String key : row1.keySet()){
                                newRow.put(key, row1.get(key));
                            }
                            for (String key : row2.keySet()){
                                newRow.put(key, row2.get(key));
                            }
                            joinedData.add(newRow);
                        }
                    }
                }

                if(!joinedRowExists){
                    Map<String, Object> newRow = new HashMap<>();
                    for (String key : row1.keySet()){
                        newRow.put(key, row1.get(key));
                    }
                    for (String key : primaryKeyResultSet.getColumnNames()){
                        newRow.put(key, null);
                    }
                    joinedData.add(newRow);
                }
            }

            resultDataSet = new ResultDataSet(joinedData, joinedContainedTables);
        }

        return resultDataSet;
    }
}
