package com.juraj.hdbs.schemaManagement;

import com.juraj.hdbs.connectionManagement.pool.ConnectionContext;
import com.juraj.hdbs.connectionManagement.pool.ConnectionPool;
import com.juraj.hdbs.schemaManagement.metamodeling.*;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Relationship;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.schema.TableType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juraj on 13.3.2018..
 */
public class GlobalSchemaExtractor {

    public static GlobalSchema extractGlobalSchemaOnConnectionPool(){
        List<Database> databases = new ArrayList<>();
        List<com.juraj.hdbs.schemaManagement.metamodeling.Table> tables = new ArrayList<>();
        List<com.juraj.hdbs.schemaManagement.metamodeling.Column> columns = new ArrayList<>();
        List<com.juraj.hdbs.schemaManagement.metamodeling.Relationship> relationships = new ArrayList<>();

        for(String dbName : ConnectionPool.getConnectedDbNames()){

            ConnectionContext ctx = ConnectionPool.getFromPool(dbName);

            for(Table tbl : ctx.getDataContext().getSchemaByName(ctx.getDefaultSchemaName()).getTables(TableType.TABLE)){


                for (Column col : tbl.getColumns()){
                    columns.add(
                            new com.juraj.hdbs.schemaManagement.metamodeling.Column(col.getName(), col.getNativeType(),
                                    col.getColumnSize(), col.isPrimaryKey(), false)
                    );

                }

                tables.add(new com.juraj.hdbs.schemaManagement.metamodeling.Table(tbl.getName(), columns));
                columns = new ArrayList<>();
            }

            for(Relationship rel : ctx.getDataContext().getSchemaByName(ctx.getDefaultSchemaName()).getRelationships()){
                Column pk_column = rel.getPrimaryColumns()[0];
                Column fk_column = rel.getForeignColumns()[0];
                relationships.add(
                        new com.juraj.hdbs.schemaManagement.metamodeling.Relationship(pk_column.getName(),
                                rel.getPrimaryTable().getName(),fk_column.getName(), rel.getForeignTable().getName())
                );
            }

            databases.add(new Database(dbName, tables, relationships));

            tables = new ArrayList<>();
            relationships = new ArrayList<>();
        }

        GlobalSchema globalSchema = new GlobalSchema(databases);

        return globalSchema;
    }
}
