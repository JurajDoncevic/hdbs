package com.juraj.hdbs.querying.globalQueries;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Global select query class
 * @author Juraj
 */
public class GlobalSelectQuery extends GlobalQuery{

    private List<String> selectColumnIds;

    /** Constructor
     * @param selectColumnIds List of column ids of the SELECT clause
     * @param fromTableIds List of table ids in the FROM clause
     * @param joins List of joins
     * @param whereClause WHERE clause object
     */
    public GlobalSelectQuery(List<String> selectColumnIds, List<String> fromTableIds, List<Join> joins, WhereClause whereClause) {
        super(fromTableIds, joins, whereClause);
        this.selectColumnIds = selectColumnIds;
    }


    /** Gets the columns ids of the SELECT clause
     * @return List of column ids
     */
    public List<String> getSelectColumnIds() {
        return selectColumnIds;
    }

    /** Gets the mentioned column ids that belong to a given table with id
     * @param tableId Table id
     * @return List of columns ids
     */
    public List<String> getRequiredColumnIdsForTable(String tableId){
        List<String> requiredSelect = new ArrayList<>();
        List<String> bySelect = selectColumnIds.stream().filter(c -> c.contains(tableId)).collect(Collectors.toList());
        List<String> byFkInJoins = getJoins().stream().filter(c -> c.getForeignKeyId().contains(tableId)).map(c -> c.getForeignKeyId()).collect(Collectors.toList());
        requiredSelect.addAll(bySelect);
        requiredSelect.addAll(byFkInJoins);

        return requiredSelect;
    }
}
