
package br.com.dsls.useCaseDiagram;

import br.com.models.Relation;
import br.com.models.UseCaseDiagram;
import br.com.models.UseCase;
import br.com.models.Actor;
import br.com.models.Element;
import br.com.models.RelationType;

/**
 *
 * @author Leandro Luque
 */
public class DSLToJavaObject extends UseCaseDiagramBaseListener {
    
    private final UseCaseDiagram diagrama;
    private boolean isParent;
    private boolean initRelation;

    public DSLToJavaObject() {
        diagrama = new UseCaseDiagram();
        initRelation = false;
    }

    @Override
    public void enterDiagramName(UseCaseDiagramParser.DiagramNameContext ctx) {
        diagrama.setName(stripQuotes(ctx.STRING().getText()));
    }

    @Override
    public void enterActor(UseCaseDiagramParser.ActorContext ctx) {
        if(isParent | initRelation) {
            isParent = false;
            return;
        }
        Actor ator = new Actor();
        ator.setName(stripQuotes(ctx.STRING().getText()));
        if(ctx.INHERITANCE() != null) {
            Actor pai = new Actor();
            pai.setName(stripQuotes(ctx.actor().STRING().getText()));
            //ator.setParent(pai);
            isParent = true;
        }
        
        diagrama.getElements().add(ator);
    }

    @Override
    public void exitActors(UseCaseDiagramParser.ActorsContext ctx) {
        int i = 1;
        for (Element el : diagrama.getElements()) {
            if (el instanceof Actor) {
                el.setId(i++);
            }
        }
    }

    @Override
    public void enterUseCase(UseCaseDiagramParser.UseCaseContext ctx) {
        if(isParent | initRelation) {
            isParent = false;
            return;
        }
        UseCase casoDeUso = new UseCase();
        casoDeUso.setName(stripQuotes(ctx.STRING().getText()));
        if(ctx.INHERITANCE() != null) {
            UseCase pai = new UseCase();
            pai.setName(stripQuotes(ctx.useCase().STRING().getText()));
            //casoDeUso.setParent(pai);
            isParent = true;
        }
        
        diagrama.getElements().add(casoDeUso);
    }

    @Override
    public void exitUseCases(UseCaseDiagramParser.UseCasesContext ctx) {
        int i = 1;
        for (Element el : diagrama.getElements()) {
            if (el instanceof UseCase) {
                el.setId(i++);
            }
        }
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
        
        if(source==null || target==null) return;
        
        source.setName(stripQuotes(ctx.useCase(0).getText()));
        target.setName(stripQuotes(ctx.useCase(1).getText()));
        
        relation.setSource(source);
        relation.setTarget(target);
        relation.setType(RelationType.INCLUSION);
        
        diagrama.getRelations().add(relation);
        
        initRelation = true;
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
