package com.juraj.hdbs.schemaManagement.metamodeling;

import com.juraj.hdbs.Utils.DBVendor;

import java.util.List;

/** Representation of a Mysql database
 * @author Juraj
 */
public class MysqlDatabase extends Database {

    private DBVendor dbVendor;

    /** Constructor
     * @param name Name of the database
     * @param tables List of tables in the database
     * @param relationships List of relationships among the tables in the database
     */
    public MysqlDatabase(String name, List<Table> tables, List<Relationship> relationships) {
        super(name, tables, relationships);
        dbVendor = DBVendor.MYSQL;
    }

    /** Gets the database vendor
     * @return MYSQL value of DBVendor enum
     */
    @Override
    public DBVendor getDbVendor() {
        return dbVendor;
    }
}
