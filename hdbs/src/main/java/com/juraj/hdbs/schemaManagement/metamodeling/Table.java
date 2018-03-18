package com.juraj.hdbs.schemaManagement.metamodeling;


import java.util.ArrayList;
import java.util.List;

/** Represents a table in a database
 * @author Juraj
 */
public class Table {

    private String name;
    private List<Column> columns;

    /** Constructor
     * @param name Name of the table
     * @param columns List of columns in the table
     */
    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    /** Gets the table name
     * @return String of the table's name
     */
    public String getName() {
        return name;
    }

    /** Gets all the table's columns
     * @return List of Column
     */
    public List<Column> getColumns() {
        return columns;
    }

    /** Gets the primary key column of the table
     * @return Column object or null if none exist
     */
    public Column getPrimaryKey(){
        for(Column col : columns){
            if (col.isPrimaryKey()){
                return col;
            }
        }
        return null;
    }

    /** Gets all the foreign key columns of the table
     * @return List of Column
     */
    public List<Column> getForeignKeys(){
        ArrayList<Column> cols = new ArrayList<>();
        for(Column col : columns){
            if(col.isForeignKey()){
                cols.add(col);
            }
        }

        return cols;
    }

    /** Gets a column by its name
     * @param columnName Name of searched column
     * @return Column object or null if none exist
     */
    public Column getColumnByName(String columnName){
        for(Column col : columns){
            if(col.getName().equals(columnName)){
                return col;
            }
        }

        return null;
    }
}
