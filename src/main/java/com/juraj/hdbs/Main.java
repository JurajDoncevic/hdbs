package com.juraj.hdbs;

import com.juraj.hdbs.connectionManagement.ConnectionManager;
import com.juraj.hdbs.connectionManagement.credentials.DBCredentials;
import com.juraj.hdbs.connectionManagement.credentials.DBCredentialsMysql;
import com.juraj.hdbs.connectionManagement.credentials.DBCredentialsPostgres;
import com.juraj.hdbs.connectionManagement.pool.ConnectionContext;
import com.juraj.hdbs.connectionManagement.pool.ConnectionPool;
import com.juraj.hdbs.schemaManagement.GlobalSchemaExtractor;
import com.juraj.hdbs.schemaManagement.CurrentGlobalSchema;
import com.juraj.hdbs.schemaManagement.GlobalSchemaToXMLConverter;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.schema.TableType;


/**
 * Created by Juraj on 11.3.2018..
 */
public class Main {

    public static void main(String args[]){
        try {
            //set up credentials received from user
            DBCredentials credentialsPostgres = new DBCredentialsPostgres("localhost:5432", "postgres1", "postgres", "admin");
            DBCredentials credentialsMysql = new DBCredentialsMysql("localhost:3306", "mysql1", "root", "admin");
            DBCredentials credentialsPostgres2 = new DBCredentialsPostgres("localhost:5432", "postgres2", "postgres", "admin");
            DBCredentials credentialsMysql2 = new DBCredentialsMysql("localhost:3306", "mysql2", "root", "admin");

            //add connection - if OK add to ConnectionPool
            ConnectionManager.addDbConnection(credentialsPostgres);
            ConnectionManager.addDbConnection(credentialsMysql);
            ConnectionManager.addDbConnection(credentialsPostgres2);
            ConnectionManager.addDbConnection(credentialsMysql2);


            //extract and set current global schema
            CurrentGlobalSchema.setGlobalSchema(GlobalSchemaExtractor.extractGlobalSchemaOnConnectionPool());

            //export global schema to xml
            GlobalSchemaToXMLConverter converter = new GlobalSchemaToXMLConverter(CurrentGlobalSchema.getGlobalSchema());

            System.out.print(converter.convert());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printGlobalSchema(){
        ConnectionPool.getConnectedDbNames().forEach(db -> System.out.println(db));
        System.out.println("-----------------");

        for(String dbName : ConnectionPool.getConnectedDbNames()){
            ConnectionContext ctx = ConnectionPool.getFromPool(dbName);
            System.out.println("Database: " + dbName);
            for(Table tbl : ctx.getDataContext().getSchemaByName(ctx.getDefaultSchemaName()).getTables(TableType.TABLE)){
                System.out.println("\tTable: " + tbl.getName());

                for (Column col : tbl.getColumns()){
                    if(col.isPrimaryKey()){
                        System.out.println("\t\t(PK)" + col.getName() + ":" + col.getNativeType().toUpperCase());
                    }
                    else {
                        System.out.println("\t\t" + col.getName() + ":" + col.getNativeType() + " " + col.getColumnSize().toString());
                    }

                }
            }
        }
    }
}
