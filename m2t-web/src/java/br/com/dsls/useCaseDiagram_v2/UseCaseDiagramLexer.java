// Generated from G:\_TG\_Testes\WebAccessibleUML\src\java\com\dsls\u005CuseCaseDiagram_v2\UseCaseDiagram.g4 by ANTLR 4.4
package br.com.dsls.useCaseDiagram_v2;
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
		EXTENSION=7, INCLUSION=8, COMMA=9, DOT=10, STRING=11, WS=12;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'"
	};
	public static final String[] ruleNames = {
		"DIAGRAM", "ACTORS", "USE_CASES", "RELATIONS", "INHERITANCE", "COMMUNICATION", 
		"EXTENSION", "INCLUSION", "COMMA", "DOT", "STRING", "ESC", "WS"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\16~\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3"+
		"\f\7\fn\n\f\f\f\16\fq\13\f\3\f\3\f\3\r\3\r\3\r\3\16\6\16y\n\16\r\16\16"+
		"\16z\3\16\3\16\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\2\33\16\3\2\5\5\2\f\f\17\17$$\n\2$$))^^ddhhppttvv\5\2\13\f\17\17"+
		"\"\"~\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r"+
		"\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2"+
		"\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5&\3\2\2\2\7-\3\2\2\2\t:\3\2\2\2\13C\3"+
		"\2\2\2\rL\3\2\2\2\17X\3\2\2\2\21_\3\2\2\2\23g\3\2\2\2\25i\3\2\2\2\27k"+
		"\3\2\2\2\31t\3\2\2\2\33x\3\2\2\2\35\36\7F\2\2\36\37\7k\2\2\37 \7c\2\2"+
		" !\7i\2\2!\"\7t\2\2\"#\7c\2\2#$\7o\2\2$%\7c\2\2%\4\3\2\2\2&\'\7C\2\2\'"+
		"(\7v\2\2()\7q\2\2)*\7t\2\2*+\7g\2\2+,\7u\2\2,\6\3\2\2\2-.\7E\2\2./\7c"+
		"\2\2/\60\7u\2\2\60\61\7q\2\2\61\62\7u\2\2\62\63\7\"\2\2\63\64\7f\2\2\64"+
		"\65\7g\2\2\65\66\7\"\2\2\66\67\7W\2\2\678\7u\2\289\7q\2\29\b\3\2\2\2:"+
		";\7T\2\2;<\7g\2\2<=\7n\2\2=>\7c\2\2>?\7\u00e9\2\2?@\7\u00f7\2\2@A\7g\2"+
		"\2AB\7u\2\2B\n\3\2\2\2CD\7j\2\2DE\7g\2\2EF\7t\2\2FG\7f\2\2GH\7c\2\2HI"+
		"\7\"\2\2IJ\7f\2\2JK\7g\2\2K\f\3\2\2\2LM\7e\2\2MN\7q\2\2NO\7p\2\2OP\7g"+
		"\2\2PQ\7e\2\2QR\7v\2\2RS\7c\2\2ST\7\"\2\2TU\7e\2\2UV\7q\2\2VW\7o\2\2W"+
		"\16\3\2\2\2XY\7g\2\2YZ\7z\2\2Z[\7v\2\2[\\\7g\2\2\\]\7p\2\2]^\7f\2\2^\20"+
		"\3\2\2\2_`\7k\2\2`a\7p\2\2ab\7e\2\2bc\7n\2\2cd\7w\2\2de\7f\2\2ef\7g\2"+
		"\2f\22\3\2\2\2gh\7.\2\2h\24\3\2\2\2ij\7\60\2\2j\26\3\2\2\2ko\7$\2\2ln"+
		"\n\2\2\2ml\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2pr\3\2\2\2qo\3\2\2\2r"+
		"s\7$\2\2s\30\3\2\2\2tu\7^\2\2uv\t\3\2\2v\32\3\2\2\2wy\t\4\2\2xw\3\2\2"+
		"\2yz\3\2\2\2zx\3\2\2\2z{\3\2\2\2{|\3\2\2\2|}\b\16\2\2}\34\3\2\2\2\5\2"+
		"oz\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}