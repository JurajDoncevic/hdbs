package com.juraj.hdbs.querying.queryComponents;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** Class of an UPDATE clause for a query
 * @author Juraj
 */
public class UpdateClause {

    private String tableId;
    private Map<String, String> columnValues;

    /** Constructor
     * @param tableId Id of table to be updated
     * @param columnValues Map of column names and the corresponding update values
     */
    public UpdateClause(String tableId, Map<String, String> columnValues) {
        this.tableId = tableId;
        this.columnValues = columnValues;
    }

    /** Gets the table id
     * @return Table id
     */
    public String getTableId() {
        return tableId;
    }

    /** Gets a map of column and their corresponding update values
     * @return Map of column values
     */
    public Map<String, String> getColumnValues() {
        return columnValues;
    }

    /** Gets set of columns to be updated
     * @return Column ids
     */
    public Set<String> getColumnIds(){
        return columnValues.keySet();
    }
}
