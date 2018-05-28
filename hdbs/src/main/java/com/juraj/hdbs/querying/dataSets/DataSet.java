package com.juraj.hdbs.querying.dataSets;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/** DataSet class used to store table data
 * @author Juraj
 */
public class DataSet {

    protected List<Map<String, Object>> data;

    protected Set<String> columnNames;


    /** Constructor
     */
    protected DataSet(){
        this.data = null;
        this.columnNames = null;
    }

    /** Constructor - creates data set with given data
     * @param data Data in a List of Maps
     */
    public DataSet(List<Map<String, Object>> data){
        if(data != null && !data.isEmpty()){
            this.columnNames = data.get(0).keySet();
        }
        this.data = data;
    }

    /** Constructor - creates an empty data set with specified column names
     * @param columnNames Names for columns used in this dataset
     */
    public DataSet(Set<String> columnNames){
        this.columnNames = columnNames;
        data = new ArrayList<>();
    }

    /** Adds a new row to the dataset
     * @param row Map of row
     */
    public void addRow(Map<String, Object> row){
        if (columnNames.containsAll(row.keySet()) && row.keySet().containsAll(columnNames)){
            data.add(row);
        }
    }

    /** Returns data in column with given name
     * @param columnName String of column name
     * @return List of objects representing data in the column
     */
    public List<Object> getColumn(String columnName){
        List<Object> column = null;

        if (columnName.contains(columnName)) {
            column = new ArrayList<>();

            for (Map<String, Object> value : data) {
                column.add(value.get(columnName));
            }
        }

        return column;
    }

    /** Removes row at index
     * @param index Index of row to be deleted
     */
    public void removeRowAt(int index){
        if (data.get(index) != null){
            data.remove(index);
        }
    }

    /** Clears all data from dataset - column names are preserved
     */
    public void clearDataSet(){
        data.clear();
    }

    /** Returns row at given index
     * @param index Index of row to be returned
     * @return Map of row
     */
    public Map<String, Object> getRow(int index){
        return data.get(index);
    }

    /** Returns data from dataset
     * @return List of Maps containing data from dataset
     */
    public List<Map<String, Object>> getData() {
        return data;
    }

    /** Returns column names in this dataset
     * @return Set of column names
     */
    public Set<String> getColumnNames() {
        return columnNames;
    }

    /** Checks if this dataset has a column with given name
     * @param columnName Name of column
     * @return True if the column exists, else False
     */
    public boolean hasColumnName(String columnName){
        return columnNames.contains(columnName);
    }

    /** Creates a projected dataset that just contains the columns with the given names
     * @param columnNames Projected column names
     * @return a new DataSet
     */
    public DataSet getProjectedDataSet(List<String> columnNames){
        for (String columnName : columnNames){
            if (!hasColumnName(columnName))
                columnNames.remove(columnName);
        }

        DataSet projectedDataSet = null;

        List<Map<String, Object>> projectedData = new ArrayList<>();
        for (Map<String, Object> row : data){

            Map<String, Object> projectedRow = new HashMap<>();

            for (String columnName : columnNames){
                projectedRow.put(columnName, row.get(columnName));
            }

            projectedData.add(projectedRow);
        }

        if (projectedData.size() > 0) {
            projectedDataSet = new DataSet(projectedData);
        } else {
            projectedDataSet = new DataSet(new HashSet<>(columnNames));
        }

        return projectedDataSet;
    }

    /** Exports DataSet as a CSV string with a header row
     * @return String containing the CSV
     */
    public String getCSV(){
        String csv = "";

        csv += columnNames.stream().collect(Collectors.joining(",")) + "\n";

        for (Map<String, Object> row : data){
            for (String key : row.keySet()){
                csv += row.get(key).toString() + ",";
            }
            csv = StringUtils.removeEnd(csv, ",");
            csv += "\n";
        }

        return csv;
    }

    /** Runs an aggregation function on a column the data set holds
     * @param columnName Column name from the data set
     * @param function Function that takes a list of objects and aggregates it to one object
     * @return Aggregation result (number or null if error)
     */
    public Object runAggregationFunctionOnColumn(String columnName, Function<List<Object>, Object> function){
        List<Object> agg_column = getColumn(columnName);
        return function.apply(agg_column);
    }
}
