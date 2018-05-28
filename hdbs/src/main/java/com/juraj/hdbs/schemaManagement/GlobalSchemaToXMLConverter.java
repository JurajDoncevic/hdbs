package com.juraj.hdbs.schemaManagement;

import com.juraj.hdbs.schemaManagement.metamodeling.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/** Used to convert global schema to XML in order to be passed to other service or applications
 * @author Juraj
 */
public class GlobalSchemaToXMLConverter {

    /** Static method used to convert given global schema into a XML format
     * @param globalSchema A GlobalSchema object to be converted
     * @return String with the content of a prettified XML document describing the schema
     */
    public static String convert(GlobalSchema globalSchema){
        Document document = new Document();

        Element globalSchemaElement = new Element("hdbs");

        Element databasesElement = new Element("databases");
        for(Database db : globalSchema.getDatabases()){
            Element databaseElement = new Element("database");
            databaseElement.setAttribute("id", db.getName());
            databaseElement.setAttribute("name", db.getName());
            databaseElement.setAttribute("dbVendor", db.getDbVendor().toString().toLowerCase());

            Element tablesElement = new Element("tables");
            for(Table tbl : db.getTables()){
                Element tableElement = new Element("table");
                tableElement.setAttribute("id", db.getName()+"."+tbl.getName());
                tableElement.setAttribute("name", tbl.getName());

                Element columnsElement = new Element("columns");
                for(Column col : tbl.getColumns()){
                    Element columnElement = new Element("column");
                    columnElement.setAttribute("id", db.getName()+"."+tbl.getName()+"." + col.getName());
                    columnElement.setAttribute("name", col.getName());
                    columnElement.setAttribute("dataType", col.getDataType());
                    columnElement.setAttribute("dataSize", String.valueOf(col.getDataSize()));
                    columnElement.setAttribute("isPrimaryKey", String.valueOf(col.isPrimaryKey()));
                    columnElement.setAttribute("isForeignKey", String.valueOf(col.isForeignKey()));

                    columnsElement.addContent(columnElement);
                }
                tableElement.addContent(columnsElement);

                tablesElement.addContent(tableElement);
            }
            databaseElement.addContent(tablesElement);

            Element relationshipsElement = new Element("relationships");
            for(LocalRelationship rel : db.getLocalRelationships()){
                Element relationshipElement = new Element("relationship");
                relationshipElement.setAttribute("pkColumnId", rel.getPrimaryKeyId());
                relationshipElement.setAttribute("fkColumnId", rel.getForeignKeyId());

                relationshipsElement.addContent(relationshipElement);
            }
            databaseElement.addContent(relationshipsElement);

            databasesElement.addContent(databaseElement);
        }
        globalSchemaElement.addContent(databasesElement);

        Element globalRelationshipsElement = new Element("globalRelationships");
        for(GlobalRelationship globalRelationship : globalSchema.getGlobalRelationships()){
            Element globalRelationshipElement = new Element("globalRelationship");
            globalRelationshipElement.setAttribute("pkColumnId", globalRelationship.getPrimaryKeyId());
            globalRelationshipElement.setAttribute("fkColumnId", globalRelationship.getForeignKeyId());

            globalRelationshipsElement.addContent(globalRelationshipElement);
        }
        globalSchemaElement.addContent(globalRelationshipsElement);

        document.addContent(globalSchemaElement);

        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());

        String output = outputter.outputString(document);

        return output;
    }
}
