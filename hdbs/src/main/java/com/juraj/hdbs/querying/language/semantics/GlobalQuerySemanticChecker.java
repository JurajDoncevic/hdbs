package com.juraj.hdbs.querying.language.semantics;

import com.juraj.hdbs.metadataDb.MetadataDb;
import com.juraj.hdbs.querying.globalQueries.*;
import com.juraj.hdbs.querying.graphing.JoinGraph;
import com.juraj.hdbs.querying.queryComponents.Condition;
import com.juraj.hdbs.querying.queryComponents.Join;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalSchema;
import com.juraj.hdbs.utils.DBVendor;
import com.juraj.hdbs.utils.customExceptions.SemanticAnalysisException;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/** Contains methods for semantic evaluation of global queries
 * @author Juraj
 */
//TODO: abstraction for select, delete, update
public class GlobalQuerySemanticChecker {

    /** Checks global select query semantics
     * @param globalSelectQuery Global select query
     * @param globalSchema Current global schema
     * @param metadataDb Metadata database service
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    public static void checkGlobalSelectSemantics(GlobalSelectQuery globalSelectQuery, GlobalSchema globalSchema, MetadataDb metadataDb) throws SemanticAnalysisException {

        checkFromValidity(globalSelectQuery, globalSchema);
        checkSelectValidity(globalSelectQuery, globalSchema);
        checkJoinValidity(globalSelectQuery, globalSchema, metadataDb);
        checkWhereValidity(globalSelectQuery, globalSchema);

    }

    /** Checks global delete query semantics
     * @param globalDeleteQuery Global delete query
     * @param globalSchema Current global schema
     * @param metadataDb Metadata database service
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    public static void checkGlobalDeleteSemantics(GlobalDeleteQuery globalDeleteQuery, GlobalSchema globalSchema, MetadataDb metadataDb) throws SemanticAnalysisException {
        checkFromValidity(globalDeleteQuery, globalSchema);
        checkJoinValidity(globalDeleteQuery, globalSchema, metadataDb);
        checkWhereValidity(globalDeleteQuery, globalSchema);
        checkDeleteValidity(globalDeleteQuery, globalSchema);
    }

    /** Checks global update query semantics
     * @param globalUpdateQuery Global select query
     * @param globalSchema Current global schema
     * @param metadataDb Metadata database service
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    public static void checkGlobalUpdateSemantics(GlobalUpdateQuery globalUpdateQuery, GlobalSchema globalSchema, MetadataDb metadataDb) throws SemanticAnalysisException {
        checkFromValidity(globalUpdateQuery, globalSchema);
        checkJoinValidity(globalUpdateQuery, globalSchema, metadataDb);
        checkWhereValidity(globalUpdateQuery, globalSchema);
        checkUpdateValidity(globalUpdateQuery, globalSchema);
    }

    /** Checks global aggregate query semantics
     * @param globalAggregateQuery Global select query
     * @param globalSchema Current global schema
     * @param metadataDb Metadata database service
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    public static void checkGlobalAggregateSemantics(GlobalAggregateQuery globalAggregateQuery, GlobalSchema globalSchema, MetadataDb metadataDb) throws SemanticAnalysisException {
        checkFromValidity(globalAggregateQuery, globalSchema);
        checkJoinValidity(globalAggregateQuery, globalSchema, metadataDb);
        checkWhereValidity(globalAggregateQuery, globalSchema);
        checkAggregateValidity(globalAggregateQuery, globalSchema, metadataDb);
    }

    /** Checks rules for the aggregate clause (SUM, AVG, MIN, MAX, AVG)
     * @param globalAggregateQuery Global update query
     * @param globalSchema Current global schema
     * @param metadataDb  Metadata database service
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    private static void checkAggregateValidity(GlobalAggregateQuery globalAggregateQuery, GlobalSchema globalSchema, MetadataDb metadataDb) throws SemanticAnalysisException {
        String aggregateColumnId = globalAggregateQuery.getAggregateColumnId();
        String aggregateColumnDataType;
        if (!globalSchema.doesColumnExist(aggregateColumnId))
            throw new SemanticAnalysisException(String.format("Column %s does not exist.", aggregateColumnId));

        aggregateColumnDataType = globalSchema.getColumnById(aggregateColumnId).getDataType();

        List<String> allowed_types = metadataDb.getTypeService().getAggregationAllowedTypes();


        if (!allowed_types.contains(aggregateColumnDataType)){
            throw new SemanticAnalysisException(String.format("Column %s is of type %s and it cannot be used in a aggregate function.", aggregateColumnId, aggregateColumnDataType));
        }
    }

    /** Checks rules for the UPDATE clause
     * @param globalUpdateQuery Global update query
     * @param globalSchema Current global schema
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    private static void checkUpdateValidity(GlobalUpdateQuery globalUpdateQuery, GlobalSchema globalSchema) throws SemanticAnalysisException {
        String tableId = globalUpdateQuery.getUpdateClause().getTableId();

        //check if table exist
        if (!globalSchema.doesTableExist(tableId)){
            throw new SemanticAnalysisException(String.format("UPDATE CLAUSE: Table with ID: %s does not exist.", tableId));
        }

        //check if table has columns
        for (String columnId : globalUpdateQuery.getUpdateClause().getColumnIds()) {
            try {
                String columnName = columnId.split("\\.")[2];
                if (!globalSchema.getTableById(tableId).hasColumnWithName(columnName)) {
                    throw new SemanticAnalysisException(String.format("UPDATE CLAUSE: Column with ID: %s does not exist.", columnId));
                }
            }catch (SemanticAnalysisException e){
                throw e;
            }catch (Exception e){//because of the split - should never be reached because of syntax
                throw new SemanticAnalysisException("UPDATE CLAUSE: Improper column ID detected.");
            }
        }
    }

    /** Checks rules for the DELETE clause
     * @param globalDeleteQuery Global delete query
     * @param globalSchema Current global schema
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    private static void checkDeleteValidity(GlobalDeleteQuery globalDeleteQuery, GlobalSchema globalSchema) throws SemanticAnalysisException {

        //check if delete table exists
        if(!globalSchema.doesTableExist(globalDeleteQuery.getDeleteTableId())){
            throw new SemanticAnalysisException(String.format("DELETE CLAUSE: Table with ID: %s does not exist.", globalDeleteQuery.getDeleteTableId()));
        }

        //check if delete table is in from tables
        if(!globalDeleteQuery.getFromTableIds().contains(globalDeleteQuery.getDeleteTableId())){
            throw  new SemanticAnalysisException(String.format("DELETE CLAUSE: Table in DELETE must be mentioned in FROM. Table with ID: %s not mentioned in FROM.", globalDeleteQuery.getDeleteTableId()));
        }
    }

    /** Checks rules for the SELECT clause
     * @param globalSelectQuery Global select query
     * @param globalSchema Current global schema
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    private static void checkSelectValidity(GlobalSelectQuery globalSelectQuery, GlobalSchema globalSchema) throws SemanticAnalysisException {
        List<String> referencedColumnIds = globalSelectQuery.getSelectColumnIds();
        List<String> referencedTableIds = globalSelectQuery.getFromTableIds();

        //are all columns selected
        if (globalSelectQuery.getSelectColumnIds().size() == 0)
            return;

        //do the columns exist
        for(String refColId : referencedColumnIds){
            if (!globalSchema.doesColumnExist(refColId)){
                throw new SemanticAnalysisException(String.format("SELECT CLAUSE: Column with ID: %s does not exist.", refColId));
            }
        }

        //are the given columns from the tables mentioned in the FROM clause
        for(String refColId : referencedColumnIds) {
            String[] splits = refColId.split("\\.");

            if (!referencedTableIds.contains(splits[0] + "." + splits[1])) {
                throw new SemanticAnalysisException(String.format("SELECT CLAUSE: Column with ID: %s is not located in any of the tables mentioned in the FROM clause", refColId));
            }
        }
    }

    /** Checks rules for the FROM clause
     * @param globalQuery Global query
     * @param globalSchema Current global schema
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    private static void checkFromValidity(GlobalQuery globalQuery, GlobalSchema globalSchema) throws SemanticAnalysisException {
        List<String> referencedTableIds = globalQuery.getFromTableIds();

        //do the tables exist
        for(String refTableId : referencedTableIds){
            if(!globalSchema.doesTableExist(refTableId)){
                throw new SemanticAnalysisException(String.format("FROM CLAUSE: Table with ID: %s does not exist.", refTableId));
            }
        }

        //are there repeated table names
        if(referencedTableIds.stream().distinct().count() != referencedTableIds.size()){
            throw new SemanticAnalysisException("FROM CLAUSE: Table IDs must not be repeated.");
        }
    }

    /** Checks rules for the JOIN clause
     * @param globalQuery Global query
     * @param globalSchema Current global schema
     * @param metadataDb Metadata database service
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    private static void checkJoinValidity(GlobalQuery globalQuery, GlobalSchema globalSchema, MetadataDb metadataDb) throws SemanticAnalysisException {
        List<Join> joins = globalQuery.getJoins();
        List<String> fromTableIds = globalQuery.getFromTableIds();

        //if there are no joins, no need to check validity
        if (joins.size() == 0){
            return;
        }

        //do these column ids exist
        for (Join join : joins){
            if (!globalSchema.doesColumnExist(join.getPrimaryKeyId()) || !globalSchema.doesColumnExist(join.getForeignKeyId())){
                throw new SemanticAnalysisException(String.format("JOIN CLAUSE: One of the columns with IDs: %s %s does not exist.", join.getPrimaryKeyId(), join.getForeignKeyId()));
            }
        }

        //are the columns located in the from tables
        for (Join join : joins){
            String[] splits = join.getPrimaryKeyId().split("\\.");
            if (!fromTableIds.contains(splits[0]+"."+splits[1])){
                throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Column with ID: %s does not appear in tables mentioned in the FROM CLAUSE", join.getPrimaryKeyId()));
            }
            splits = join.getForeignKeyId().split("\\.");
            if (!fromTableIds.contains(splits[0]+"."+splits[1])){
                throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Column with ID: %s does not appear in tables mentioned in the FROM CLAUSE", join.getForeignKeyId()));
            }
        }

        //are all from tables mentioned
        Set<String> mentionedTables = new HashSet<>();
        for (Join join: joins){
            String[] splits = join.getPrimaryKeyId().split("\\.");
            mentionedTables.add(splits[0]+"."+splits[1]);
            splits = join.getForeignKeyId().split("\\.");
            mentionedTables.add(splits[0]+"."+splits[1]);
        }
        for (String tableId : fromTableIds){
            if (!mentionedTables.contains(tableId)){
                throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Not all tables of the FROM clause are used: %s", tableId));
            }
        }


        //are the columns in a join from different tables
        for (Join join : joins){
            String[] splits = join.getPrimaryKeyId().split("\\.");
            String pkTable = splits[0]+"."+splits[1];

            splits = join.getForeignKeyId().split("\\.");
            String fkTable = splits[0]+"."+splits[1];

            if (pkTable.equals(fkTable)){
                throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Cannot join on same table: %s", fkTable));
            }
        }

        //are the columns type compatible
        /*for (Join join : joins){
            String[] splits = join.getPrimaryKeyId().split("\\.");
            String pkDb = splits[0];
            splits = join.getForeignKeyId().split("\\.");
            String fkDb = splits[0];

            String PkDataType = globalSchema.getColumnById(join.getPrimaryKeyId()).getDataType();
            String FkDataType = globalSchema.getColumnById(join.getForeignKeyId()).getDataType();

            //same vendor
            if (globalSchema.getDatabaseByName(pkDb).getDbVendor() == globalSchema.getDatabaseByName(fkDb).getDbVendor()){
                if (globalSchema.getDatabaseByName(pkDb).getDbVendor() == DBVendor.POSTGRESQL){
                    if (!metadataDb.getPostgresReverseTypeCompatibilityServie().getCompatibleTypesForType(PkDataType).contains(FkDataType)){
                        throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Incompatible PostgreSQL types: %s, %s", PkDataType, FkDataType));
                    }
                } else {
                    if (!metadataDb.getMysqlReverseTypeCompatibilityService().getCompatibleTypesForType(PkDataType).contains(FkDataType)){
                        throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Incompatible MySQL types: %s, %s", PkDataType, FkDataType));
                    }
                }
            } else {
                //different vendor
                if (globalSchema.getDatabaseByName(pkDb).getDbVendor() == DBVendor.POSTGRESQL){
                    if (metadataDb.getPostgresMysqlTypeCompatibilityService().getCompatibility(PkDataType, FkDataType) == null){
                        throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Incompatible PostgreSQL and MySQL types: %s, %s", PkDataType, FkDataType));
                    }
                } else {
                    if (metadataDb.getPostgresMysqlTypeCompatibilityService().getCompatibility(FkDataType, PkDataType) == null){
                        throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Incompatible MySQL and PostgreSQL types: %s, %s", PkDataType, FkDataType));
                    }
                }
            }
        }*/


