package com.juraj.hdbs.querying.localization;

import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.querying.queryComponents.WhereClause;
import com.juraj.hdbs.utils.DBVendor;

import java.util.List;
import java.util.stream.Collectors;

/** Local query class
 * @author Juraj
 */
public abstract class LocalQuery {

    public List<String> fromTableIds;
    public List<Join> joins;
    public WhereClause whereClause;
    public String dbName;
    public DBVendor dbVendor;

    /** Constructor
     * @param fromTableIds Table ids in the FROM clause
     * @param joins Local joins
     * @param whereClause Local where clause
     * @param dbName Corresponding database name
     * @param dbVendor Vendor of corresponding database
     */
    public LocalQuery(List<String> fromTableIds, List<Join> joins, WhereClause whereClause, String dbName, DBVendor dbVendor) {
        this.fromTableIds = fromTableIds;
        this.joins = joins;
        this.whereClause = whereClause;
        this.dbName = dbName;
        this.dbVendor = dbVendor;
    }

    /** Clears repeated mentions from join and from clause
     */
    public void cleanQuery(){
        if (fromTableIds != null)
            fromTableIds = fromTableIds.stream().distinct().collect(Collectors.toList());
        if (joins != null)
            joins = joins.stream().distinct().collect(Collectors.toList());
    }

    /** Gets table ids in FROM clause
     * @return Table ids
     */
    public List<String> getFromTableIds() {
        return fromTableIds;
    }

    /** Gets the local joins
     * @return List of joins
     */
    public List<Join> getJoins() {
        return joins;
    }

    /** Gets the local WHERE clause
     * @return WHERE clause
     */
    public WhereClause getWhereClause() {
        return whereClause;
    }

    /** Gets the database vendor
     * @return Database vendor
     */
    public DBVendor getDbVendor() {
        return dbVendor;
    }

    /** Gets the corresponding database name
     * @return Database name
     */
    public String getDbName() {
        return dbName;
    }

    /** Converts object to a PostgreSQL text query
     * @return String with query text
     */
    public abstract String toPostgreSQLTextQuery();

    /** Converts object to a MySQL text query
     * @return String with query text
     */
    public abstract String toMysqlTextQuery();
}
