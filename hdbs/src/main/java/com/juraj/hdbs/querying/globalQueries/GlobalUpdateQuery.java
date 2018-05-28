package com.juraj.hdbs.querying.globalQueries;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.UpdateClause;
import com.juraj.hdbs.querying.queryComponents.WhereClause;

import java.util.List;

/** Global update query class
 * @author Juraj
 */
public class GlobalUpdateQuery extends GlobalQuery{
    private UpdateClause updateClause;

    /** Constructor
     * @param updateClause UPDATE clause object
     * @param fromTableIds List of table ids in the FROM clause
     * @param joins List of joins
     * @param whereClause WHERE clause object
     */
    public GlobalUpdateQuery(UpdateClause updateClause, List<String> fromTableIds, List<Join> joins, WhereClause whereClause) {
        super(fromTableIds, joins, whereClause);
        this.updateClause = updateClause;
    }

    /** Gets the Update clause
     * @return Update clause object
     */
    public UpdateClause getUpdateClause() {
        return updateClause;
    }
}
