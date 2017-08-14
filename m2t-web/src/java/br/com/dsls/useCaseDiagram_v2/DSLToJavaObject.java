package br.com.dsls.useCaseDiagram_v2;

import br.com.models.Actor;
import br.com.models.Element;
import br.com.models.Relation;
import br.com.models.RelationType;
import br.com.models.UseCase;
import br.com.models.UseCaseDiagram;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 *
 * @author Leandro Luque
 */
public class DSLToJavaObject extends UseCaseDiagramBaseListener {
    
    private final UseCaseDiagram diagrama;
    private boolean initRelation;
    private final Parser parser;

    public DSLToJavaObject(Parser parser) {
        this.diagrama = new UseCaseDiagram();
        this.initRelation = false;
        this.parser = parser;
    }

    @Override
    public void enterDiagramName(UseCaseDiagramParser.DiagramNameContext ctx) {
        diagrama.setName(stripQuotes(ctx.STRING().getText()));
    }
    
    @Override
    public void enterActor(UseCaseDiagramParser.ActorContext ctx) {
        if(initRelation) return;
        Actor ator = new Actor();
        ator.setName(stripQuotes(ctx.STRING().getText()));
        diagrama.getElements().add(ator);
    }

    @Override
    public void enterUseCase(UseCaseDiagramParser.UseCaseContext ctx) {
        if(initRelation) return;
        UseCase casoDeUso = new UseCase();
        casoDeUso.setName(stripQuotes(ctx.STRING().getText()));
        diagrama.getElements().add(casoDeUso);
    }

    @Override
    public void enterRelationComm(UseCaseDiagramParser.RelationCommContext ctx) {
        Relation relation = new Relation();
        
        Element source = getElementByName(stripQuotes(ctx.element(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.element(1).getText()));
        
        relation.setSource(source);
        relation.setTarget(target);
        relation.setType(RelationType.COMMUNICATION);
        
        diagrama.getRelations().add(relation);
        
        initRelation = true;
    }

    @Override
    public void enterRelationExte(UseCaseDiagramParser.RelationExteContext ctx) {
        Relation relation = new Relation();
        
        Element source = getElementByName(stripQuotes(ctx.useCase(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.useCase(1).getText()));
        
        source.setName(stripQuotes(ctx.useCase(0).getText()));
        target.setName(stripQuotes(ctx.useCase(1).getText()));
        
        relation.setSource(source);
        relation.setTarget(target);
        relation.setType(RelationType.EXTENSION);
        
        diagrama.getRelations().add(relation);
        
        initRelation = true;
    }

    @Override
    public void enterRelationIncl(UseCaseDiagramParser.RelationInclContext ctx) {
        Relation relation = new Relation();
        
        Element source = getElementByName(stripQuotes(ctx.useCase(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.useCase(1).getText()));
        
        source.setName(stripQuotes(ctx.useCase(0).getText()));
        target.setName(stripQuotes(ctx.useCase(1).getText()));
        
        relation.setSource(source);
        relation.setTarget(target);
        relation.setType(RelationType.INCLUSION);
        
        diagrama.getRelations().add(relation);
        
        initRelation = true;
    }

    @Override
    public void enterRelationInheAct(UseCaseDiagramParser.RelationInheActContext ctx) {
        Relation relation = new Relation();
        
        Element source = getElementByName(stripQuotes(ctx.actor(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.actor(1).getText()));
        
        source.setName(stripQuotes(ctx.actor(0).getText()));
        target.setName(stripQuotes(ctx.actor(1).getText()));
        
        relation.setSource(source);
        relation.setTarget(target);
        relation.setType(RelationType.GENERALIZATION);
        
        diagrama.getRelations().add(relation);
        
        initRelation = true;
    }

    @Override
    public void enterRelationInheUC(UseCaseDiagramParser.RelationInheUCContext ctx) {
        Relation relation = new Relation();
        
        Element source = getElementByName(stripQuotes(ctx.useCase(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.useCase(1).getText()));
        
        source.setName(stripQuotes(ctx.useCase(0).getText()));
        target.setName(stripQuotes(ctx.useCase(1).getText()));
        
        relation.setSource(source);
        relation.setTarget(target);
        relation.setType(RelationType.GENERALIZATION);
        
        diagrama.getRelations().add(relation);
        
        initRelation = true;
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        System.out.println("ERROR NODE: " + node);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        System.out.println("TERMINAL: " + node);
    }
    
    public UseCaseDiagram getDiagram() {
        return this.diagrama;
    }

    private Element getElementByName(String name) {
        for (Element el : diagrama.getElements()) {
            if(el.getName().equals(name))
                return el;
        }
        return null;
    }
    
    private String stripQuotes(String text) {
        if (text == null || text.charAt(0) != '"') {
            return text;
        }
        return text.substring(1, text.length() - 1);
    }
}
