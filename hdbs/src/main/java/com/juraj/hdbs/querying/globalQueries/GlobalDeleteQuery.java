package com.juraj.hdbs.querying.globalQueries;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;

import java.util.List;

/** Global delete query class
 * @author Juraj
 */
public class GlobalDeleteQuery extends GlobalQuery {

    private String deleteTableId;

    /** Constructor
     * @param deleteTableId Table id of the DELETE clause
     * @param fromTableIds List of table ids in the FROM clause
     * @param joins List of joins
     * @param whereClause WHERE clause object
     */
    public GlobalDeleteQuery(String deleteTableId, List<String> fromTableIds, List<Join> joins, WhereClause whereClause) {
        super(fromTableIds, joins, whereClause);
        this.deleteTableId = deleteTableId;
    }

    /** Gets the table id from the DELETE clause
     * @return Table id
     */
    public String getDeleteTableId() {
        return deleteTableId;
    }
}
