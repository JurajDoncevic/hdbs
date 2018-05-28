package com.juraj.hdbs.querying.localization;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;
import com.juraj.hdbs.utils.DBVendor;
import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/** Local select query class
 * @author Juraj
 */
public class LocalSelectQuery extends LocalQuery{

    private List<String> selectedColumnIds;

    /** Constructor
     * @param fromTableIds Table ids in the FROM clause
     * @param joins Local joins
     * @param whereClause Local where clause
     * @param selectedColumnIds Columns in SELECT clause
     * @param dbName Corresponding database name
     * @param dbVendor Vendor of corresponding database
     */
    public LocalSelectQuery(List<String> fromTableIds, List<Join> joins, WhereClause whereClause, List<String> selectedColumnIds, String dbName, DBVendor dbVendor) {
        super(fromTableIds, joins, whereClause, dbName, dbVendor);
        this.selectedColumnIds = selectedColumnIds;
    }

    /** Clears repeated mentions from join, select and from clause
     */
    @Override
    public void cleanQuery(){
        if (fromTableIds != null)
            fromTableIds = fromTableIds.stream().distinct().collect(Collectors.toList());
        if (selectedColumnIds != null)
            selectedColumnIds = selectedColumnIds.stream().distinct().collect(Collectors.toList());
        if (joins != null)
            joins = joins.stream().distinct().collect(Collectors.toList());
    }

    /** Gets column ids from SELECT clause
     * @return Column ids
     */
    public List<String> getSelectedColumnIds() {
        return selectedColumnIds;
    }

    /** Converts object to a PostgreSQL text query
     * @return String with query text
     */
    @Override
    public String toPostgreSQLTextQuery(){
        cleanQuery();
        String selectClause = "";
        String fromClause = "";
        String whereClause = "";

        if (!selectedColumnIds.isEmpty()){
            selectClause = "SELECT " + selectedColumnIds.stream().collect(Collectors.joining(", "));
        }else {
            selectClause = "SELECT *";
        }

        fromClause = "FROM " + fromTableIds.stream().collect(Collectors.joining(", "));

        if(!joins.isEmpty())
            whereClause = "WHERE " + joins.stream().map(j -> j.getPrimaryKeyLocalized() + "=" + j.getForeignKeyLocalized()).collect(Collectors.joining(" AND "));

        return selectClause + "\n" + fromClause + "\n" + whereClause + ";";
    }

    /** Converts object to a MySQL text query
     * @return String with query text
     */
    @Override
    public String toMysqlTextQuery(){
        cleanQuery();
        String selectClause = "";
        String fromClause = "";
        String whereClause = "";

        if (!selectedColumnIds.isEmpty()){
            selectClause = "SELECT " + selectedColumnIds.stream().collect(Collectors.joining(", "));
        }else {
            selectClause = "SELECT *";
        }

        fromClause = "FROM " + fromTableIds.stream().collect(Collectors.joining(", "));

        if(!joins.isEmpty())
            whereClause = "WHERE " + joins.stream().map(j -> j.getPrimaryKeyLocalized() + "=" + j.getForeignKeyLocalized()).collect(Collectors.joining(" AND "));


        return selectClause + "\n" + fromClause + "\n" + whereClause + ";";
    }
}
