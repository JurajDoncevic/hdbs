package com.juraj.hdbs.schemaManagement;

import com.juraj.hdbs.connection.pool.ConnectionContext;
import com.juraj.hdbs.connection.pool.ConnectionContextMysql;
import com.juraj.hdbs.connection.pool.ConnectionContextPostgres;
import com.juraj.hdbs.connection.pool.ConnectionPool;
import com.juraj.hdbs.metadataDb.MetadataDb;
import com.juraj.hdbs.schemaManagement.metamodeling.*;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Relationship;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.schema.TableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Used to load a global schema from all the connected databases' local schemas
 * @author Juraj
 */
public class SchemaLoader {

    /** Static method that is used to load a relevant global schema from connected databases
     * @param connectionPool Connection pool containing all relevant database connections
     * @param metadataDb Metadata database object holding a connection to a database describing global metadata and settings
     * @throws Exception Thrown on connection pool failure
     * @return GlobalSchema object that holds the current status of the global schema
     */
    public static GlobalSchema loadGlobalSchemaOnConnectionPool(ConnectionPool connectionPool, MetadataDb metadataDb) throws Exception {
        List<Database> databases = new ArrayList<>();
        List<com.juraj.hdbs.schemaManagement.metamodeling.Table> tables = new ArrayList<>();
        List<com.juraj.hdbs.schemaManagement.metamodeling.Column> columns = new ArrayList<>();
        List<LocalRelationship> localRelationships = new ArrayList<>();

        for(String dbName : connectionPool.getConnectedDbNames()){

            ConnectionContext ctx = connectionPool.getFromPool(dbName);

            for(Table tbl : ctx.getDataContext().getSchemaByName(ctx.getDefaultSchemaName()).getTables(TableType.TABLE)){


                for (Column col : tbl.getColumns()){
                    boolean isForeignKey =  Arrays.asList(tbl.getForeignKeys()).contains(col);
                    columns.add(
                            new com.juraj.hdbs.schemaManagement.metamodeling.Column(col.getName(), col.getNativeType(),
                                    col.getColumnSize(), col.isPrimaryKey(), isForeignKey)
                    );

                }

                tables.add(new com.juraj.hdbs.schemaManagement.metamodeling.Table(tbl.getName(), columns));
                columns = new ArrayList<>();
            }

            for(Relationship rel : ctx.getDataContext().getSchemaByName(ctx.getDefaultSchemaName()).getRelationships()){
                Column pk_column = rel.getPrimaryColumns()[0];
                Column fk_column = rel.getForeignColumns()[0];
                localRelationships.add(
                        new LocalRelationship(dbName+"."+pk_column.getTable().getName()+"."+pk_column.getName(),
                                dbName+"."+fk_column.getTable().getName()+"."+ fk_column.getName())
                );
            }

            if (ctx.getClass().equals(ConnectionContextPostgres.class)){
                databases.add(new PostgresDatabase(dbName, tables, localRelationships));
            }
            if(ctx.getClass().equals(ConnectionContextMysql.class)){
                databases.add(new MysqlDatabase(dbName, tables, localRelationships));
            }


            tables = new ArrayList<>();
            localRelationships = new ArrayList<>();
        }

        //get global localRelationships
        List<GlobalRelationship> globalRelationships = metadataDb.getGlobalRelationshipService().getAll();

        GlobalSchema globalSchema = new GlobalSchema(databases, globalRelationships);



        return globalSchema;
    }
}
