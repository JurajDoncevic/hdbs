package com.juraj.hdbs.querying.localization;

import com.juraj.hdbs.querying.queryComponents.ComparisonOperator;
import com.juraj.hdbs.querying.queryComponents.Condition;
import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;
import com.juraj.hdbs.utils.DBVendor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Local delete query
 * @author Juraj
 */
public class LocalDeleteQuery extends LocalQuery {

    private String deleteTableName;

    /** Constructor
     * @param deleteTableName Table name from the DELETE clause
     * @param whereClause Local where clause
     * @param dbName Corresponding database name
     * @param dbVendor Vendor of corresponding database
     */
    public LocalDeleteQuery(String deleteTableName, WhereClause whereClause, String dbName, DBVendor dbVendor) {
        super(null, null, whereClause, dbName, dbVendor);
        this.deleteTableName = deleteTableName;
    }

    /** Gets the table name from the DELETE clause
     * @return Name of table
     */
    public String getDeleteTableName() {
        return deleteTableName;
    }

    /** Converts object to a PostgreSQL text query
     * @return String with query text
     */
    @Override
    public String toPostgreSQLTextQuery() {
        cleanQuery();
        String deleteClause = "";
        String whereClause = "";

        deleteClause = "DELETE FROM " + deleteTableName;
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



        return deleteClause + "\n" + whereClause + ";";
    }

    /** Converts object to a MySQL text query
     * @return String with query text
     */
    @Override
    public String toMysqlTextQuery() {
        cleanQuery();
        String deleteClause = "";
        String whereClause = "";

        deleteClause = "DELETE FROM " + deleteTableName;
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



        return deleteClause + "\n" + whereClause + ";";
    }
}
