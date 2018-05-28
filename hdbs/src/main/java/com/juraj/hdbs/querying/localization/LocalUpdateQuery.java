package com.juraj.hdbs.querying.localization;

import com.juraj.hdbs.querying.queryComponents.*;
import com.juraj.hdbs.utils.DBVendor;

import java.util.List;

/** Local update query class
 * @author Juraj
 */
public class LocalUpdateQuery extends LocalQuery {
    private UpdateClause updateClause;

    /** Constructor
     * @param updateClause UPDATE clause
     * @param fromTableIds Table ids in the FROM clause
     * @param joins Local joins
     * @param whereClause Local where clause
     * @param dbName Corresponding database name
     * @param dbVendor Vendor of corresponding database
     */
    public LocalUpdateQuery(UpdateClause updateClause, List<String> fromTableIds, List<Join> joins, WhereClause whereClause, String dbName, DBVendor dbVendor) {
        super(fromTableIds, joins, whereClause, dbName, dbVendor);
        this.updateClause = updateClause;
    }

    /** Gets UPDATE clause
     * @return Update clause
     */
    public UpdateClause getUpdateClause() {
        return updateClause;
    }

    /** Converts object to a PostgreSQL text query
     * @return String with query text
     */
    @Override
    public String toPostgreSQLTextQuery() {
        String whereClause = "";
        String updateClause = "";

        updateClause = "UPDATE " + this.updateClause.getTableId() + " SET ";
        for (String columnName : this.updateClause.getColumnValues().keySet()){
            String value = this.updateClause.getColumnValues().get(columnName);
            updateClause += String.format("%s=%s, ", columnName, value);
        }
        updateClause = updateClause.substring(0, updateClause.length() - 2);


        whereClause = "WHERE";
        for (int i = 0; i < this.whereClause.getConditions().size(); i++) {
            if (i > 0) {
                whereClause += " " + this.whereClause.getLogicalOperators().get(i - 1).name();
            }
            Condition condition = this.whereClause.getConditions().get(i);

            if (condition.getComparisonOperator() == ComparisonOperator.EQUAL) {
                whereClause += " " + condition.getVariable() + "=" + condition.getValue();
            } else if (condition.getComparisonOperator() == ComparisonOperator.NOT_EQUAL) {
                whereClause += " " + condition.getVariable() + "!=" + condition.getValue();
            } else {
                whereClause += " " + condition.getVariable() + condition.getComparisonOperator().getValue() + condition.getValue();
            }
        }
        return updateClause + "\n" + whereClause + ";";
    }

    /** Converts object to a MySQL text query
     * @return String with query text
     */
    @Override
    public String toMysqlTextQuery() {
        String whereClause = "";
        String updateClause = "";

        updateClause = "UPDATE " + this.updateClause.getTableId() + " SET ";
        for (String columnName : this.updateClause.getColumnValues().keySet()){
            String value = this.updateClause.getColumnValues().get(columnName);
            updateClause += String.format("%s=%s, ", columnName, value);
        }
        updateClause = updateClause.substring(0, updateClause.length() - 2);


        whereClause = "WHERE";
        for (int i = 0; i < this.whereClause.getConditions().size(); i++) {
            if (i > 0) {
                whereClause += " " + this.whereClause.getLogicalOperators().get(i - 1).name();
            }
            Condition condition = this.whereClause.getConditions().get(i);

            if (condition.getComparisonOperator() == ComparisonOperator.EQUAL) {
                whereClause += " " + condition.getVariable() + "=" + condition.getValue();
            } else if (condition.getComparisonOperator() == ComparisonOperator.NOT_EQUAL) {
                whereClause += " " + condition.getVariable() + "!=" + condition.getValue();
            } else {
                whereClause += " " + condition.getVariable() + condition.getComparisonOperator().getValue() + condition.getValue();
            }
        }
        return updateClause + "\n" + whereClause + ";";
    }
}
