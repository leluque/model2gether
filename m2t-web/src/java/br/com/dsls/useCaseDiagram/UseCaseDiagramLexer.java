// Generated from G:\_TG\_Testes\Teste_ANTLR\Teste_3\src\u005CuseCaseLanguage\UseCaseDiagram.g4 by ANTLR 4.4
package br.com.dsls.useCaseDiagram;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UseCaseDiagramLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DIAGRAM=1, ACTORS=2, USE_CASES=3, RELATIONS=4, INHERITANCE=5, COMMUNICATION=6, 
		EXTENSION=7, INCLUSION=8, COMMA=9, QUOTE=10, DOT=11, STRING=12, WS=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'"
	};
	public static final String[] ruleNames = {
		"DIAGRAM", "ACTORS", "USE_CASES", "RELATIONS", "INHERITANCE", "COMMUNICATION", 
		"EXTENSION", "INCLUSION", "COMMA", "QUOTE", "DOT", "STRING", "ESC", "UNICODE", 
		"HEX", "WS"
	};


	public UseCaseDiagramLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "UseCaseDiagram.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17\u0091\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\7\rw\n\r\f\r\16\rz\13"+
		"\r\3\r\3\r\3\16\3\16\3\16\5\16\u0081\n\16\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\21\6\21\u008c\n\21\r\21\16\21\u008d\3\21\3\21\2\2\22\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\2\35\2\37\2!"+
		"\17\3\2\6\4\2$$^^\n\2$$\61\61^^ddhhppttvv\5\2\62;CHch\5\2\13\f\17\17\""+
		"\"\u0091\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2!\3\2\2\2\3#\3\2\2\2\5,\3\2\2\2\7\63\3\2\2\2\t"+
		"@\3\2\2\2\13I\3\2\2\2\rR\3\2\2\2\17^\3\2\2\2\21e\3\2\2\2\23m\3\2\2\2\25"+
		"o\3\2\2\2\27q\3\2\2\2\31s\3\2\2\2\33}\3\2\2\2\35\u0082\3\2\2\2\37\u0088"+
		"\3\2\2\2!\u008b\3\2\2\2#$\7F\2\2$%\7k\2\2%&\7c\2\2&\'\7i\2\2\'(\7t\2\2"+
		"()\7c\2\2)*\7o\2\2*+\7c\2\2+\4\3\2\2\2,-\7C\2\2-.\7v\2\2./\7q\2\2/\60"+
		"\7t\2\2\60\61\7g\2\2\61\62\7u\2\2\62\6\3\2\2\2\63\64\7E\2\2\64\65\7c\2"+
		"\2\65\66\7u\2\2\66\67\7q\2\2\678\7u\2\289\7\"\2\29:\7f\2\2:;\7g\2\2;<"+
		"\7\"\2\2<=\7W\2\2=>\7u\2\2>?\7q\2\2?\b\3\2\2\2@A\7T\2\2AB\7g\2\2BC\7n"+
		"\2\2CD\7c\2\2DE\7\u00e9\2\2EF\7\u00f7\2\2FG\7g\2\2GH\7u\2\2H\n\3\2\2\2"+
		"IJ\7j\2\2JK\7g\2\2KL\7t\2\2LM\7f\2\2MN\7c\2\2NO\7\"\2\2OP\7f\2\2PQ\7g"+
		"\2\2Q\f\3\2\2\2RS\7e\2\2ST\7q\2\2TU\7p\2\2UV\7g\2\2VW\7e\2\2WX\7v\2\2"+
		"XY\7c\2\2YZ\7\"\2\2Z[\7e\2\2[\\\7q\2\2\\]\7o\2\2]\16\3\2\2\2^_\7g\2\2"+
		"_`\7z\2\2`a\7v\2\2ab\7g\2\2bc\7p\2\2cd\7f\2\2d\20\3\2\2\2ef\7k\2\2fg\7"+
		"p\2\2gh\7e\2\2hi\7n\2\2ij\7w\2\2jk\7f\2\2kl\7g\2\2l\22\3\2\2\2mn\7.\2"+
		"\2n\24\3\2\2\2op\7$\2\2p\26\3\2\2\2qr\7\60\2\2r\30\3\2\2\2sx\7$\2\2tw"+
		"\5\33\16\2uw\n\2\2\2vt\3\2\2\2vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2"+
		"y{\3\2\2\2zx\3\2\2\2{|\7$\2\2|\32\3\2\2\2}\u0080\7^\2\2~\u0081\t\3\2\2"+
		"\177\u0081\5\35\17\2\u0080~\3\2\2\2\u0080\177\3\2\2\2\u0081\34\3\2\2\2"+
		"\u0082\u0083\7w\2\2\u0083\u0084\5\37\20\2\u0084\u0085\5\37\20\2\u0085"+
		"\u0086\5\37\20\2\u0086\u0087\5\37\20\2\u0087\36\3\2\2\2\u0088\u0089\t"+
		"\4\2\2\u0089 \3\2\2\2\u008a\u008c\t\5\2\2\u008b\u008a\3\2\2\2\u008c\u008d"+
		"\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f"+
		"\u0090\b\21\2\2\u0090\"\3\2\2\2\7\2vx\u0080\u008d\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}