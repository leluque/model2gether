

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import br.com.models.Actor;
import br.com.models.Element;
import br.com.models.ElementType;
import br.com.models.Relation;
import br.com.models.RelationType;
import br.com.models.UseCase;
import br.com.models.UseCaseDiagram;
import br.com.models.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Leandro Luque
 */
@ServerEndpoint(value = "/test")
public class Test {

    private static final Set<HandlerDiagram> peers = Collections.synchronizedSet(new HashSet<HandlerDiagram>());
    private final JsonParser parser = new JsonParser();
    private final Gson gson = new Gson();
    
    @OnMessage
    public void broadcastMessage(String message, Session session, @PathParam("id") String id) {
//        System.out.println("broadcastMessage: " + message);
        HandlerDiagram handler = findOpenedDiagram(Integer.parseInt(id));
        JsonObject obj = parser.parse(message).getAsJsonObject();
        if (handler != null) {
            try {
                if (obj.get("msg").getAsString().equals("initDiagram")) {
                    if (!handler.isOpened) {
                        initDiagram(handler.diagram, obj.getAsJsonObject("diagram"));
                        handler.isOpened = true;
                    }
                    JsonObject msg = new JsonObject();
                    msg.addProperty("msg", "openDiagram");
                    msg.add("diagram", parser.parse(gson.toJson(handler.diagram)));
                    session.getBasicRemote().sendText(msg.toString());
                } else {
                    processMessage(handler.diagram, obj);
                    for (Session peer : handler.peers) {
                        if (!peer.equals(session)) {
                            peer.getBasicRemote().sendText(message);
                        }
                    }
                }
            } catch (IOException ex) {
                System.err.println("ERRO BroadcastMessage: " + ex);
            }
        } else {
            System.err.println("Diagrama nao encontrado!!!");
        }
    }

    @OnOpen
    public void onOpen(Session peer, @PathParam("id") String id) {
        System.out.println("Abri o endpoint");
        HandlerDiagram handler = findOpenedDiagram(Integer.parseInt(id));
        if (handler != null) {
            handler.peers.add(peer);
        } else {
            handler = new HandlerDiagram();
            handler.diagram.setId(Integer.parseInt(id));
            handler.peers.add(peer);
            peers.add(handler);
        }
    }

    @OnClose
    public void onClose(Session peer, @PathParam("id") String id) {
        HandlerDiagram handler = findOpenedDiagram(Integer.parseInt(id));
        if (handler != null) {
            handler.peers.remove(peer);
            if(handler.peers.isEmpty())
                peers.remove(handler);
        }
    }
    
    @OnError
    public void onError(Session peer, Throwable t) {
        System.out.println("ERRO!" + t);
        t.printStackTrace();
    }
    
    private HandlerDiagram findOpenedDiagram(int id) {
        for (HandlerDiagram d : peers) {
            if (d.diagram.getId() == id) {
                return d;
            }
        }
        return null;
    }

    private void processMessage(UseCaseDiagram diagram, JsonObject obj) {
        String msg = obj.get("msg").getAsString();
        System.out.println(obj);
        // Avaliar msg para processar dados
        JsonObject element = null;
        if(obj.get("element") != null)
            element = obj.get("element").getAsJsonObject();
        switch (msg) {
            case "createUMLActor":
                addElement(diagram, element, "Actor");
                break;
            case "createUMLUseCase":
                addElement(diagram, element, "UseCase");
                break;
            case "dropElement":
                dropElement(diagram, element);
                break;
            case "deleteElement":
                deleteElement(diagram, element);
                break;
            case "renameElement":
                renameElement(diagram, element);
                break;
            case "addUMLCommunication":
                addRelation(diagram, obj, RelationType.COMMUNICATION);
                break;
            case "addUMLInclude":
                addRelation(diagram, obj, RelationType.INCLUSION);
                break;
            case "addUMLExtend":
                addRelation(diagram, obj, RelationType.EXTENSION);
                break;
            case "addUMLGeneralization":
                addRelation(diagram, obj, RelationType.GENERALIZATION);
                break;
            case "renameDiagram":
                renameDiagram(diagram, obj);
                break;
        }
    }
    
