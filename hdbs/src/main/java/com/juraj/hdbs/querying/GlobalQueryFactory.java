package com.juraj.hdbs.querying;

import com.juraj.hdbs.metadataDb.MetadataDb;
import com.juraj.hdbs.querying.globalQueries.*;
import com.juraj.hdbs.querying.language.semantics.GlobalQuerySemanticChecker;
import com.juraj.hdbs.querying.queryComponents.*;
import com.juraj.hdbs.schemaManagement.metamodeling.Column;
import com.juraj.hdbs.schemaManagement.metamodeling.GlobalSchema;
import com.juraj.hdbs.utils.customExceptions.QueryCreationException;
import com.juraj.hdbs.querying.language.GlobalSQLLexer;
import com.juraj.hdbs.querying.language.GlobalSQLParser;
import com.juraj.hdbs.querying.language.VerboseListener;
import com.juraj.hdbs.utils.customExceptions.SemanticAnalysisException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** Factory class for creation of global queries from a GlobalSQL query text
 * @author Juraj
 */
public class GlobalQueryFactory {

    /** Returns a newly created GlobalSelectQuery object by lexing, parsing and semantically analyzing the given query text
     * @param queryText Text of a query written in GlobalSQL
     * @param globalSchema Current global schema
     * @param metadataDb Meatadata database service
     * @return GlobalSelectQuery object
     * @throws QueryCreationException Thrown on syntax or semantic errors
     */
    public static GlobalSelectQuery createSelect(String queryText, GlobalSchema globalSchema, MetadataDb metadataDb) throws QueryCreationException{

        GlobalSQLLexer lexer = new GlobalSQLLexer(CharStreams.fromString(queryText));

        VerboseListener errorListener = new VerboseListener();
        GlobalSQLParser parser = new GlobalSQLParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        GlobalSQLParser.Select_queryContext ctx = parser.select_query();


        //Check for syntax errors
        if (errorListener.hasErrors()){
            String errorString = "";
            for(String error : errorListener.getSyntaxErrors()){
                errorString += error;
            }
            throw new QueryCreationException(errorString);
        }


        GlobalSelectQuery globalSelectQuery = createSelectQueryObject(ctx, globalSchema);

        try {
            GlobalQuerySemanticChecker.checkGlobalSelectSemantics(globalSelectQuery, globalSchema, metadataDb);
        } catch (SemanticAnalysisException e) {
            throw new QueryCreationException("SEMANTIC ERROR IN " + e.getMessage());
        }

        return globalSelectQuery;
    }