        //are the columns part of relationships
        for (Join join : joins) {
            if (!globalSchema.doesRelationshipExist(join.getPrimaryKeyId(), join.getForeignKeyId())) {
                throw new SemanticAnalysisException(String.format("JOIN CLAUSE: No such relationship exists: %s", join.getPrimaryKeyId() + "-" + join.getForeignKeyId()));
            }
            ;
        }


        //repeated joins?
        for (int i = 0; i < joins.size(); i++)
            for (int j = i + 1; j < joins.size(); j++)
                if (joins.get(i).equals(joins.get(j))){
                    throw new SemanticAnalysisException(String.format("JOIN CLAUSE: All joins must be unique. This one is not: %s", joins.get(i)));
                }

        //cyclic join?
        JoinGraph joinGraph = new JoinGraph(joins);
        if (!joinGraph.isJoinGraphValid()){
           throw new SemanticAnalysisException(String.format("JOIN CLAUSE: Joins are cyclical or disconnected."));
        }

    }

    /** Checks rules for the WHERE clause
     * @param globalQuery Global query
     * @param globalSchema Current global schema
     * @throws SemanticAnalysisException Thrown in case of a semantic error
     */
    private static void checkWhereValidity(GlobalQuery globalQuery, GlobalSchema globalSchema) throws SemanticAnalysisException {
        List<String> mentionedColumns = new ArrayList<>();
        List<String> fromTableIds = globalQuery.getFromTableIds();

        for (Condition c : globalQuery.getWhereClause().getConditions()){
            mentionedColumns.add(c.getVariable());
        }

        //do the columns exist
        for (String colId : mentionedColumns){
            if (!globalSchema.doesColumnExist(colId)){
                throw new SemanticAnalysisException(String.format("WHERE CLAUSE: Column %s does not exist.", colId));
            }
        }

        //are the columns found in the from tables
        for (String colId : mentionedColumns){
            String[] splits = colId.split("\\.");
            if(!fromTableIds.contains(splits[0]+"."+splits[1])){
                throw new SemanticAnalysisException(String.format("WHERE CLAUSE: Column %s not found in tables mentioned in FROM clause", colId));
            }
        }
    }


}
