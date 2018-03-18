package com.juraj.hdbs.metadataDb.Models;

/** Model class used to describe a compatibility of two types from a PostgreSQL and MySQL database
 * @author Juraj
 */
public class PostgresMysqlTypeCompatibilityModel {

    private String postgresTypeName;
    private String mysqlTypeName;

    /** Constructor
     * @param postgresTypeName Name of a data type from a PostgreSQL database
     * @param mysqlTypeName Name of a data type from a MySQL database
     */
    public PostgresMysqlTypeCompatibilityModel(String postgresTypeName, String mysqlTypeName) {
        this.postgresTypeName = postgresTypeName;
        this.mysqlTypeName = mysqlTypeName;
    }

    /** Gets the PostgreSQL data type name in this compatibility
     * @return String with data type name
     */
    public String getPostgresTypeName() {
        return postgresTypeName;
    }

    /** Gets the MySQL data type name in this compatibility
     * @return String with data type name
     */
    public String getMysqlTypeName() {
        return mysqlTypeName;
    }
}