    private void addElement(UseCaseDiagram diagram, JsonObject obj, String elementName) {
        try {
            Class el = Class.forName("br.com.models.".concat(elementName));
            Element element = (Element) el.newInstance();
            element.setId(obj.get("id").getAsInt());
            element.setName(obj.get("name").getAsString());
            element.setWidth(obj.get("width").getAsDouble());
            element.setHeight(obj.get("height").getAsDouble());
            element.setX(obj.get("x").getAsInt());
            element.setY(obj.get("y").getAsInt());
            diagram.getElements().add(element);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void dropElement(UseCaseDiagram diagram, JsonObject obj) {
        ElementType type = null;
        switch (obj.get("type").getAsString()) {
            case "ACTOR":
                type = ElementType.ACTOR;
                break;
            case "USE_CASE":
                type = ElementType.USE_CASE;
                break;
        }
        Element element = findElement(diagram, obj.get("id").getAsInt(), type);
        if(element != null) {
            element.setX(obj.get("x").getAsInt());
            element.setY(obj.get("y").getAsInt());
            element.setWidth(obj.get("width").getAsDouble());
            element.setHeight(obj.get("height").getAsDouble());
        }
    }
    
    private void deleteElement(UseCaseDiagram diagram, JsonObject obj) {
        ElementType typeElement = null;
        RelationType typeRelation = null;
        switch (obj.get("type").getAsString()) {
            case "ACTOR":
                typeElement = ElementType.ACTOR;
                break;
            case "USE_CASE":
                typeElement = ElementType.USE_CASE;
                break;
            case "COMMUNICATION":
                typeRelation = RelationType.COMMUNICATION;
                break;
            case "INCLUSION":
                typeRelation = RelationType.INCLUSION;
                break;
            case "EXTENSION":
                typeRelation = RelationType.EXTENSION;
                break;
            case "GENERALIZATION":
                typeRelation = RelationType.GENERALIZATION;
                break;
        }
        if(typeElement != null) {           // Excluir elemento
            Element element = findElement(diagram, obj.get("id").getAsInt(), typeElement);
            diagram.getElements().remove(element);
            ArrayList<Relation> relations = findRelations(diagram, element);
            for (Relation rel: relations) {
                deleteElement(diagram, parser.parse(gson.toJson(rel, Relation.class)).getAsJsonObject());
            }
        } else if(typeRelation != null) {   // Excluir relacao
            Relation relation = findRelation(diagram, obj.get("id").getAsInt(), typeRelation);
            diagram.getRelations().remove(relation);
        }
        
    }
    
    private void renameElement(UseCaseDiagram diagram, JsonObject obj) {
        ElementType type = null;
        switch (obj.get("type").getAsString()) {
            case "ACTOR":
                type = ElementType.ACTOR;
                break;
            case "USE_CASE":
                type = ElementType.USE_CASE;
                break;
        }
        Element element = findElement(diagram, obj.get("id").getAsInt(), type);
        
        element.setName(obj.get("name").getAsString());
    }
    
    private void addRelation(UseCaseDiagram diagram, JsonObject obj, RelationType type) {
        ElementType sourceType = null;
        switch (obj.get("source").getAsJsonObject().get("type").getAsString()) {
            case "ACTOR":
                sourceType = ElementType.ACTOR;
                break;
            case "USE_CASE":
                sourceType = ElementType.USE_CASE;
                break;
        }
        Element source = findElement(diagram, obj.get("source").getAsJsonObject().get("id").getAsInt(), sourceType);

        ElementType targetType = null;
        switch (obj.get("target").getAsJsonObject().get("type").getAsString()) {
            case "ACTOR":
                targetType = ElementType.ACTOR;
                break;
            case "USE_CASE":
                targetType = ElementType.USE_CASE;
                break;
        }
        Element target = findElement(diagram, obj.get("target").getAsJsonObject().get("id").getAsInt(), targetType);

        Relation relation = new Relation();
        relation.setSource(source);
        relation.setTarget(target);
        relation.setType(type);
        diagram.getRelations().add(relation);
    }
    
    private void initDiagram(UseCaseDiagram diagram, JsonObject obj) {
        diagram.setName(obj.get("name").getAsString());
        JsonArray list = obj.getAsJsonArray("elements");
        for (int i = 0; i < list.size(); i++) {
            Element el = null;
            switch (list.get(i).getAsJsonObject().get("type").getAsString()) {
                case "ACTOR":
                    el = (Actor) gson.fromJson(list.get(i).toString(), Actor.class);
                    break;
                case "USE_CASE":
                    el = (UseCase) gson.fromJson(list.get(i).toString(), UseCase.class);
                    break;
            }
            diagram.getElements().add(el);
        }
        list = obj.getAsJsonArray("relations");
        for (int i = 0; i < list.size(); i++) {
            ElementType sourceType = null;
            switch (list.get(i).getAsJsonObject().get("source").getAsJsonObject().get("type").getAsString()) {
                case "ACTOR":
                    sourceType = ElementType.ACTOR;
                    break;
                case "USE_CASE":
                    sourceType = ElementType.USE_CASE;
                    break;
            }
            Element source = findElement(diagram, list.get(i).getAsJsonObject().get("source").getAsJsonObject().get("id").getAsInt(), sourceType);
            
            ElementType targetType = null;
            switch (list.get(i).getAsJsonObject().get("target").getAsJsonObject().get("type").getAsString()) {
                case "ACTOR":
                    targetType = ElementType.ACTOR;
                    break;
                case "USE_CASE":
                    targetType = ElementType.USE_CASE;
                    break;
            }
            Element target = findElement(diagram, list.get(i).getAsJsonObject().get("target").getAsJsonObject().get("id").getAsInt(), targetType);
            
            Relation relation = new Relation();
            relation.setId(list.get(i).getAsJsonObject().get("id").getAsInt());
            relation.setType(RelationType.getFromStringType(list.get(i).getAsJsonObject().get("type").getAsString()));
            relation.setSource(source);
            relation.setTarget(target);
            
            diagram.getRelations().add(relation);
        }
        list = obj.getAsJsonArray("collaborators");
        for (int i = 0; i < list.size(); i++) {
            diagram.getCollaborators().add(gson.fromJson(list.get(i).toString(), User.class));
        }
    }
    
    private  void renameDiagram(UseCaseDiagram diagram, JsonObject obj) {
        diagram.setName(obj.get("newName").getAsString());
    }
    
    private Element findElement(UseCaseDiagram diagram, int id, ElementType type) {
        for (Element el : diagram.getElements()) { // Atores e Casos de Uso podem ter o mesmo ID!!!
            if (el.getId() == id && el.getType().equals(type)) {
                return el;
            }
            if (el.getId() == id && el.getType().equals(type)) {
                return el;
            }
        }
        return null;
    }
    
    private Relation findRelation(UseCaseDiagram diagram, int id, RelationType type) {
        for (Relation rel : diagram.getRelations()) {
            if (rel.getId() == id && rel.getType().equals(type)) {
                return rel;
            }
            if (rel.getId() == id && rel.getType().equals(type)) {
                return rel;
            }
        }
        return null;
    }
    
    private ArrayList<Relation> findRelations(UseCaseDiagram diagram, Element element) {
        ArrayList<Relation> relations = new ArrayList<>();
        for (Relation rel : diagram.getRelations()) {
            if (rel.getSource() == element || rel.getTarget() == element)
                relations.add(rel);
        }
        return relations;
    }
    
    private class HandlerDiagram {
        public UseCaseDiagram diagram = new UseCaseDiagram();
        public Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
        public boolean isOpened = false;
    }

}
