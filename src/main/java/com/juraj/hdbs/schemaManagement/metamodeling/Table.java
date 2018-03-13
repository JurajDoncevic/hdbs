package com.juraj.hdbs.schemaManagement.metamodeling;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj on 13.3.2018..
 */
public class Table {

    private String name;
    private List<Column> columns;

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Column getPrimaryKey(){
        for(Column col : columns){
            if (col.isPrimaryKey()){
                return col;
            }
        }
        return null;
    }

    public List<Column> getForeignKeys(){
        ArrayList<Column> cols = new ArrayList<>();
        for(Column col : columns){
            if(col.isForeignKey()){
                cols.add(col);
            }
        }

        return cols;
    }

    public Column getColumnByName(String columnName){
        for(Column col : columns){
            if(col.getName().equals(columnName)){
                return col;
            }
        }

        return null;
    }
}
