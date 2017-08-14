// Generated from G:\_TG\_Testes\WebAccessibleUML\src\java\com\dsls\u005CuseCaseDiagram_v2\UseCaseDiagram.g4 by ANTLR 4.4
package br.com.dsls.useCaseDiagram_v2;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class UseCaseDiagramParser extends Parser {

    static {
        RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache
            = new PredictionContextCache();
    public static final int DIAGRAM = 1, ACTORS = 2, USE_CASES = 3, RELATIONS = 4, INHERITANCE = 5, COMMUNICATION = 6,
            EXTENSION = 7, INCLUSION = 8, COMMA = 9, DOT = 10, STRING = 11, WS = 12;
    public static final String[] tokenNames = {
        "<INVALID>", "DIAGRAM", "'Atores'", "'Casos de Uso'", "'Relacionamentos'", "'herda de'",
        "'se associa com'", "'estende'", "'inclui'", "COMMA", "'.'", "STRING", "WS"
    };
    public static final int RULE_useCaseDiagram = 0, RULE_diagramName = 1, RULE_element = 2, RULE_actor = 3,
            RULE_actors = 4, RULE_useCase = 5, RULE_useCases = 6, RULE_relation = 7,
            RULE_relations = 8;
    public static final String[] ruleNames = {
        "useCaseDiagram", "diagramName", "element", "actor", "actors", "useCase",
        "useCases", "relation", "relations"
    };

    @Override
    public String getGrammarFileName() {
        return "UseCaseDiagram.g4";
    }

    @Override
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public UseCaseDiagramParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class UseCaseDiagramContext extends ParserRuleContext {

        public RelationsContext relations() {
            return getRuleContext(RelationsContext.class, 0);
        }

        public UseCasesContext useCases() {
            return getRuleContext(UseCasesContext.class, 0);
        }

        public DiagramNameContext diagramName() {
            return getRuleContext(DiagramNameContext.class, 0);
        }

        public ActorsContext actors() {
            return getRuleContext(ActorsContext.class, 0);
        }

        public UseCaseDiagramContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_useCaseDiagram;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterUseCaseDiagram(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitUseCaseDiagram(this);
            }
        }
    }

    public final UseCaseDiagramContext useCaseDiagram() throws RecognitionException {
        UseCaseDiagramContext _localctx = new UseCaseDiagramContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_useCaseDiagram);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(18);
                diagramName();
                setState(19);
                actors();
                setState(20);
                useCases();
                setState(21);
                relations();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class DiagramNameContext extends ParserRuleContext {

        public TerminalNode DIAGRAM() {
            return getToken(UseCaseDiagramParser.DIAGRAM, 0);
        }

        public TerminalNode STRING() {
            return getToken(UseCaseDiagramParser.STRING, 0);
        }

        public DiagramNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_diagramName;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterDiagramName(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitDiagramName(this);
            }
        }
    }

    public final DiagramNameContext diagramName() throws RecognitionException {
        DiagramNameContext _localctx = new DiagramNameContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_diagramName);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(23);
                match(DIAGRAM);
                setState(24);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ElementContext extends ParserRuleContext {

        public ElementContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_element;
        }

        public ElementContext() {
        }

        public void copyFrom(ElementContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class ElementActorContext extends ElementContext {

        public ActorContext actor() {
            return getRuleContext(ActorContext.class, 0);
        }

        public ElementActorContext(ElementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterElementActor(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitElementActor(this);
            }
        }
    }

    public static class ElementUseCaseContext extends ElementContext {

        public UseCaseContext useCase() {
            return getRuleContext(UseCaseContext.class, 0);
        }

        public ElementUseCaseContext(ElementContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterElementUseCase(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitElementUseCase(this);
            }
        }
    }

    public final ElementContext element() throws RecognitionException {
        ElementContext _localctx = new ElementContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_element);
        try {
            setState(28);
            switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                case 1:
                    _localctx = new ElementActorContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                     {
                        setState(26);
                        actor();
                    }
                    break;
                case 2:
                    _localctx = new ElementUseCaseContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                     {
                        setState(27);
                        useCase();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ActorContext extends ParserRuleContext {

        public TerminalNode STRING() {
            return getToken(UseCaseDiagramParser.STRING, 0);
        }

        public ActorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_actor;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterActor(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitActor(this);
            }
        }
    }

    public final ActorContext actor() throws RecognitionException {
        ActorContext _localctx = new ActorContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_actor);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(30);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ActorsContext extends ParserRuleContext {

        public TerminalNode DOT() {
            return getToken(UseCaseDiagramParser.DOT, 0);
        }

        public ActorContext actor(int i) {
            return getRuleContext(ActorContext.class, i);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(UseCaseDiagramParser.COMMA);
        }

        public TerminalNode ACTORS() {
            return getToken(UseCaseDiagramParser.ACTORS, 0);
        }

        public List<ActorContext> actor() {
            return getRuleContexts(ActorContext.class);
        }

        public TerminalNode COMMA(int i) {
            return getToken(UseCaseDiagramParser.COMMA, i);
        }

        public ActorsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_actors;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterActors(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitActors(this);
            }
        }
    }

    public final ActorsContext actors() throws RecognitionException {
        ActorsContext _localctx = new ActorsContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_actors);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(32);
                match(ACTORS);
                setState(43);
                _la = _input.LA(1);
                if (_la == STRING) {
                    {
                        setState(33);
                        actor();
                        setState(38);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == COMMA) {
                            {
                                {
                                    setState(34);
                                    match(COMMA);
                                    setState(35);
                                    actor();
                                }
                            }
                            setState(40);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(41);
                        match(DOT);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class UseCaseContext extends ParserRuleContext {

        public TerminalNode STRING() {
            return getToken(UseCaseDiagramParser.STRING, 0);
        }

        public UseCaseContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_useCase;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterUseCase(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitUseCase(this);
            }
        }
    }

    public final UseCaseContext useCase() throws RecognitionException {
        UseCaseContext _localctx = new UseCaseContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_useCase);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(45);
                match(STRING);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class UseCasesContext extends ParserRuleContext {

        public List<UseCaseContext> useCase() {
            return getRuleContexts(UseCaseContext.class);
        }

        public TerminalNode DOT() {
            return getToken(UseCaseDiagramParser.DOT, 0);
        }

        public TerminalNode USE_CASES() {
            return getToken(UseCaseDiagramParser.USE_CASES, 0);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(UseCaseDiagramParser.COMMA);
        }

        public UseCaseContext useCase(int i) {
            return getRuleContext(UseCaseContext.class, i);
        }

        public TerminalNode COMMA(int i) {
            return getToken(UseCaseDiagramParser.COMMA, i);
        }

        public UseCasesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_useCases;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterUseCases(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitUseCases(this);
            }
        }
    }

    public final UseCasesContext useCases() throws RecognitionException {
        UseCasesContext _localctx = new UseCasesContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_useCases);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(47);
                match(USE_CASES);
                setState(58);
                _la = _input.LA(1);
                if (_la == STRING) {
                    {
                        setState(48);
                        useCase();
                        setState(53);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == COMMA) {
                            {
                                {
                                    setState(49);
                                    match(COMMA);
                                    setState(50);
                                    useCase();
                                }
                            }
                            setState(55);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(56);
                        match(DOT);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class RelationContext extends ParserRuleContext {

        public RelationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_relation;
        }

        public RelationContext() {
        }

        public void copyFrom(RelationContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class RelationInheUCContext extends RelationContext {

        public List<UseCaseContext> useCase() {
            return getRuleContexts(UseCaseContext.class);
        }

        public TerminalNode INHERITANCE() {
            return getToken(UseCaseDiagramParser.INHERITANCE, 0);
        }

        public UseCaseContext useCase(int i) {
            return getRuleContext(UseCaseContext.class, i);
        }

        public RelationInheUCContext(RelationContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterRelationInheUC(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitRelationInheUC(this);
            }
        }
    }

    public static class RelationCommContext extends RelationContext {

        public List<ElementContext> element() {
            return getRuleContexts(ElementContext.class);
        }

        public ElementContext element(int i) {
            return getRuleContext(ElementContext.class, i);
        }

        public TerminalNode COMMUNICATION() {
            return getToken(UseCaseDiagramParser.COMMUNICATION, 0);
        }

        public RelationCommContext(RelationContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterRelationComm(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitRelationComm(this);
            }
        }
    }

    public static class RelationInheActContext extends RelationContext {

        public ActorContext actor(int i) {
            return getRuleContext(ActorContext.class, i);
        }

        public List<ActorContext> actor() {
            return getRuleContexts(ActorContext.class);
        }

        public TerminalNode INHERITANCE() {
            return getToken(UseCaseDiagramParser.INHERITANCE, 0);
        }

        public RelationInheActContext(RelationContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterRelationInheAct(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitRelationInheAct(this);
            }
        }
    }

    public static class RelationInclContext extends RelationContext {

        public List<UseCaseContext> useCase() {
            return getRuleContexts(UseCaseContext.class);
        }

        public UseCaseContext useCase(int i) {
            return getRuleContext(UseCaseContext.class, i);
        }

        public TerminalNode INCLUSION() {
            return getToken(UseCaseDiagramParser.INCLUSION, 0);
        }

        public RelationInclContext(RelationContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterRelationIncl(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitRelationIncl(this);
            }
        }
    }

    public static class RelationExteContext extends RelationContext {

        public List<UseCaseContext> useCase() {
            return getRuleContexts(UseCaseContext.class);
        }

        public UseCaseContext useCase(int i) {
            return getRuleContext(UseCaseContext.class, i);
        }

        public TerminalNode EXTENSION() {
            return getToken(UseCaseDiagramParser.EXTENSION, 0);
        }

        public RelationExteContext(RelationContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterRelationExte(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitRelationExte(this);
            }
        }
    }

    public final RelationContext relation() throws RecognitionException {
        RelationContext _localctx = new RelationContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_relation);
        try {
            setState(80);
            switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
                case 1:
                    _localctx = new RelationCommContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                     {
                        setState(60);
                        element();
                        setState(61);
                        match(COMMUNICATION);
                        setState(62);
                        element();
                    }
                    break;
                case 2:
                    _localctx = new RelationExteContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                     {
                        setState(64);
                        useCase();
                        setState(65);
                        match(EXTENSION);
                        setState(66);
                        useCase();
                    }
                    break;
                case 3:
                    _localctx = new RelationInclContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                     {
                        setState(68);
                        useCase();
                        setState(69);
                        match(INCLUSION);
                        setState(70);
                        useCase();
                    }
                    break;
                case 4:
                    _localctx = new RelationInheUCContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                     {
                        setState(72);
                        useCase();
                        setState(73);
                        match(INHERITANCE);
                        setState(74);
                        useCase();
                    }
                    break;
                case 5:
                    _localctx = new RelationInheActContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                     {
                        setState(76);
                        actor();
                        setState(77);
                        match(INHERITANCE);
                        setState(78);
                        actor();
                    }
                    break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class RelationsContext extends ParserRuleContext {

        public List<RelationContext> relation() {
            return getRuleContexts(RelationContext.class);
        }

        public TerminalNode DOT() {
            return getToken(UseCaseDiagramParser.DOT, 0);
        }

        public List<TerminalNode> COMMA() {
            return getTokens(UseCaseDiagramParser.COMMA);
        }

        public RelationContext relation(int i) {
            return getRuleContext(RelationContext.class, i);
        }

        public TerminalNode COMMA(int i) {
            return getToken(UseCaseDiagramParser.COMMA, i);
        }

        public TerminalNode RELATIONS() {
            return getToken(UseCaseDiagramParser.RELATIONS, 0);
        }

        public RelationsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_relations;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).enterRelations(this);
            }
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof UseCaseDiagramListener) {
                ((UseCaseDiagramListener) listener).exitRelations(this);
            }
        }
    }

    public final RelationsContext relations() throws RecognitionException {
        RelationsContext _localctx = new RelationsContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_relations);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(82);
                match(RELATIONS);
                setState(93);
                _la = _input.LA(1);
                if (_la == STRING) {
                    {
                        setState(83);
                        relation();
                        setState(88);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == COMMA) {
                            {
                                {
                                    setState(84);
                                    match(COMMA);
                                    setState(85);
                                    relation();
                                }
                            }
                            setState(90);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(91);
                        match(DOT);
                    }
                }

            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static final String _serializedATN
            = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\16b\4\2\t\2\4\3\t"
            + "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2"
            + "\3\2\3\2\3\3\3\3\3\3\3\4\3\4\5\4\37\n\4\3\5\3\5\3\6\3\6\3\6\3\6\7\6\'"
            + "\n\6\f\6\16\6*\13\6\3\6\3\6\5\6.\n\6\3\7\3\7\3\b\3\b\3\b\3\b\7\b\66\n"
            + "\b\f\b\16\b9\13\b\3\b\3\b\5\b=\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"
            + "\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tS\n\t\3\n\3\n\3\n\3\n"
            + "\7\nY\n\n\f\n\16\n\\\13\n\3\n\3\n\5\n`\n\n\3\n\2\2\13\2\4\6\b\n\f\16\20"
            + "\22\2\2c\2\24\3\2\2\2\4\31\3\2\2\2\6\36\3\2\2\2\b \3\2\2\2\n\"\3\2\2\2"
            + "\f/\3\2\2\2\16\61\3\2\2\2\20R\3\2\2\2\22T\3\2\2\2\24\25\5\4\3\2\25\26"
            + "\5\n\6\2\26\27\5\16\b\2\27\30\5\22\n\2\30\3\3\2\2\2\31\32\7\3\2\2\32\33"
            + "\7\r\2\2\33\5\3\2\2\2\34\37\5\b\5\2\35\37\5\f\7\2\36\34\3\2\2\2\36\35"
            + "\3\2\2\2\37\7\3\2\2\2 !\7\r\2\2!\t\3\2\2\2\"-\7\4\2\2#(\5\b\5\2$%\7\13"
            + "\2\2%\'\5\b\5\2&$\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)+\3\2\2\2*(\3"
            + "\2\2\2+,\7\f\2\2,.\3\2\2\2-#\3\2\2\2-.\3\2\2\2.\13\3\2\2\2/\60\7\r\2\2"
            + "\60\r\3\2\2\2\61<\7\5\2\2\62\67\5\f\7\2\63\64\7\13\2\2\64\66\5\f\7\2\65"
            + "\63\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28:\3\2\2\29\67\3\2\2"
            + "\2:;\7\f\2\2;=\3\2\2\2<\62\3\2\2\2<=\3\2\2\2=\17\3\2\2\2>?\5\6\4\2?@\7"
            + "\b\2\2@A\5\6\4\2AS\3\2\2\2BC\5\f\7\2CD\7\t\2\2DE\5\f\7\2ES\3\2\2\2FG\5"
            + "\f\7\2GH\7\n\2\2HI\5\f\7\2IS\3\2\2\2JK\5\f\7\2KL\7\7\2\2LM\5\f\7\2MS\3"
            + "\2\2\2NO\5\b\5\2OP\7\7\2\2PQ\5\b\5\2QS\3\2\2\2R>\3\2\2\2RB\3\2\2\2RF\3"
            + "\2\2\2RJ\3\2\2\2RN\3\2\2\2S\21\3\2\2\2T_\7\6\2\2UZ\5\20\t\2VW\7\13\2\2"
            + "WY\5\20\t\2XV\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2\2\2\\Z\3\2"
            + "\2\2]^\7\f\2\2^`\3\2\2\2_U\3\2\2\2_`\3\2\2\2`\23\3\2\2\2\n\36(-\67<RZ"
            + "_";
    public static final ATN _ATN
            = new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}
