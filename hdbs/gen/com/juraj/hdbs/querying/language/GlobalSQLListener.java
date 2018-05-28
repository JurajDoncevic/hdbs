// Generated from D:/Diplomski/hdbs/src/main/java/com/juraj/hdbs/querying/language\GlobalSQL.g4 by ANTLR 4.7
package com.juraj.hdbs.querying.language;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GlobalSQLParser}.
 */
public interface GlobalSQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#number_value}.
	 * @param ctx the parse tree
	 */
	void enterNumber_value(GlobalSQLParser.Number_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#number_value}.
	 * @param ctx the parse tree
	 */
	void exitNumber_value(GlobalSQLParser.Number_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#string_value}.
	 * @param ctx the parse tree
	 */
	void enterString_value(GlobalSQLParser.String_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#string_value}.
	 * @param ctx the parse tree
	 */
	void exitString_value(GlobalSQLParser.String_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(GlobalSQLParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(GlobalSQLParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#number_comparison_operator}.
	 * @param ctx the parse tree
	 */
	void enterNumber_comparison_operator(GlobalSQLParser.Number_comparison_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#number_comparison_operator}.
	 * @param ctx the parse tree
	 */
	void exitNumber_comparison_operator(GlobalSQLParser.Number_comparison_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#string_comparison_operator}.
	 * @param ctx the parse tree
	 */
	void enterString_comparison_operator(GlobalSQLParser.String_comparison_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#string_comparison_operator}.
	 * @param ctx the parse tree
	 */
	void exitString_comparison_operator(GlobalSQLParser.String_comparison_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(GlobalSQLParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(GlobalSQLParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#logical_operator}.
	 * @param ctx the parse tree
	 */
	void enterLogical_operator(GlobalSQLParser.Logical_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#logical_operator}.
	 * @param ctx the parse tree
	 */
	void exitLogical_operator(GlobalSQLParser.Logical_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#select_clause}.
	 * @param ctx the parse tree
	 */
	void enterSelect_clause(GlobalSQLParser.Select_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#select_clause}.
	 * @param ctx the parse tree
	 */
	void exitSelect_clause(GlobalSQLParser.Select_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#delete_clause}.
	 * @param ctx the parse tree
	 */
	void enterDelete_clause(GlobalSQLParser.Delete_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#delete_clause}.
	 * @param ctx the parse tree
	 */
	void exitDelete_clause(GlobalSQLParser.Delete_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#update_clause}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_clause(GlobalSQLParser.Update_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#update_clause}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_clause(GlobalSQLParser.Update_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#aggregate_clause}.
	 * @param ctx the parse tree
	 */
	void enterAggregate_clause(GlobalSQLParser.Aggregate_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#aggregate_clause}.
	 * @param ctx the parse tree
	 */
	void exitAggregate_clause(GlobalSQLParser.Aggregate_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#from_clause}.
	 * @param ctx the parse tree
	 */
	void enterFrom_clause(GlobalSQLParser.From_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#from_clause}.
	 * @param ctx the parse tree
	 */
	void exitFrom_clause(GlobalSQLParser.From_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#join_clause}.
	 * @param ctx the parse tree
	 */
	void enterJoin_clause(GlobalSQLParser.Join_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#join_clause}.
	 * @param ctx the parse tree
	 */
	void exitJoin_clause(GlobalSQLParser.Join_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#where_clause}.
	 * @param ctx the parse tree
	 */
	void enterWhere_clause(GlobalSQLParser.Where_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#where_clause}.
	 * @param ctx the parse tree
	 */
	void exitWhere_clause(GlobalSQLParser.Where_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#select_query}.
	 * @param ctx the parse tree
	 */
	void enterSelect_query(GlobalSQLParser.Select_queryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#select_query}.
	 * @param ctx the parse tree
	 */
	void exitSelect_query(GlobalSQLParser.Select_queryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#delete_query}.
	 * @param ctx the parse tree
	 */
	void enterDelete_query(GlobalSQLParser.Delete_queryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#delete_query}.
	 * @param ctx the parse tree
	 */
	void exitDelete_query(GlobalSQLParser.Delete_queryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#update_query}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_query(GlobalSQLParser.Update_queryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#update_query}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_query(GlobalSQLParser.Update_queryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlobalSQLParser#aggregate_query}.
	 * @param ctx the parse tree
	 */
	void enterAggregate_query(GlobalSQLParser.Aggregate_queryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlobalSQLParser#aggregate_query}.
	 * @param ctx the parse tree
	 */
	void exitAggregate_query(GlobalSQLParser.Aggregate_queryContext ctx);
}