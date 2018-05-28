// Generated from D:/Diplomski/hdbs/src/main/java/com/juraj/hdbs/querying/language\GlobalSQL.g4 by ANTLR 4.7
package com.juraj.hdbs.querying.language;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GlobalSQLLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"SELECT", "FROM", "JOIN", "WHERE", "DELETE", "UPDATE", "VALUES", "SUM", 
		"COUNT", "AVG", "MIN", "MAX", "SEMI", "COLO", "WS", "GT", "GE", "LT", 
		"LE", "EQ", "NE", "AND", "OR", "LPAREN", "RPAREN", "COLID", "TBLID", "DBID", 
		"ALLCOLS", "INTEGER", "DECIMAL", "STRING", "DBL_SLASH", "BLOCK_COMMENT"
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


	public GlobalSQLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GlobalSQL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2$\u0134\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4"+
		"\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\16\3\16\3\17\3\17\3\20\6\20\u008f\n\20\r\20\16\20\u0090\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33"+
		"\3\33\7\33\u00b1\n\33\f\33\16\33\u00b4\13\33\3\33\3\33\3\33\7\33\u00b9"+
		"\n\33\f\33\16\33\u00bc\13\33\3\33\3\33\3\33\7\33\u00c1\n\33\f\33\16\33"+
		"\u00c4\13\33\3\34\3\34\7\34\u00c8\n\34\f\34\16\34\u00cb\13\34\3\34\3\34"+
		"\3\34\7\34\u00d0\n\34\f\34\16\34\u00d3\13\34\3\35\3\35\7\35\u00d7\n\35"+
		"\f\35\16\35\u00da\13\35\3\36\3\36\3\37\7\37\u00df\n\37\f\37\16\37\u00e2"+
		"\13\37\3\37\6\37\u00e5\n\37\r\37\16\37\u00e6\3\37\7\37\u00ea\n\37\f\37"+
		"\16\37\u00ed\13\37\3\37\5\37\u00f0\n\37\3 \7 \u00f3\n \f \16 \u00f6\13"+
		" \3 \6 \u00f9\n \r \16 \u00fa\3 \7 \u00fe\n \f \16 \u0101\13 \3 \3 \6"+
		" \u0105\n \r \16 \u0106\3 \3 \3 \6 \u010c\n \r \16 \u010d\5 \u0110\n "+
		"\3!\3!\6!\u0114\n!\r!\16!\u0115\3!\3!\3\"\3\"\3\"\3\"\7\"\u011e\n\"\f"+
		"\"\16\"\u0121\13\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\7#\u012b\n#\f#\16#\u012e"+
		"\13#\3#\3#\3#\3#\3#\5\u0115\u011f\u012c\2$\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$\3\2\b\5\2\13\f"+
		"\16\17\"\"\5\2C\\aac|\6\2\62;C\\aac|\3\2\63;\3\2\62;\3\2\62\62\2\u0147"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\3G\3\2\2"+
		"\2\5N\3\2\2\2\7S\3\2\2\2\tX\3\2\2\2\13^\3\2\2\2\re\3\2\2\2\17l\3\2\2\2"+
		"\21s\3\2\2\2\23w\3\2\2\2\25}\3\2\2\2\27\u0081\3\2\2\2\31\u0085\3\2\2\2"+
		"\33\u0089\3\2\2\2\35\u008b\3\2\2\2\37\u008e\3\2\2\2!\u0094\3\2\2\2#\u0096"+
		"\3\2\2\2%\u0099\3\2\2\2\'\u009b\3\2\2\2)\u009e\3\2\2\2+\u00a0\3\2\2\2"+
		"-\u00a3\3\2\2\2/\u00a7\3\2\2\2\61\u00aa\3\2\2\2\63\u00ac\3\2\2\2\65\u00ae"+
		"\3\2\2\2\67\u00c5\3\2\2\29\u00d4\3\2\2\2;\u00db\3\2\2\2=\u00ef\3\2\2\2"+
		"?\u010f\3\2\2\2A\u0111\3\2\2\2C\u0119\3\2\2\2E\u0126\3\2\2\2GH\7U\2\2"+
		"HI\7G\2\2IJ\7N\2\2JK\7G\2\2KL\7E\2\2LM\7V\2\2M\4\3\2\2\2NO\7H\2\2OP\7"+
		"T\2\2PQ\7Q\2\2QR\7O\2\2R\6\3\2\2\2ST\7L\2\2TU\7Q\2\2UV\7K\2\2VW\7P\2\2"+
		"W\b\3\2\2\2XY\7Y\2\2YZ\7J\2\2Z[\7G\2\2[\\\7T\2\2\\]\7G\2\2]\n\3\2\2\2"+
		"^_\7F\2\2_`\7G\2\2`a\7N\2\2ab\7G\2\2bc\7V\2\2cd\7G\2\2d\f\3\2\2\2ef\7"+
		"W\2\2fg\7R\2\2gh\7F\2\2hi\7C\2\2ij\7V\2\2jk\7G\2\2k\16\3\2\2\2lm\7X\2"+
		"\2mn\7C\2\2no\7N\2\2op\7W\2\2pq\7G\2\2qr\7U\2\2r\20\3\2\2\2st\7U\2\2t"+
		"u\7W\2\2uv\7O\2\2v\22\3\2\2\2wx\7E\2\2xy\7Q\2\2yz\7W\2\2z{\7P\2\2{|\7"+
		"V\2\2|\24\3\2\2\2}~\7C\2\2~\177\7X\2\2\177\u0080\7I\2\2\u0080\26\3\2\2"+
		"\2\u0081\u0082\7O\2\2\u0082\u0083\7K\2\2\u0083\u0084\7P\2\2\u0084\30\3"+
		"\2\2\2\u0085\u0086\7O\2\2\u0086\u0087\7C\2\2\u0087\u0088\7Z\2\2\u0088"+
		"\32\3\2\2\2\u0089\u008a\7=\2\2\u008a\34\3\2\2\2\u008b\u008c\7.\2\2\u008c"+
		"\36\3\2\2\2\u008d\u008f\t\2\2\2\u008e\u008d\3\2\2\2\u008f\u0090\3\2\2"+
		"\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0093"+
		"\b\20\2\2\u0093 \3\2\2\2\u0094\u0095\7@\2\2\u0095\"\3\2\2\2\u0096\u0097"+
		"\7@\2\2\u0097\u0098\7?\2\2\u0098$\3\2\2\2\u0099\u009a\7>\2\2\u009a&\3"+
		"\2\2\2\u009b\u009c\7>\2\2\u009c\u009d\7?\2\2\u009d(\3\2\2\2\u009e\u009f"+
		"\7?\2\2\u009f*\3\2\2\2\u00a0\u00a1\7#\2\2\u00a1\u00a2\7?\2\2\u00a2,\3"+
		"\2\2\2\u00a3\u00a4\7C\2\2\u00a4\u00a5\7P\2\2\u00a5\u00a6\7F\2\2\u00a6"+
		".\3\2\2\2\u00a7\u00a8\7Q\2\2\u00a8\u00a9\7T\2\2\u00a9\60\3\2\2\2\u00aa"+
		"\u00ab\7*\2\2\u00ab\62\3\2\2\2\u00ac\u00ad\7+\2\2\u00ad\64\3\2\2\2\u00ae"+
		"\u00b2\t\3\2\2\u00af\u00b1\t\4\2\2\u00b0\u00af\3\2\2\2\u00b1\u00b4\3\2"+
		"\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4"+
		"\u00b2\3\2\2\2\u00b5\u00b6\7\60\2\2\u00b6\u00ba\t\3\2\2\u00b7\u00b9\t"+
		"\4\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba"+
		"\u00bb\3\2\2\2\u00bb\u00bd\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\u00be\7\60"+
		"\2\2\u00be\u00c2\t\3\2\2\u00bf\u00c1\t\4\2\2\u00c0\u00bf\3\2\2\2\u00c1"+
		"\u00c4\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\66\3\2\2"+
		"\2\u00c4\u00c2\3\2\2\2\u00c5\u00c9\t\3\2\2\u00c6\u00c8\t\4\2\2\u00c7\u00c6"+
		"\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca"+
		"\u00cc\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cc\u00cd\7\60\2\2\u00cd\u00d1\t"+
		"\3\2\2\u00ce\u00d0\t\4\2\2\u00cf\u00ce\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1"+
		"\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d28\3\2\2\2\u00d3\u00d1\3\2\2\2"+
		"\u00d4\u00d8\t\3\2\2\u00d5\u00d7\t\4\2\2\u00d6\u00d5\3\2\2\2\u00d7\u00da"+
		"\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9:\3\2\2\2\u00da"+
		"\u00d8\3\2\2\2\u00db\u00dc\7,\2\2\u00dc<\3\2\2\2\u00dd\u00df\7/\2\2\u00de"+
		"\u00dd\3\2\2\2\u00df\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2"+
		"\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e3\u00e5\t\5\2\2\u00e4"+
		"\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2"+
		"\2\2\u00e7\u00eb\3\2\2\2\u00e8\u00ea\t\6\2\2\u00e9\u00e8\3\2\2\2\u00ea"+
		"\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00f0\3\2"+
		"\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00f0\t\7\2\2\u00ef\u00e0\3\2\2\2\u00ef"+
		"\u00ee\3\2\2\2\u00f0>\3\2\2\2\u00f1\u00f3\7/\2\2\u00f2\u00f1\3\2\2\2\u00f3"+
		"\u00f6\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f8\3\2"+
		"\2\2\u00f6\u00f4\3\2\2\2\u00f7\u00f9\t\5\2\2\u00f8\u00f7\3\2\2\2\u00f9"+
		"\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00ff\3\2"+
		"\2\2\u00fc\u00fe\t\6\2\2\u00fd\u00fc\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff"+
		"\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0102\3\2\2\2\u0101\u00ff\3\2"+
		"\2\2\u0102\u0104\7\60\2\2\u0103\u0105\t\6\2\2\u0104\u0103\3\2\2\2\u0105"+
		"\u0106\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0110\3\2"+
		"\2\2\u0108\u0109\t\7\2\2\u0109\u010b\7\60\2\2\u010a\u010c\t\6\2\2\u010b"+
		"\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010b\3\2\2\2\u010d\u010e\3\2"+
		"\2\2\u010e\u0110\3\2\2\2\u010f\u00f4\3\2\2\2\u010f\u0108\3\2\2\2\u0110"+
		"@\3\2\2\2\u0111\u0113\7)\2\2\u0112\u0114\13\2\2\2\u0113\u0112\3\2\2\2"+
		"\u0114\u0115\3\2\2\2\u0115\u0116\3\2\2\2\u0115\u0113\3\2\2\2\u0116\u0117"+
		"\3\2\2\2\u0117\u0118\7)\2\2\u0118B\3\2\2\2\u0119\u011a\7\61\2\2\u011a"+
		"\u011b\7\61\2\2\u011b\u011f\3\2\2\2\u011c\u011e\13\2\2\2\u011d\u011c\3"+
		"\2\2\2\u011e\u0121\3\2\2\2\u011f\u0120\3\2\2\2\u011f\u011d\3\2\2\2\u0120"+
		"\u0122\3\2\2\2\u0121\u011f\3\2\2\2\u0122\u0123\7\f\2\2\u0123\u0124\3\2"+
		"\2\2\u0124\u0125\b\"\2\2\u0125D\3\2\2\2\u0126\u0127\7\61\2\2\u0127\u0128"+
		"\7,\2\2\u0128\u012c\3\2\2\2\u0129\u012b\13\2\2\2\u012a\u0129\3\2\2\2\u012b"+
		"\u012e\3\2\2\2\u012c\u012d\3\2\2\2\u012c\u012a\3\2\2\2\u012d\u012f\3\2"+
		"\2\2\u012e\u012c\3\2\2\2\u012f\u0130\7,\2\2\u0130\u0131\7\61\2\2\u0131"+
		"\u0132\3\2\2\2\u0132\u0133\b#\2\2\u0133F\3\2\2\2\27\2\u0090\u00b2\u00ba"+
		"\u00c2\u00c9\u00d1\u00d8\u00e0\u00e6\u00eb\u00ef\u00f4\u00fa\u00ff\u0106"+
		"\u010d\u010f\u0115\u011f\u012c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}