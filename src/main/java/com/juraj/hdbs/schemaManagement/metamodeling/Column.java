package com.juraj.hdbs.schemaManagement.metamodeling;

/**
 * Created by Juraj on 13.3.2018..
 */
public class Column {

    private String name;
    private String dataType;
    private int dataSize;
    private boolean isPrimaryKey;
    private boolean isForeignKey;

    public Column(String name, String dataType, int dataSize, boolean isPrimaryKey, boolean isForeignKey) {
        this.name = name;
        this.dataType = dataType.toUpperCase();
        this.dataSize = dataSize;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public int getDataSize() {
        return dataSize;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isForeignKey() {
        return isForeignKey;
    }


}
