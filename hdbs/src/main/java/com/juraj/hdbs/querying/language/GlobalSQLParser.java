// Generated from D:/Diplomski/hdbs/src/main/java/com/juraj/hdbs/querying/language\GlobalSQL.g4 by ANTLR 4.7
package com.juraj.hdbs.querying.language;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GlobalSQLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SELECT=1, FROM=2, JOIN=3, WHERE=4, DELETE=5, UPDATE=6, VALUES=7, SUM=8, 
		COUNT=9, AVG=10, MIN=11, MAX=12, SEMI=13, COLO=14, WS=15, GT=16, GE=17, 
		LT=18, LE=19, EQ=20, NE=21, AND=22, OR=23, LPAREN=24, RPAREN=25, COLID=26, 
		TBLID=27, DBID=28, ALLCOLS=29, INTEGER=30, DECIMAL=31, STRING=32, DBL_SLASH=33, 
		BLOCK_COMMENT=34;
	public static final int
		RULE_number_value = 0, RULE_string_value = 1, RULE_value = 2, RULE_number_comparison_operator = 3, 
		RULE_string_comparison_operator = 4, RULE_comparison = 5, RULE_logical_operator = 6, 
		RULE_select_clause = 7, RULE_delete_clause = 8, RULE_update_clause = 9, 
		RULE_aggregate_clause = 10, RULE_from_clause = 11, RULE_join_clause = 12, 
		RULE_where_clause = 13, RULE_select_query = 14, RULE_delete_query = 15, 
		RULE_update_query = 16, RULE_aggregate_query = 17;
	public static final String[] ruleNames = {
		"number_value", "string_value", "value", "number_comparison_operator", 
		"string_comparison_operator", "comparison", "logical_operator", "select_clause", 
		"delete_clause", "update_clause", "aggregate_clause", "from_clause", "join_clause", 
		"where_clause", "select_query", "delete_query", "update_query", "aggregate_query"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'SELECT'", "'FROM'", "'JOIN'", "'WHERE'", "'DELETE'", "'UPDATE'", 
		"'VALUES'", "'SUM'", "'COUNT'", "'AVG'", "'MIN'", "'MAX'", "';'", "','", 
		null, "'>'", "'>='", "'<'", "'<='", "'='", "'!='", "'AND'", "'OR'", "'('", 
		"')'", null, null, null, "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "SELECT", "FROM", "JOIN", "WHERE", "DELETE", "UPDATE", "VALUES", 
		"SUM", "COUNT", "AVG", "MIN", "MAX", "SEMI", "COLO", "WS", "GT", "GE", 
		"LT", "LE", "EQ", "NE", "AND", "OR", "LPAREN", "RPAREN", "COLID", "TBLID", 
		"DBID", "ALLCOLS", "INTEGER", "DECIMAL", "STRING", "DBL_SLASH", "BLOCK_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GlobalSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GlobalSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Number_valueContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(GlobalSQLParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(GlobalSQLParser.DECIMAL, 0); }
		public Number_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterNumber_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitNumber_value(this);
		}
	}

	public final Number_valueContext number_value() throws RecognitionException {
		Number_valueContext _localctx = new Number_valueContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_number_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			_la = _input.LA(1);
			if ( !(_la==INTEGER || _la==DECIMAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_valueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(GlobalSQLParser.STRING, 0); }
		public String_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterString_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitString_value(this);
		}
	}

	public final String_valueContext string_value() throws RecognitionException {
		String_valueContext _localctx = new String_valueContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_string_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public Number_valueContext number_value() {
			return getRuleContext(Number_valueContext.class,0);
		}
		public String_valueContext string_value() {
			return getRuleContext(String_valueContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_value);
		try {
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case DECIMAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				number_value();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				string_value();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Number_comparison_operatorContext extends ParserRuleContext {
		public TerminalNode GT() { return getToken(GlobalSQLParser.GT, 0); }
		public TerminalNode GE() { return getToken(GlobalSQLParser.GE, 0); }
		public TerminalNode LT() { return getToken(GlobalSQLParser.LT, 0); }
		public TerminalNode LE() { return getToken(GlobalSQLParser.LE, 0); }
		public TerminalNode EQ() { return getToken(GlobalSQLParser.EQ, 0); }
		public TerminalNode NE() { return getToken(GlobalSQLParser.NE, 0); }
		public Number_comparison_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number_comparison_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterNumber_comparison_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitNumber_comparison_operator(this);
		}
	}

	public final Number_comparison_operatorContext number_comparison_operator() throws RecognitionException {
		Number_comparison_operatorContext _localctx = new Number_comparison_operatorContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_number_comparison_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << GE) | (1L << LT) | (1L << LE) | (1L << EQ) | (1L << NE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class String_comparison_operatorContext extends ParserRuleContext {
		public TerminalNode NE() { return getToken(GlobalSQLParser.NE, 0); }
		public TerminalNode EQ() { return getToken(GlobalSQLParser.EQ, 0); }
		public String_comparison_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_comparison_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterString_comparison_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitString_comparison_operator(this);
		}
	}

	public final String_comparison_operatorContext string_comparison_operator() throws RecognitionException {
		String_comparison_operatorContext _localctx = new String_comparison_operatorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_string_comparison_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_la = _input.LA(1);
			if ( !(_la==EQ || _la==NE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonContext extends ParserRuleContext {
		public TerminalNode COLID() { return getToken(GlobalSQLParser.COLID, 0); }
		public Number_comparison_operatorContext number_comparison_operator() {
			return getRuleContext(Number_comparison_operatorContext.class,0);
		}
		public Number_valueContext number_value() {
			return getRuleContext(Number_valueContext.class,0);
		}
		public String_comparison_operatorContext string_comparison_operator() {
			return getRuleContext(String_comparison_operatorContext.class,0);
		}
		public String_valueContext string_value() {
			return getRuleContext(String_valueContext.class,0);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitComparison(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_comparison);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(COLID);
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(49);
				number_comparison_operator();
				setState(50);
				number_value();
				}
				break;
			case 2:
				{
				setState(52);
				string_comparison_operator();
				setState(53);
				string_value();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logical_operatorContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(GlobalSQLParser.AND, 0); }
		public TerminalNode OR() { return getToken(GlobalSQLParser.OR, 0); }
		public Logical_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logical_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterLogical_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitLogical_operator(this);
		}
	}

	public final Logical_operatorContext logical_operator() throws RecognitionException {
		Logical_operatorContext _localctx = new Logical_operatorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_logical_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_clauseContext extends ParserRuleContext {
		public TerminalNode SELECT() { return getToken(GlobalSQLParser.SELECT, 0); }
		public List<TerminalNode> COLID() { return getTokens(GlobalSQLParser.COLID); }
		public TerminalNode COLID(int i) {
			return getToken(GlobalSQLParser.COLID, i);
		}
		public List<TerminalNode> COLO() { return getTokens(GlobalSQLParser.COLO); }
		public TerminalNode COLO(int i) {
			return getToken(GlobalSQLParser.COLO, i);
		}
		public TerminalNode ALLCOLS() { return getToken(GlobalSQLParser.ALLCOLS, 0); }
		public Select_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterSelect_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitSelect_clause(this);
		}
	}

	public final Select_clauseContext select_clause() throws RecognitionException {
		Select_clauseContext _localctx = new Select_clauseContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_select_clause);
		int _la;
		try {
			setState(70);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				match(SELECT);
				setState(60);
				match(COLID);
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COLO) {
					{
					{
					setState(61);
					match(COLO);
					setState(62);
					match(COLID);
					}
					}
					setState(67);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				match(SELECT);
				setState(69);
				match(ALLCOLS);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Delete_clauseContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(GlobalSQLParser.DELETE, 0); }
		public TerminalNode TBLID() { return getToken(GlobalSQLParser.TBLID, 0); }
		public Delete_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delete_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterDelete_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitDelete_clause(this);
		}
	}

	public final Delete_clauseContext delete_clause() throws RecognitionException {
		Delete_clauseContext _localctx = new Delete_clauseContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_delete_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(DELETE);
			setState(73);
			match(TBLID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Update_clauseContext extends ParserRuleContext {
		public TerminalNode UPDATE() { return getToken(GlobalSQLParser.UPDATE, 0); }
		public TerminalNode TBLID() { return getToken(GlobalSQLParser.TBLID, 0); }
		public List<TerminalNode> LPAREN() { return getTokens(GlobalSQLParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(GlobalSQLParser.LPAREN, i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(GlobalSQLParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(GlobalSQLParser.RPAREN, i);
		}
		public TerminalNode VALUES() { return getToken(GlobalSQLParser.VALUES, 0); }
		public List<TerminalNode> COLID() { return getTokens(GlobalSQLParser.COLID); }
		public TerminalNode COLID(int i) {
			return getToken(GlobalSQLParser.COLID, i);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COLO() { return getTokens(GlobalSQLParser.COLO); }
		public TerminalNode COLO(int i) {
			return getToken(GlobalSQLParser.COLO, i);
		}
		public Update_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_update_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterUpdate_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitUpdate_clause(this);
		}
	}

	public final Update_clauseContext update_clause() throws RecognitionException {
		Update_clauseContext _localctx = new Update_clauseContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_update_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(UPDATE);
			setState(76);
			match(TBLID);
			setState(77);
			match(LPAREN);
			{
			setState(78);
			match(COLID);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLO) {
				{
				{
				setState(79);
				match(COLO);
				setState(80);
				match(COLID);
				}
				}
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(86);
			match(RPAREN);
			setState(87);
			match(VALUES);
			setState(88);
			match(LPAREN);
			{
			setState(89);
			value();
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLO) {
				{
				{
				setState(90);
				match(COLO);
				setState(91);
				value();
				}
				}
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			setState(97);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Aggregate_clauseContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GlobalSQLParser.LPAREN, 0); }
		public TerminalNode COLID() { return getToken(GlobalSQLParser.COLID, 0); }
		public TerminalNode RPAREN() { return getToken(GlobalSQLParser.RPAREN, 0); }
		public TerminalNode SUM() { return getToken(GlobalSQLParser.SUM, 0); }
		public TerminalNode AVG() { return getToken(GlobalSQLParser.AVG, 0); }
		public TerminalNode MIN() { return getToken(GlobalSQLParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(GlobalSQLParser.MAX, 0); }
		public TerminalNode COUNT() { return getToken(GlobalSQLParser.COUNT, 0); }
		public Aggregate_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregate_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterAggregate_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitAggregate_clause(this);
		}
	}

	public final Aggregate_clauseContext aggregate_clause() throws RecognitionException {
		Aggregate_clauseContext _localctx = new Aggregate_clauseContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_aggregate_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SUM) | (1L << COUNT) | (1L << AVG) | (1L << MIN) | (1L << MAX))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(100);
			match(LPAREN);
			setState(101);
			match(COLID);
			setState(102);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class From_clauseContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(GlobalSQLParser.FROM, 0); }
		public List<TerminalNode> TBLID() { return getTokens(GlobalSQLParser.TBLID); }
		public TerminalNode TBLID(int i) {
			return getToken(GlobalSQLParser.TBLID, i);
		}
		public List<TerminalNode> COLO() { return getTokens(GlobalSQLParser.COLO); }
		public TerminalNode COLO(int i) {
			return getToken(GlobalSQLParser.COLO, i);
		}
		public From_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_from_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterFrom_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitFrom_clause(this);
		}
	}

	public final From_clauseContext from_clause() throws RecognitionException {
		From_clauseContext _localctx = new From_clauseContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_from_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(FROM);
			setState(105);
			match(TBLID);
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COLO) {
				{
				{
				setState(106);
				match(COLO);
				setState(107);
				match(TBLID);
				}
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Join_clauseContext extends ParserRuleContext {
		public TerminalNode JOIN() { return getToken(GlobalSQLParser.JOIN, 0); }
		public TerminalNode LPAREN() { return getToken(GlobalSQLParser.LPAREN, 0); }
		public List<TerminalNode> COLID() { return getTokens(GlobalSQLParser.COLID); }
		public TerminalNode COLID(int i) {
			return getToken(GlobalSQLParser.COLID, i);
		}
		public TerminalNode COLO() { return getToken(GlobalSQLParser.COLO, 0); }
		public TerminalNode RPAREN() { return getToken(GlobalSQLParser.RPAREN, 0); }
		public Join_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_join_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterJoin_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitJoin_clause(this);
		}
	}

	public final Join_clauseContext join_clause() throws RecognitionException {
		Join_clauseContext _localctx = new Join_clauseContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_join_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(JOIN);
			setState(114);
			match(LPAREN);
			setState(115);
			match(COLID);
			setState(116);
			match(COLO);
			setState(117);
			match(COLID);
			setState(118);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Where_clauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(GlobalSQLParser.WHERE, 0); }
		public List<ComparisonContext> comparison() {
			return getRuleContexts(ComparisonContext.class);
		}
		public ComparisonContext comparison(int i) {
			return getRuleContext(ComparisonContext.class,i);
		}
		public List<Logical_operatorContext> logical_operator() {
			return getRuleContexts(Logical_operatorContext.class);
		}
		public Logical_operatorContext logical_operator(int i) {
			return getRuleContext(Logical_operatorContext.class,i);
		}
		public Where_clauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_where_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterWhere_clause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitWhere_clause(this);
		}
	}

	public final Where_clauseContext where_clause() throws RecognitionException {
		Where_clauseContext _localctx = new Where_clauseContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_where_clause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			match(WHERE);
			setState(121);
			comparison();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND || _la==OR) {
				{
				{
				setState(122);
				logical_operator();
				setState(123);
				comparison();
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Select_queryContext extends ParserRuleContext {
		public Select_clauseContext select_clause() {
			return getRuleContext(Select_clauseContext.class,0);
		}
		public From_clauseContext from_clause() {
			return getRuleContext(From_clauseContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GlobalSQLParser.SEMI, 0); }
		public List<Join_clauseContext> join_clause() {
			return getRuleContexts(Join_clauseContext.class);
		}
		public Join_clauseContext join_clause(int i) {
			return getRuleContext(Join_clauseContext.class,i);
		}
		public Where_clauseContext where_clause() {
			return getRuleContext(Where_clauseContext.class,0);
		}
		public Select_queryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_select_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterSelect_query(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitSelect_query(this);
		}
	}

	public final Select_queryContext select_query() throws RecognitionException {
		Select_queryContext _localctx = new Select_queryContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_select_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			select_clause();
			setState(131);
			from_clause();
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JOIN) {
				{
				{
				setState(132);
				join_clause();
				}
				}
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(142);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(138);
				match(SEMI);
				}
				break;
			case WHERE:
				{
				{
				setState(139);
				where_clause();
				setState(140);
				match(SEMI);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Delete_queryContext extends ParserRuleContext {
		public Delete_clauseContext delete_clause() {
			return getRuleContext(Delete_clauseContext.class,0);
		}
		public From_clauseContext from_clause() {
			return getRuleContext(From_clauseContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GlobalSQLParser.SEMI, 0); }
		public List<Join_clauseContext> join_clause() {
			return getRuleContexts(Join_clauseContext.class);
		}
		public Join_clauseContext join_clause(int i) {
			return getRuleContext(Join_clauseContext.class,i);
		}
		public Where_clauseContext where_clause() {
			return getRuleContext(Where_clauseContext.class,0);
		}
		public Delete_queryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delete_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterDelete_query(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitDelete_query(this);
		}
	}

	public final Delete_queryContext delete_query() throws RecognitionException {
		Delete_queryContext _localctx = new Delete_queryContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_delete_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			delete_clause();
			setState(145);
			from_clause();
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JOIN) {
				{
				{
				setState(146);
				join_clause();
				}
				}
				setState(151);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(152);
				match(SEMI);
				}
				break;
			case WHERE:
				{
				{
				setState(153);
				where_clause();
				setState(154);
				match(SEMI);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Update_queryContext extends ParserRuleContext {
		public Update_clauseContext update_clause() {
			return getRuleContext(Update_clauseContext.class,0);
		}
		public From_clauseContext from_clause() {
			return getRuleContext(From_clauseContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GlobalSQLParser.SEMI, 0); }
		public List<Join_clauseContext> join_clause() {
			return getRuleContexts(Join_clauseContext.class);
		}
		public Join_clauseContext join_clause(int i) {
			return getRuleContext(Join_clauseContext.class,i);
		}
		public Where_clauseContext where_clause() {
			return getRuleContext(Where_clauseContext.class,0);
		}
		public Update_queryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_update_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterUpdate_query(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitUpdate_query(this);
		}
	}

	public final Update_queryContext update_query() throws RecognitionException {
		Update_queryContext _localctx = new Update_queryContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_update_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			update_clause();
			setState(159);
			from_clause();
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JOIN) {
				{
				{
				setState(160);
				join_clause();
				}
				}
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(170);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(166);
				match(SEMI);
				}
				break;
			case WHERE:
				{
				{
				setState(167);
				where_clause();
				setState(168);
				match(SEMI);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Aggregate_queryContext extends ParserRuleContext {
		public Aggregate_clauseContext aggregate_clause() {
			return getRuleContext(Aggregate_clauseContext.class,0);
		}
		public From_clauseContext from_clause() {
			return getRuleContext(From_clauseContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(GlobalSQLParser.SEMI, 0); }
		public List<Join_clauseContext> join_clause() {
			return getRuleContexts(Join_clauseContext.class);
		}
		public Join_clauseContext join_clause(int i) {
			return getRuleContext(Join_clauseContext.class,i);
		}
		public Where_clauseContext where_clause() {
			return getRuleContext(Where_clauseContext.class,0);
		}
		public Aggregate_queryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregate_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).enterAggregate_query(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlobalSQLListener ) ((GlobalSQLListener)listener).exitAggregate_query(this);
		}
	}

	public final Aggregate_queryContext aggregate_query() throws RecognitionException {
		Aggregate_queryContext _localctx = new Aggregate_queryContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_aggregate_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			aggregate_clause();
			setState(173);
			from_clause();
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JOIN) {
				{
				{
				setState(174);
				join_clause();
				}
				}
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(184);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMI:
				{
				setState(180);
				match(SEMI);
				}
				break;
			case WHERE:
				{
				{
				setState(181);
				where_clause();
				setState(182);
				match(SEMI);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3$\u00bd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\3\3\3\3\4\3\4\5\4-\n\4\3\5\3\5\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\5\7:\n\7\3\b\3\b\3\t\3\t\3\t\3\t\7\tB\n\t\f\t\16\t"+
		"E\13\t\3\t\3\t\5\tI\n\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\7\13"+
		"T\n\13\f\13\16\13W\13\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13_\n\13\f\13"+
		"\16\13b\13\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\7\ro\n\r\f"+
		"\r\16\rr\13\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3"+
		"\17\7\17\u0080\n\17\f\17\16\17\u0083\13\17\3\20\3\20\3\20\7\20\u0088\n"+
		"\20\f\20\16\20\u008b\13\20\3\20\3\20\3\20\3\20\5\20\u0091\n\20\3\21\3"+
		"\21\3\21\7\21\u0096\n\21\f\21\16\21\u0099\13\21\3\21\3\21\3\21\3\21\5"+
		"\21\u009f\n\21\3\22\3\22\3\22\7\22\u00a4\n\22\f\22\16\22\u00a7\13\22\3"+
		"\22\3\22\3\22\3\22\5\22\u00ad\n\22\3\23\3\23\3\23\7\23\u00b2\n\23\f\23"+
		"\16\23\u00b5\13\23\3\23\3\23\3\23\3\23\5\23\u00bb\n\23\3\23\2\2\24\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2\7\3\2 !\3\2\22\27\3\2\26\27"+
		"\3\2\30\31\3\2\n\16\2\u00ba\2&\3\2\2\2\4(\3\2\2\2\6,\3\2\2\2\b.\3\2\2"+
		"\2\n\60\3\2\2\2\f\62\3\2\2\2\16;\3\2\2\2\20H\3\2\2\2\22J\3\2\2\2\24M\3"+
		"\2\2\2\26e\3\2\2\2\30j\3\2\2\2\32s\3\2\2\2\34z\3\2\2\2\36\u0084\3\2\2"+
		"\2 \u0092\3\2\2\2\"\u00a0\3\2\2\2$\u00ae\3\2\2\2&\'\t\2\2\2\'\3\3\2\2"+
		"\2()\7\"\2\2)\5\3\2\2\2*-\5\2\2\2+-\5\4\3\2,*\3\2\2\2,+\3\2\2\2-\7\3\2"+
		"\2\2./\t\3\2\2/\t\3\2\2\2\60\61\t\4\2\2\61\13\3\2\2\2\629\7\34\2\2\63"+
		"\64\5\b\5\2\64\65\5\2\2\2\65:\3\2\2\2\66\67\5\n\6\2\678\5\4\3\28:\3\2"+
		"\2\29\63\3\2\2\29\66\3\2\2\2:\r\3\2\2\2;<\t\5\2\2<\17\3\2\2\2=>\7\3\2"+
		"\2>C\7\34\2\2?@\7\20\2\2@B\7\34\2\2A?\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3"+
		"\2\2\2DI\3\2\2\2EC\3\2\2\2FG\7\3\2\2GI\7\37\2\2H=\3\2\2\2HF\3\2\2\2I\21"+
		"\3\2\2\2JK\7\7\2\2KL\7\35\2\2L\23\3\2\2\2MN\7\b\2\2NO\7\35\2\2OP\7\32"+
		"\2\2PU\7\34\2\2QR\7\20\2\2RT\7\34\2\2SQ\3\2\2\2TW\3\2\2\2US\3\2\2\2UV"+
		"\3\2\2\2VX\3\2\2\2WU\3\2\2\2XY\7\33\2\2YZ\7\t\2\2Z[\7\32\2\2[`\5\6\4\2"+
		"\\]\7\20\2\2]_\5\6\4\2^\\\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2ac\3\2"+
		"\2\2b`\3\2\2\2cd\7\33\2\2d\25\3\2\2\2ef\t\6\2\2fg\7\32\2\2gh\7\34\2\2"+
		"hi\7\33\2\2i\27\3\2\2\2jk\7\4\2\2kp\7\35\2\2lm\7\20\2\2mo\7\35\2\2nl\3"+
		"\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2q\31\3\2\2\2rp\3\2\2\2st\7\5\2\2t"+
		"u\7\32\2\2uv\7\34\2\2vw\7\20\2\2wx\7\34\2\2xy\7\33\2\2y\33\3\2\2\2z{\7"+
		"\6\2\2{\u0081\5\f\7\2|}\5\16\b\2}~\5\f\7\2~\u0080\3\2\2\2\177|\3\2\2\2"+
		"\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\35\3"+
		"\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085\5\20\t\2\u0085\u0089\5\30\r\2\u0086"+
		"\u0088\5\32\16\2\u0087\u0086\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3"+
		"\2\2\2\u0089\u008a\3\2\2\2\u008a\u0090\3\2\2\2\u008b\u0089\3\2\2\2\u008c"+
		"\u0091\7\17\2\2\u008d\u008e\5\34\17\2\u008e\u008f\7\17\2\2\u008f\u0091"+
		"\3\2\2\2\u0090\u008c\3\2\2\2\u0090\u008d\3\2\2\2\u0091\37\3\2\2\2\u0092"+
		"\u0093\5\22\n\2\u0093\u0097\5\30\r\2\u0094\u0096\5\32\16\2\u0095\u0094"+
		"\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098"+
		"\u009e\3\2\2\2\u0099\u0097\3\2\2\2\u009a\u009f\7\17\2\2\u009b\u009c\5"+
		"\34\17\2\u009c\u009d\7\17\2\2\u009d\u009f\3\2\2\2\u009e\u009a\3\2\2\2"+
		"\u009e\u009b\3\2\2\2\u009f!\3\2\2\2\u00a0\u00a1\5\24\13\2\u00a1\u00a5"+
		"\5\30\r\2\u00a2\u00a4\5\32\16\2\u00a3\u00a2\3\2\2\2\u00a4\u00a7\3\2\2"+
		"\2\u00a5\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00ac\3\2\2\2\u00a7\u00a5"+
		"\3\2\2\2\u00a8\u00ad\7\17\2\2\u00a9\u00aa\5\34\17\2\u00aa\u00ab\7\17\2"+
		"\2\u00ab\u00ad\3\2\2\2\u00ac\u00a8\3\2\2\2\u00ac\u00a9\3\2\2\2\u00ad#"+
		"\3\2\2\2\u00ae\u00af\5\26\f\2\u00af\u00b3\5\30\r\2\u00b0\u00b2\5\32\16"+
		"\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4"+
		"\3\2\2\2\u00b4\u00ba\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00bb\7\17\2\2"+
		"\u00b7\u00b8\5\34\17\2\u00b8\u00b9\7\17\2\2\u00b9\u00bb\3\2\2\2\u00ba"+
		"\u00b6\3\2\2\2\u00ba\u00b7\3\2\2\2\u00bb%\3\2\2\2\22,9CHU`p\u0081\u0089"+
		"\u0090\u0097\u009e\u00a5\u00ac\u00b3\u00ba";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}