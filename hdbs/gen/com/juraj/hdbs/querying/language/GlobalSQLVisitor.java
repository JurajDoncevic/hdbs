// Generated from D:/Diplomski/hdbs/src/main/java/com/juraj/hdbs/querying/language\GlobalSQL.g4 by ANTLR 4.7
package com.juraj.hdbs.querying.language;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GlobalSQLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GlobalSQLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#number_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber_value(GlobalSQLParser.Number_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#string_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_value(GlobalSQLParser.String_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(GlobalSQLParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#number_comparison_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber_comparison_operator(GlobalSQLParser.Number_comparison_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#string_comparison_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_comparison_operator(GlobalSQLParser.String_comparison_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(GlobalSQLParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#select_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect_clause(GlobalSQLParser.Select_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#from_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrom_clause(GlobalSQLParser.From_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#join_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoin_clause(GlobalSQLParser.Join_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#where_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhere_clause(GlobalSQLParser.Where_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlobalSQLParser#select_query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelect_query(GlobalSQLParser.Select_queryContext ctx);
}