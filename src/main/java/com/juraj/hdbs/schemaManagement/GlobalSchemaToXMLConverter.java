package com.juraj.hdbs.schemaManagement;

import com.juraj.hdbs.schemaManagement.metamodeling.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;

/**
 * Created by Juraj on 13.3.2018..
 */
public class GlobalSchemaToXMLConverter {

    private GlobalSchema globalSchema;

    public GlobalSchemaToXMLConverter(GlobalSchema globalSchema){
        this.globalSchema = globalSchema;
    }

    public GlobalSchema getGlobalSchema(){
        return  globalSchema;
    }

    public String convert(){
        Document document = new Document();

        Element globalSchemaElement = new Element("hdbs");

        for(Database db : globalSchema.getDatabases()){
            Element databaseElement = new Element("database");
            databaseElement.setAttribute("id", db.getName());
            databaseElement.setAttribute("name", db.getName());

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
                    columnElement.setAttribute("primaryKey", String.valueOf(col.isPrimaryKey()));
                    columnElement.setAttribute("foreignKey", String.valueOf(col.isForeignKey()));

                    columnsElement.addContent(columnElement);
                }
                tableElement.addContent(columnsElement);

                tablesElement.addContent(tableElement);
            }
            databaseElement.addContent(tablesElement);

            Element relationshipsElement = new Element("relationships");
            for(Relationship rel : db.getRelationships()){
                Element relationshipElement = new Element("relationship");
                relationshipElement.setAttribute("pkColumnId", db.getName()+"."+rel.getPrimaryKeyTableName()+"."+ rel.getPrimaryKeyColumnName());
                relationshipElement.setAttribute("fkColumnId", db.getName()+"."+rel.getForeignKeyTableName()+"."+ rel.getForeignKeyColumnName());

                relationshipsElement.addContent(relationshipElement);
            }
            databaseElement.addContent(relationshipsElement);


            globalSchemaElement.addContent(databaseElement);
        }
        document.addContent(globalSchemaElement);

        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());

        String output = outputter.outputString(document);
        /*try {
            outputter.output(document, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return output;
    }
}
