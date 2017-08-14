// Generated from G:\_TG\_Testes\Teste_ANTLR\Teste_3\src\u005CuseCaseLanguage\UseCaseDiagram.g4 by ANTLR 4.4
package br.com.dsls.useCaseDiagram;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UseCaseDiagramParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DIAGRAM=1, ACTORS=2, USE_CASES=3, RELATIONS=4, INHERITANCE=5, COMMUNICATION=6, 
		EXTENSION=7, INCLUSION=8, COMMA=9, QUOTE=10, DOT=11, STRING=12, WS=13;
	public static final String[] tokenNames = {
		"<INVALID>", "DIAGRAM", "'Atores'", "'Casos de Uso'", "'Relacionamentos'", "'herda de'", 
		"'se associa com'", "'estende'", "'inclui'", "COMMA", "'\"'", "'.'", "STRING", 
		"WS"
	};
	public static final int
		RULE_useCaseDiagram = 0, RULE_diagramName = 1, RULE_element = 2, RULE_actor = 3, 
		RULE_actors = 4, RULE_useCase = 5, RULE_useCases = 6, RULE_relation = 7, 
		RULE_relations = 8;
	public static final String[] ruleNames = {
		"useCaseDiagram", "diagramName", "element", "actor", "actors", "useCase", 
		"useCases", "relation", "relations"
	};

	@Override
	public String getGrammarFileName() { return "UseCaseDiagram.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public UseCaseDiagramParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class UseCaseDiagramContext extends ParserRuleContext {
		public List<TerminalNode> DOT() { return getTokens(UseCaseDiagramParser.DOT); }
		public RelationsContext relations() {
			return getRuleContext(RelationsContext.class,0);
		}
		public UseCasesContext useCases() {
			return getRuleContext(UseCasesContext.class,0);
		}
		public ActorsContext actors() {
			return getRuleContext(ActorsContext.class,0);
		}
		public DiagramNameContext diagramName() {
			return getRuleContext(DiagramNameContext.class,0);
		}
		public TerminalNode DOT(int i) {
			return getToken(UseCaseDiagramParser.DOT, i);
		}
		public UseCaseDiagramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_useCaseDiagram; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterUseCaseDiagram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitUseCaseDiagram(this);
		}
	}

	public final UseCaseDiagramContext useCaseDiagram() throws RecognitionException {
		UseCaseDiagramContext _localctx = new UseCaseDiagramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_useCaseDiagram);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_la = _input.LA(1);
			if (_la==DIAGRAM) {
				{
				setState(18); diagramName();
				}
			}

			setState(21); actors();
			setState(22); match(DOT);
			setState(23); useCases();
			setState(24); match(DOT);
			setState(25); relations();
			setState(26); match(DOT);
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

	public static class DiagramNameContext extends ParserRuleContext {
		public TerminalNode DIAGRAM() { return getToken(UseCaseDiagramParser.DIAGRAM, 0); }
		public TerminalNode STRING() { return getToken(UseCaseDiagramParser.STRING, 0); }
		public DiagramNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_diagramName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterDiagramName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitDiagramName(this);
		}
	}

	public final DiagramNameContext diagramName() throws RecognitionException {
		DiagramNameContext _localctx = new DiagramNameContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_diagramName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28); match(DIAGRAM);
			setState(29); match(STRING);
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

	public static class ElementContext extends ParserRuleContext {
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
	 
		public ElementContext() { }
		public void copyFrom(ElementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ElementActorContext extends ElementContext {
		public ActorContext actor() {
			return getRuleContext(ActorContext.class,0);
		}
		public ElementActorContext(ElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterElementActor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitElementActor(this);
		}
	}
	public static class ElementUseCaseContext extends ElementContext {
		public UseCaseContext useCase() {
			return getRuleContext(UseCaseContext.class,0);
		}
		public ElementUseCaseContext(ElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterElementUseCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitElementUseCase(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_element);
		try {
			setState(33);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				_localctx = new ElementActorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(31); actor();
				}
				break;
			case 2:
				_localctx = new ElementUseCaseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(32); useCase();
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

	public static class ActorContext extends ParserRuleContext {
		public ActorContext actor() {
			return getRuleContext(ActorContext.class,0);
		}
		public TerminalNode STRING() { return getToken(UseCaseDiagramParser.STRING, 0); }
		public TerminalNode INHERITANCE() { return getToken(UseCaseDiagramParser.INHERITANCE, 0); }
		public ActorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterActor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitActor(this);
		}
	}

	public final ActorContext actor() throws RecognitionException {
		ActorContext _localctx = new ActorContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_actor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35); match(STRING);
			setState(38);
			_la = _input.LA(1);
			if (_la==INHERITANCE) {
				{
				setState(36); match(INHERITANCE);
				setState(37); actor();
				}
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

	public static class ActorsContext extends ParserRuleContext {
		public ActorContext actor(int i) {
			return getRuleContext(ActorContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(UseCaseDiagramParser.COMMA); }
		public TerminalNode ACTORS() { return getToken(UseCaseDiagramParser.ACTORS, 0); }
		public List<ActorContext> actor() {
			return getRuleContexts(ActorContext.class);
		}
		public TerminalNode COMMA(int i) {
			return getToken(UseCaseDiagramParser.COMMA, i);
		}
		public ActorsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actors; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterActors(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitActors(this);
		}
	}

	public final ActorsContext actors() throws RecognitionException {
		ActorsContext _localctx = new ActorsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_actors);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40); match(ACTORS);
			setState(41); actor();
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(42); match(COMMA);
				setState(43); actor();
				}
				}
				setState(48);
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

	public static class UseCaseContext extends ParserRuleContext {
		public UseCaseContext useCase() {
			return getRuleContext(UseCaseContext.class,0);
		}
		public TerminalNode STRING() { return getToken(UseCaseDiagramParser.STRING, 0); }
		public TerminalNode INHERITANCE() { return getToken(UseCaseDiagramParser.INHERITANCE, 0); }
		public UseCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_useCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterUseCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitUseCase(this);
		}
	}

	public final UseCaseContext useCase() throws RecognitionException {
		UseCaseContext _localctx = new UseCaseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_useCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49); match(STRING);
			setState(52);
			_la = _input.LA(1);
			if (_la==INHERITANCE) {
				{
				setState(50); match(INHERITANCE);
				setState(51); useCase();
				}
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

	public static class UseCasesContext extends ParserRuleContext {
		public List<UseCaseContext> useCase() {
			return getRuleContexts(UseCaseContext.class);
		}
		public TerminalNode USE_CASES() { return getToken(UseCaseDiagramParser.USE_CASES, 0); }
		public List<TerminalNode> COMMA() { return getTokens(UseCaseDiagramParser.COMMA); }
		public UseCaseContext useCase(int i) {
			return getRuleContext(UseCaseContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(UseCaseDiagramParser.COMMA, i);
		}
		public UseCasesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_useCases; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterUseCases(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitUseCases(this);
		}
	}

	public final UseCasesContext useCases() throws RecognitionException {
		UseCasesContext _localctx = new UseCasesContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_useCases);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54); match(USE_CASES);
			setState(55); useCase();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(56); match(COMMA);
				setState(57); useCase();
				}
				}
				setState(62);
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

	public static class RelationContext extends ParserRuleContext {
		public RelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relation; }
	 
		public RelationContext() { }
		public void copyFrom(RelationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RelationCommContext extends RelationContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public TerminalNode COMMUNICATION() { return getToken(UseCaseDiagramParser.COMMUNICATION, 0); }
		public RelationCommContext(RelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterRelationComm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitRelationComm(this);
		}
	}
	public static class RelationInclContext extends RelationContext {
		public List<UseCaseContext> useCase() {
			return getRuleContexts(UseCaseContext.class);
		}
		public UseCaseContext useCase(int i) {
			return getRuleContext(UseCaseContext.class,i);
		}
		public TerminalNode INCLUSION() { return getToken(UseCaseDiagramParser.INCLUSION, 0); }
		public RelationInclContext(RelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterRelationIncl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitRelationIncl(this);
		}
	}
	public static class RelationExteContext extends RelationContext {
		public List<UseCaseContext> useCase() {
			return getRuleContexts(UseCaseContext.class);
		}
		public UseCaseContext useCase(int i) {
			return getRuleContext(UseCaseContext.class,i);
		}
		public TerminalNode EXTENSION() { return getToken(UseCaseDiagramParser.EXTENSION, 0); }
		public RelationExteContext(RelationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterRelationExte(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitRelationExte(this);
		}
	}

	public final RelationContext relation() throws RecognitionException {
		RelationContext _localctx = new RelationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_relation);
		try {
			setState(75);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new RelationCommContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(63); element();
				setState(64); match(COMMUNICATION);
				setState(65); element();
				}
				break;
			case 2:
				_localctx = new RelationExteContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(67); useCase();
				setState(68); match(EXTENSION);
				setState(69); useCase();
				}
				break;
			case 3:
				_localctx = new RelationInclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(71); useCase();
				setState(72); match(INCLUSION);
				setState(73); useCase();
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

	public static class RelationsContext extends ParserRuleContext {
		public List<RelationContext> relation() {
			return getRuleContexts(RelationContext.class);
		}
		public List<TerminalNode> COMMA() { return getTokens(UseCaseDiagramParser.COMMA); }
		public RelationContext relation(int i) {
			return getRuleContext(RelationContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(UseCaseDiagramParser.COMMA, i);
		}
		public TerminalNode RELATIONS() { return getToken(UseCaseDiagramParser.RELATIONS, 0); }
		public RelationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).enterRelations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof UseCaseDiagramListener ) ((UseCaseDiagramListener)listener).exitRelations(this);
		}
	}

	public final RelationsContext relations() throws RecognitionException {
		RelationsContext _localctx = new RelationsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_relations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77); match(RELATIONS);
			setState(78); relation();
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(79); match(COMMA);
				setState(80); relation();
				}
				}
				setState(85);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\17Y\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\5\2\26"+
		"\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\5\4$\n\4\3\5\3\5"+
		"\3\5\5\5)\n\5\3\6\3\6\3\6\3\6\7\6/\n\6\f\6\16\6\62\13\6\3\7\3\7\3\7\5"+
		"\7\67\n\7\3\b\3\b\3\b\3\b\7\b=\n\b\f\b\16\b@\13\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tN\n\t\3\n\3\n\3\n\3\n\7\nT\n\n\f\n\16"+
		"\nW\13\n\3\n\2\2\13\2\4\6\b\n\f\16\20\22\2\2X\2\25\3\2\2\2\4\36\3\2\2"+
		"\2\6#\3\2\2\2\b%\3\2\2\2\n*\3\2\2\2\f\63\3\2\2\2\168\3\2\2\2\20M\3\2\2"+
		"\2\22O\3\2\2\2\24\26\5\4\3\2\25\24\3\2\2\2\25\26\3\2\2\2\26\27\3\2\2\2"+
		"\27\30\5\n\6\2\30\31\7\r\2\2\31\32\5\16\b\2\32\33\7\r\2\2\33\34\5\22\n"+
		"\2\34\35\7\r\2\2\35\3\3\2\2\2\36\37\7\3\2\2\37 \7\16\2\2 \5\3\2\2\2!$"+
		"\5\b\5\2\"$\5\f\7\2#!\3\2\2\2#\"\3\2\2\2$\7\3\2\2\2%(\7\16\2\2&\'\7\7"+
		"\2\2\')\5\b\5\2(&\3\2\2\2()\3\2\2\2)\t\3\2\2\2*+\7\4\2\2+\60\5\b\5\2,"+
		"-\7\13\2\2-/\5\b\5\2.,\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60\61\3\2\2\2\61"+
		"\13\3\2\2\2\62\60\3\2\2\2\63\66\7\16\2\2\64\65\7\7\2\2\65\67\5\f\7\2\66"+
		"\64\3\2\2\2\66\67\3\2\2\2\67\r\3\2\2\289\7\5\2\29>\5\f\7\2:;\7\13\2\2"+
		";=\5\f\7\2<:\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2\2\2?\17\3\2\2\2@>\3\2\2"+
		"\2AB\5\6\4\2BC\7\b\2\2CD\5\6\4\2DN\3\2\2\2EF\5\f\7\2FG\7\t\2\2GH\5\f\7"+
		"\2HN\3\2\2\2IJ\5\f\7\2JK\7\n\2\2KL\5\f\7\2LN\3\2\2\2MA\3\2\2\2ME\3\2\2"+
		"\2MI\3\2\2\2N\21\3\2\2\2OP\7\6\2\2PU\5\20\t\2QR\7\13\2\2RT\5\20\t\2SQ"+
		"\3\2\2\2TW\3\2\2\2US\3\2\2\2UV\3\2\2\2V\23\3\2\2\2WU\3\2\2\2\n\25#(\60"+
		"\66>MU";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}