    /** Helper method that instantiates a GlobalSelectQuery object after a successful analysis
     * @param ctx Select query context of the GlobalSQL parser
     * @param globalSchema current global schema
     * @return GlobalSelectQuery object
     */
    private static GlobalSelectQuery createSelectQueryObject(GlobalSQLParser.Select_queryContext ctx, GlobalSchema globalSchema){
        List<String> selectColumnIds = new ArrayList<>();
        List<String> fromTableIds = new ArrayList<>();
        List<Join> joins = new ArrayList<>();
        List<Condition> conditions = new ArrayList<>();
        List<LogicalOperator> operators = new ArrayList<>();
        WhereClause whereClause = null;

        //add from table ids
        if(ctx.from_clause().TBLID() != null){
            for(TerminalNode tblId : ctx.from_clause().TBLID()){
                fromTableIds.add(tblId.toString());
            }
        }

        //add select
        if(ctx.select_clause().COLID() != null){
            //if there is a *
            if (ctx.select_clause().ALLCOLS() != null){
                for (String fromTableId : fromTableIds){
                    for (Column column : globalSchema.getTableById(fromTableId).getColumns()){
                        selectColumnIds.add(fromTableId+"."+column.getName());
                    }
                }
            }else {
                //there is a specific list
                for (TerminalNode colId : ctx.select_clause().COLID()) {
                    selectColumnIds.add(colId.toString());
                }
            }


        } else {
            //all columns '*'
            //passing empty list
        }

        //add joins
        if(ctx.join_clause() != null){
            for(GlobalSQLParser.Join_clauseContext joinCtx : ctx.join_clause()){
                String pkId = joinCtx.COLID(0).toString();
                String fkId = joinCtx.COLID(1).toString();

                joins.add(new Join(pkId, fkId));
            }
        }

        //add where
        if(ctx.where_clause() != null){
            //there is a where clause
            for(GlobalSQLParser.ComparisonContext compareCtx : ctx.where_clause().comparison()){
                ComparisonOperator comparisonOperator;

                if (compareCtx.number_comparison_operator() != null){
                    //comparing a number

                    switch (compareCtx.number_comparison_operator().getStart().getText()){
                        case "=" : comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=" : comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        case ">=" : comparisonOperator = ComparisonOperator.GREATER_OR_EQUAL;
                            break;
                        case "<=" : comparisonOperator = ComparisonOperator.LESSER_OR_EQUAL;
                            break;
                        case  ">" : comparisonOperator = ComparisonOperator.GREATER;
                            break;
                        case "<" : comparisonOperator = ComparisonOperator.LESSER;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                } else {
                    //comparing a string
                    switch (compareCtx.string_comparison_operator().getStart().getText()) {
                        case "=":
                            comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=":
                            comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                }

                String value;
                if(compareCtx.number_value() != null){
                    value = compareCtx.number_value().getStart().getText();
                } else {
                    value = compareCtx.string_value().getStart().getText();
                }

                conditions.add(new Condition(compareCtx.COLID().toString(), comparisonOperator, value));
            }

            if(ctx.where_clause().logical_operator() != null){
                //there is more than one condition
                for (GlobalSQLParser.Logical_operatorContext logOpCtx : ctx.where_clause().logical_operator()){
                    LogicalOperator logicalOperator;
                    switch (logOpCtx.toString()){
                        case "AND" : logicalOperator = LogicalOperator.AND;
                            break;
                        case "OR" : logicalOperator = LogicalOperator.OR;
                            break;
                        default: logicalOperator = LogicalOperator.AND;
                    }

                    operators.add(logicalOperator);
                }

            }

            whereClause = new WhereClause(conditions, operators);
        }

        GlobalSelectQuery globalQuery = new GlobalSelectQuery(selectColumnIds, fromTableIds, joins, whereClause);

        return globalQuery;
    }

    /** Returns a newly created GlobalDeleteQuery object by lexing, parsing and semantically analyzing the given query text
     * @param queryText Text of a query written in GlobalSQL
     * @param globalSchema Current global schema
     * @param metadataDb Meatadata database service
     * @return GlobalDeleteQuery object
     * @throws QueryCreationException Thrown on syntax or semantic errors
     */
    public static GlobalDeleteQuery createDelete(String queryText, GlobalSchema globalSchema, MetadataDb metadataDb) throws QueryCreationException {
        GlobalSQLLexer lexer = new GlobalSQLLexer(CharStreams.fromString(queryText));

        VerboseListener errorListener = new VerboseListener();
        GlobalSQLParser parser = new GlobalSQLParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        GlobalSQLParser.Delete_queryContext ctx = parser.delete_query();

        //Check for syntax errors
        if (errorListener.hasErrors()){
            String errorString = "";
            for(String error : errorListener.getSyntaxErrors()){
                errorString += error;
            }
            throw new QueryCreationException(errorString);
        }

        GlobalDeleteQuery globalDeleteQuery = createDeleteQueryObject(ctx);

        try {
            GlobalQuerySemanticChecker.checkGlobalDeleteSemantics(globalDeleteQuery, globalSchema, metadataDb);
        } catch (SemanticAnalysisException e) {
            throw new QueryCreationException("SEMANTIC ERROR IN " + e.getMessage());
        }

        return globalDeleteQuery;
    }

    /** Helper method that instantiates a GlobalDeleteQuery object after a successful analysis
     * @param ctx Delete query context of the GlobalSQL parser
     * @return GlobalDeleteQuery object
     */
    private static GlobalDeleteQuery createDeleteQueryObject(GlobalSQLParser.Delete_queryContext ctx) {
        String deleteTableId = "";
        List<String> fromTableIds = new ArrayList<>();
        List<Join> joins = new ArrayList<>();
        List<Condition> conditions = new ArrayList<>();
        List<LogicalOperator> operators = new ArrayList<>();
        WhereClause whereClause = null;

        //add from table ids
        if(ctx.from_clause().TBLID() != null){
            for(TerminalNode tblId : ctx.from_clause().TBLID()){
                fromTableIds.add(tblId.toString());
            }
        }

        //add delete
        if(ctx.delete_clause().TBLID() != null){
            //there is a specific list
            deleteTableId = ctx.delete_clause().TBLID().toString();
        } else {
            //should not be reached
        }

        //add joins
        if(ctx.join_clause() != null){
            for(GlobalSQLParser.Join_clauseContext joinCtx : ctx.join_clause()){
                String pkId = joinCtx.COLID(0).toString();
                String fkId = joinCtx.COLID(1).toString();

                joins.add(new Join(pkId, fkId));
            }
        }

        //add where
        if(ctx.where_clause() != null){
            //there is a where clause
            for(GlobalSQLParser.ComparisonContext compareCtx : ctx.where_clause().comparison()){
                ComparisonOperator comparisonOperator;

                if (compareCtx.number_comparison_operator() != null){
                    //comparing a number

                    switch (compareCtx.number_comparison_operator().getStart().getText()){
                        case "=" : comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=" : comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        case ">=" : comparisonOperator = ComparisonOperator.GREATER_OR_EQUAL;
                            break;
                        case "<=" : comparisonOperator = ComparisonOperator.LESSER_OR_EQUAL;
                            break;
                        case  ">" : comparisonOperator = ComparisonOperator.GREATER;
                            break;
                        case "<" : comparisonOperator = ComparisonOperator.LESSER;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                } else {
                    //comparing a string
                    switch (compareCtx.string_comparison_operator().getStart().getText()) {
                        case "=":
                            comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=":
                            comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                }

                String value;
                if(compareCtx.number_value() != null){
                    value = compareCtx.number_value().getStart().getText();
                } else {
                    value = compareCtx.string_value().getStart().getText();
                }

                conditions.add(new Condition(compareCtx.COLID().toString(), comparisonOperator, value));
            }

            if(ctx.where_clause().logical_operator() != null){
                //there is more than one condition
                for (GlobalSQLParser.Logical_operatorContext logOpCtx : ctx.where_clause().logical_operator()){
                    LogicalOperator logicalOperator;
                    switch (logOpCtx.toString()){
                        case "AND" : logicalOperator = LogicalOperator.AND;
                            break;
                        case "OR" : logicalOperator = LogicalOperator.OR;
                            break;
                        default: logicalOperator = LogicalOperator.AND;
                    }

                    operators.add(logicalOperator);
                }

            }

            whereClause = new WhereClause(conditions, operators);
        }

        GlobalDeleteQuery globalQuery = new GlobalDeleteQuery(deleteTableId, fromTableIds, joins, whereClause);

        return globalQuery;
    }

    /** Returns a newly created GlobalUpdateQuery object by lexing, parsing and semantically analyzing the given query text
     * @param queryText Text of a query written in GlobalSQL
     * @param globalSchema Current global schema
     * @param metadataDb Meatadata database service
     * @return GlobalUpdateQuery object
     * @throws QueryCreationException Thrown on syntax or semantic errors
     */
    public static GlobalUpdateQuery createUpdate(String queryText, GlobalSchema globalSchema, MetadataDb metadataDb) throws QueryCreationException {
        GlobalSQLLexer lexer = new GlobalSQLLexer(CharStreams.fromString(queryText));

        VerboseListener errorListener = new VerboseListener();
        GlobalSQLParser parser = new GlobalSQLParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        GlobalSQLParser.Update_queryContext ctx = parser.update_query();

        //Check for syntax errors
        if (errorListener.hasErrors()){
            String errorString = "";
            for(String error : errorListener.getSyntaxErrors()){
                errorString += error;
            }
            throw new QueryCreationException(errorString);
        }

        GlobalUpdateQuery globalUpdateQuery = createUpdateQueryObject(ctx);

        try {
            GlobalQuerySemanticChecker.checkGlobalUpdateSemantics(globalUpdateQuery, globalSchema, metadataDb);
        } catch (SemanticAnalysisException e) {
            throw new QueryCreationException("SEMANTIC ERROR IN " + e.getMessage());
        }

        return globalUpdateQuery;
    }

    /** Helper method that instantiates a GlobalUpdateQuery object after a successful analysis
     * @param ctx Update query context of the GlobalSQL parser
     * @return GlobalUpdateQuery object
     */
    private static GlobalUpdateQuery createUpdateQueryObject(GlobalSQLParser.Update_queryContext ctx){
        UpdateClause updateClause = new UpdateClause("", new HashMap<String, String>());
        List<String> fromTableIds = new ArrayList<>();
        List<Join> joins = new ArrayList<>();
        List<Condition> conditions = new ArrayList<>();
        List<LogicalOperator> operators = new ArrayList<>();
        WhereClause whereClause = null;

        //add from table ids
        if(ctx.from_clause().TBLID() != null){
            for(TerminalNode tblId : ctx.from_clause().TBLID()){
                fromTableIds.add(tblId.toString());
            }
        }

        //add update
        if(ctx.update_clause().TBLID() != null){
            //get table ID of target for update
            String tableId = ctx.update_clause().TBLID().toString();

            //values (...)
            Map<String, String> columnValues = new HashMap<>();

            //get column IDs of target
            List<String> columnIds = new ArrayList<>();
            if (ctx.update_clause().COLID() != null){
                for (TerminalNode colId : ctx.update_clause().COLID()){
                    columnIds.add(colId.toString());
                }
            }
            //get values
            if (ctx.update_clause().value() != null){
                int i = 0;
                for (GlobalSQLParser.ValueContext valueContext : ctx.update_clause().value()){
                    String value = valueContext.getStart().getText();
                    columnValues.put(columnIds.get(i), value);
                    i++;
                }
            }
            updateClause = new UpdateClause(tableId, columnValues);
        } else {
            //should not be reached
        }


        //add joins
        if(ctx.join_clause() != null){
            for(GlobalSQLParser.Join_clauseContext joinCtx : ctx.join_clause()){
                String pkId = joinCtx.COLID(0).toString();
                String fkId = joinCtx.COLID(1).toString();

                joins.add(new Join(pkId, fkId));
            }
        }

        //add where
        if(ctx.where_clause() != null){
            //there is a where clause
            for(GlobalSQLParser.ComparisonContext compareCtx : ctx.where_clause().comparison()){
                ComparisonOperator comparisonOperator;

                if (compareCtx.number_comparison_operator() != null){
                    //comparing a number

                    switch (compareCtx.number_comparison_operator().getStart().getText()){
                        case "=" : comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=" : comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        case ">=" : comparisonOperator = ComparisonOperator.GREATER_OR_EQUAL;
                            break;
                        case "<=" : comparisonOperator = ComparisonOperator.LESSER_OR_EQUAL;
                            break;
                        case  ">" : comparisonOperator = ComparisonOperator.GREATER;
                            break;
                        case "<" : comparisonOperator = ComparisonOperator.LESSER;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                } else {
                    //comparing a string
                    switch (compareCtx.string_comparison_operator().getStart().getText()) {
                        case "=":
                            comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=":
                            comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                }

                String value;
                if(compareCtx.number_value() != null){
                    value = compareCtx.number_value().getStart().getText();
                } else {
                    value = compareCtx.string_value().getStart().getText();
                }

                conditions.add(new Condition(compareCtx.COLID().toString(), comparisonOperator, value));
            }

            if(ctx.where_clause().logical_operator() != null){
                //there is more than one condition
                for (GlobalSQLParser.Logical_operatorContext logOpCtx : ctx.where_clause().logical_operator()){
                    LogicalOperator logicalOperator;
                    switch (logOpCtx.toString()){
                        case "AND" : logicalOperator = LogicalOperator.AND;
                            break;
                        case "OR" : logicalOperator = LogicalOperator.OR;
                            break;
                        default: logicalOperator = LogicalOperator.AND;
                    }

                    operators.add(logicalOperator);
                }

            }

            whereClause = new WhereClause(conditions, operators);
        }

        GlobalUpdateQuery globalQuery = new GlobalUpdateQuery(updateClause, fromTableIds, joins, whereClause);

        return globalQuery;
    }

    /** Returns a newly created GlobalAggregateQuery object by lexing, parsing and semantically analyzing the given query text
     * @param queryText Text of a query written in GlobalSQL
     * @param globalSchema Current global schema
     * @param metadataDb Meatadata database service
     * @return GlobalAggregateQuery object
     * @throws QueryCreationException Thrown on syntax or semantic errors
     */
    public static GlobalAggregateQuery createAggregate(String queryText, GlobalSchema globalSchema, MetadataDb metadataDb) throws QueryCreationException {
        GlobalSQLLexer lexer = new GlobalSQLLexer(CharStreams.fromString(queryText));

        VerboseListener errorListener = new VerboseListener();
        GlobalSQLParser parser = new GlobalSQLParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        GlobalSQLParser.Aggregate_queryContext ctx = parser.aggregate_query();

        //Check for syntax errors
        if (errorListener.hasErrors()){
            String errorString = "";
            for(String error : errorListener.getSyntaxErrors()){
                errorString += error;
            }
            throw new QueryCreationException(errorString);
        }

        GlobalAggregateQuery globalAggregateQuery = createAggregateQueryObject(ctx);

        try {
            GlobalQuerySemanticChecker.checkGlobalAggregateSemantics(globalAggregateQuery, globalSchema, metadataDb);
        } catch (SemanticAnalysisException e) {
            throw new QueryCreationException("SEMANTIC ERROR IN " + e.getMessage());
        }

        return globalAggregateQuery;
    }

    /** Helper method that instantiates a GlobalAggregateQuery object after a successful analysis
     * @param ctx Update query context of the GlobalSQL parser
     * @throws QueryCreationException Thrown on creation error
     * @return GlobalAggregateQuery object
     */
    private static GlobalAggregateQuery createAggregateQueryObject(GlobalSQLParser.Aggregate_queryContext ctx) throws QueryCreationException {
        String aggregateColumnId = "";
        List<String> fromTableIds = new ArrayList<>();
        List<Join> joins = new ArrayList<>();
        List<Condition> conditions = new ArrayList<>();
        List<LogicalOperator> operators = new ArrayList<>();
        WhereClause whereClause = null;

        //add from table ids
        if(ctx.from_clause().TBLID() != null){
            for(TerminalNode tblId : ctx.from_clause().TBLID()){
                fromTableIds.add(tblId.toString());
            }
        }


        //add joins
        if(ctx.join_clause() != null){
            for(GlobalSQLParser.Join_clauseContext joinCtx : ctx.join_clause()){
                String pkId = joinCtx.COLID(0).toString();
                String fkId = joinCtx.COLID(1).toString();

                joins.add(new Join(pkId, fkId));
            }
        }

        //add where
        if(ctx.where_clause() != null){
            //there is a where clause
            for(GlobalSQLParser.ComparisonContext compareCtx : ctx.where_clause().comparison()){
                ComparisonOperator comparisonOperator;

                if (compareCtx.number_comparison_operator() != null){
                    //comparing a number

                    switch (compareCtx.number_comparison_operator().getStart().getText()){
                        case "=" : comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=" : comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        case ">=" : comparisonOperator = ComparisonOperator.GREATER_OR_EQUAL;
                            break;
                        case "<=" : comparisonOperator = ComparisonOperator.LESSER_OR_EQUAL;
                            break;
                        case  ">" : comparisonOperator = ComparisonOperator.GREATER;
                            break;
                        case "<" : comparisonOperator = ComparisonOperator.LESSER;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                } else {
                    //comparing a string
                    switch (compareCtx.string_comparison_operator().getStart().getText()) {
                        case "=":
                            comparisonOperator = ComparisonOperator.EQUAL;
                            break;
                        case "!=":
                            comparisonOperator = ComparisonOperator.NOT_EQUAL;
                            break;
                        default: comparisonOperator = ComparisonOperator.EQUAL; //this shouldn't be reached
                    }
                }

                String value;
                if(compareCtx.number_value() != null){
                    value = compareCtx.number_value().getStart().getText();
                } else {
                    value = compareCtx.string_value().getStart().getText();
                }

                conditions.add(new Condition(compareCtx.COLID().toString(), comparisonOperator, value));
            }

            if(ctx.where_clause().logical_operator() != null){
                //there is more than one condition
                for (GlobalSQLParser.Logical_operatorContext logOpCtx : ctx.where_clause().logical_operator()){
                    LogicalOperator logicalOperator;
                    switch (logOpCtx.toString()){
                        case "AND" : logicalOperator = LogicalOperator.AND;
                            break;
                        case "OR" : logicalOperator = LogicalOperator.OR;
                            break;
                        default: logicalOperator = LogicalOperator.AND;
                    }

                    operators.add(logicalOperator);
                }

            }

            whereClause = new WhereClause(conditions, operators);
        }

        GlobalAggregateQuery globalAggregateQuery = null;
        //add aggregate
        if (ctx.aggregate_clause().COLID() != null){
            aggregateColumnId = ctx.aggregate_clause().COLID().getText();

            if (ctx.aggregate_clause().MAX() != null){
                globalAggregateQuery = new GlobalMaxAggregateQuery(aggregateColumnId, fromTableIds, joins, whereClause);
            }
            else if (ctx.aggregate_clause().MIN() != null){
                globalAggregateQuery = new GlobalMinAggregateQuery(aggregateColumnId, fromTableIds, joins, whereClause);
            }
            else if (ctx.aggregate_clause().AVG() != null){
                globalAggregateQuery = new GlobalAvgAggregateQuery(aggregateColumnId, fromTableIds, joins, whereClause);
            }
            else if (ctx.aggregate_clause().COUNT() != null){
                globalAggregateQuery = new GlobalCountAggregateQuery(aggregateColumnId, fromTableIds, joins, whereClause);
            }
            else if (ctx.aggregate_clause().SUM() != null){
                globalAggregateQuery = new GlobalSumAggregateQuery(aggregateColumnId, fromTableIds, joins, whereClause);
            } else {
                throw new QueryCreationException("Proper aggregate function could not be established");
            }
        }



        return globalAggregateQuery;
    }



}
