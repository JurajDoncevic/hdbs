package com.juraj.hdbs.schemaManagement.metamodeling;

/** Represents a column of a table
 * @author Juraj
 */
public class Column {

    private String name;
    private String dataType;
    private int dataSize;
    private boolean isPrimaryKey;
    private boolean isForeignKey;

    /** Constructor
     * @param name Column name
     * @param dataType Type of data stored by the column
     * @param dataSize Size of data stored by the column
     * @param isPrimaryKey Determines if the column is a primary key column
     * @param isForeignKey Determines if the column is a foreign key column
     */
    public Column(String name, String dataType, int dataSize, boolean isPrimaryKey, boolean isForeignKey) {
        this.name = name;
        this.dataType = dataType.toUpperCase();
        this.dataSize = dataSize;
        this.isPrimaryKey = isPrimaryKey;
        this.isForeignKey = isForeignKey;
    }

    /** Gets the name of the column
     * @return A string of the column name
     */
    public String getName() {
        return name;
    }

    /** Gets the type of data stored by the column
     * @return A string of the data type
     */
    public String getDataType() {
        return dataType;
    }

    /** Gets the size of the data stored by the column; ex. for VARCHAR(255) returns 255
     * @return An integer of the size of data
     */
    public int getDataSize() {
        return dataSize;
    }

    /** Determines if this is a primary key column
     * @return Boolean
     */
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    /** Determines if this is a foreign key column
     * @return Boolean
     */
    public boolean isForeignKey() {
        return isForeignKey;
    }


}
