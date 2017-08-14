package br.com.dsls.useCaseDiagram;

import br.com.models.Actor;
import br.com.models.Element;
import br.com.models.UseCase;
import br.com.models.UseCaseDiagram;

/**
 *
 * @author Leandro Luque
 */
public class DSLToXML extends UseCaseDiagramBaseListener {
    
    private StringBuilder textDiagram;
    private final UseCaseDiagram diagrama;
    private int idRel = 1;

    public DSLToXML() {
        textDiagram = new StringBuilder();
        diagrama = new UseCaseDiagram();
    }

    @Override
    public void exitDiagramName(UseCaseDiagramParser.DiagramNameContext ctx) {
        textDiagram.append("<UMLUseCaseDiagram ");
        if(ctx.STRING() != null)
            textDiagram.append("name=\"").append(stripQuotes(ctx.STRING().getText())).append("\"");
        else
            textDiagram.append("name=\"New Diagram\"");
        textDiagram.append(">");
    }

    @Override
    public void exitActors(UseCaseDiagramParser.ActorsContext ctx) {
        StringBuilder sb = new StringBuilder();
        String mask = "<UMLActor id=\"act_%d\" x=\"100\" y=\"%d\" width=\"50\" height=\"70\" tagValues=\"\">"
                .concat("<item id=\"name\" value=\"%s\"/></UMLActor>");
        int id = 1;
        int distancia = 498 / (ctx.actor().size()+1); // 498 -> altura fixa do canvas
        int altura = distancia;
        for (UseCaseDiagramParser.ActorContext actx : ctx.actor()) {
            sb.append(String.format(mask, id, altura-35, stripQuotes(actx.STRING().getText())));
            altura += distancia;
            Actor actor = new Actor();
            actor.setId(id++);
            actor.setName(stripQuotes(actx.STRING().getText()));
            diagrama.getElements().add(actor);
        }
        textDiagram.append(sb);
    }

    @Override
    public void exitUseCases(UseCaseDiagramParser.UseCasesContext ctx) {
        StringBuilder sb = new StringBuilder();
        String mask = "<UMLUseCase id=\"uc_%d\" x=\"400\" y=\"%d\"  width=\"140\" height=\"40\" tagValues=\"\">"
                .concat("<item id=\"name\" value=\"%s\"/></UMLUseCase>");
        int id = 1;
        int distancia = 498 / (ctx.useCase().size()+1); // 498 -> altura fixa do canvas
        int altura = distancia;
        for (UseCaseDiagramParser.UseCaseContext uctx : ctx.useCase()) {
            sb.append(String.format(mask, id, altura-20, stripQuotes(uctx.STRING().getText())));
            altura += distancia;
            UseCase uc = new UseCase();
            uc.setId(id++);
            uc.setName(stripQuotes(uctx.STRING().getText()));
            diagrama.getElements().add(uc);
        }
        textDiagram.append(sb);
    }

    @Override
    public void exitRelationComm(UseCaseDiagramParser.RelationCommContext ctx) {
        Element source = getElementByName(stripQuotes(ctx.element(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.element(1).getText()));
        
        String sourceId = source instanceof Actor ?
                "act_".concat(Integer.toString(source.getId())) :
                "uc_".concat(Integer.toString(source.getId()));
        String targetId = target instanceof Actor ?
                "act_".concat(Integer.toString(target.getId())) :
                "uc_".concat(Integer.toString(target.getId()));
        
        textDiagram.append("<UMLCommunication id=\"".concat(Integer.toString(idRel++))
            .concat("\" side_A=\"").concat(sourceId)
            .concat("\" side_B=\"").concat(targetId).concat("\"></UMLCommunication>"));
    }

    @Override
    public void exitRelationExte(UseCaseDiagramParser.RelationExteContext ctx) {
        Element source = getElementByName(stripQuotes(ctx.useCase(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.useCase(1).getText()));
        
        String sourceId = "uc_".concat(Integer.toString(source.getId()));
        String targetId = "uc_".concat(Integer.toString(target.getId()));
        
        textDiagram.append("<UMLExtend id=\"".concat(Integer.toString(idRel++))
            .concat("\" side_A=\"").concat(sourceId)
            .concat("\" side_B=\"").concat(targetId).concat("\"><item id=\"stereotype\" value=\"«extend»\"/></UMLExtend>"));
    }

    @Override
    public void exitRelationIncl(UseCaseDiagramParser.RelationInclContext ctx) {
        Element source = getElementByName(stripQuotes(ctx.useCase(0).getText()));
        Element target = getElementByName(stripQuotes(ctx.useCase(1).getText()));
        
        String sourceId = "uc_".concat(Integer.toString(source.getId()));
        String targetId = "uc_".concat(Integer.toString(target.getId()));
        
        textDiagram.append("<UMLInclude id=\"".concat(Integer.toString(idRel++))
            .concat("\" side_A=\"").concat(sourceId)
            .concat("\" side_B=\"").concat(targetId).concat("\"><item id=\"stereotype\" value=\"«include»\"/></UMLInclude>"));
    }

    @Override
    public void exitUseCaseDiagram(UseCaseDiagramParser.UseCaseDiagramContext ctx) {
        textDiagram.append("</UMLUseCaseDiagram>");
    }
    
    
    public String getXML() {
        return textDiagram.toString();
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
