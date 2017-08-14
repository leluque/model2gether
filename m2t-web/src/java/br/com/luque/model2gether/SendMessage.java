package br.com.luque.model2gether;

import br.com.models.Actor;
import br.com.models.ElementType;
import br.com.models.Relation;
import br.com.models.RelationType;
import br.com.models.UseCase;
import br.com.models.UseCaseDiagram;
import br.com.websocket.HandlerDiagram;
import br.com.websocket.UMLDiagramEndpoint;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 */
public class SendMessage extends HttpServlet {

    private static final Set<HandlerDiagram> peers = Collections.synchronizedSet(new HashSet<HandlerDiagram>());
    private final JsonParser parser = new JsonParser();
    private final Gson gson = new Gson();

    public static final Map<HttpSession, String> idBySession = new HashMap();
    private final MessagePoolSingleton messagePoolSingleton = MessagePoolSingleton.getInstance();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        br.com.models.User user = (br.com.models.User) request.getSession().getAttribute("user");
        String id = request.getParameter("id");
        System.out.println("Mensagem recebida!");
        if (request.getParameter("open") != null) {
            System.out.println("OPEN: " + id);
            onOpen(request.getSession(), id);
        } else if (request.getParameter("close") != null) {
            System.out.println("CLOSE: " + id);
            onClose(request.getSession(), request.getParameter("id"));
        } else {
            System.out.println("MESSAGE: " + request.getParameter("msg"));
            broadcastMessage(request.getParameter("msg"), request.getSession(), request.getParameter("id"));
        }
        response.setContentType("application/json");
        response.getWriter().print("{}");
    }

    public void broadcastMessage(String message, HttpSession session, String id) {
//        System.out.println("broadcastMessage: " + message);
        try {
            HandlerDiagram handler = findOpenedDiagram(Integer.parseInt(id));
            if (message.contains("give-me-the-id")) {
                messagePoolSingleton.addMessage(session.getAttribute("user").toString(), id, "websocket-opened:" + idBySession.get(session));
                return;
            }
            JsonObject obj = parser.parse(message).getAsJsonObject();
            if (handler != null) {
                System.out.println("A mensagem recebida pelo servidor foi " + obj.get("msg").getAsString());
                if (obj.get("msg").getAsString().equals("initDiagram")) {
                    if (!handler.isOpened) {
                        initDiagram(handler.diagram, obj.getAsJsonObject("diagram"));
                        handler.isOpened = true;
                    }
                    JsonObject msg = new JsonObject();
                    msg.addProperty("msg", "openDiagram");
                    msg.add("diagram", parser.parse(gson.toJson(handler.diagram)));
                    System.out.println("Diagrama a ser aberto: ");
                    System.out.println(parser.parse(gson.toJson(handler.diagram)));
                    messagePoolSingleton.addMessage(session.getAttribute("user").toString(), id, msg.toString());
                } else {
                    processMessage(handler.diagram, obj);
                    for (HttpSession peer : handler.peers) {
                        if (!peer.equals(session)) {
                            try {
                                System.out.println("Adicionando mensagem para o peer " + peer.getAttribute("user").toString());
                                messagePoolSingleton.addMessage(peer.getAttribute("user").toString(), id, message);
                            } catch (Exception error) {
                            }
                        }
                    }
                }

            } else {
                System.err.println("Diagrama nao encontrado!!!");
            }
        } catch (Exception ex) {
            System.err.println("ERRO BroadcastMessage: " + ex);
        }
    }

    public static void onOpen(HttpSession peer, String id) {
        idBySession.put(peer, id);
        HandlerDiagram handler = findOpenedDiagram(Integer.parseInt(id));
        if (handler != null) {
            handler.peers.add(peer);
            System.out.println("O handler NÃO era null e o diagrama foi armazenado.");
        } else {
            handler = new HandlerDiagram();
            handler.diagram.setId(Integer.parseInt(id));
            handler.peers.add(peer);
            peers.add(handler);
            System.out.println("O handler era null e o diagrama foi armazenado.");
        }
    }

    public void onClose(HttpSession peer, String id) {
        HandlerDiagram handler = findOpenedDiagram(Integer.parseInt(id));
        if (handler != null) {
            handler.peers.remove(peer);
            if (handler.peers.isEmpty()) {
                peers.remove(handler);
            }
        }
    }

    private static HandlerDiagram findOpenedDiagram(int id) {
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
        if (obj.get("element") != null) {
            element = obj.get("element").getAsJsonObject();
        }
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
            br.com.models.Element element = (br.com.models.Element) el.newInstance();
            element.setId(obj.get("id").getAsInt());
            element.setName(obj.get("name").getAsString());
            element.setWidth(obj.get("width").getAsDouble());
            element.setHeight(obj.get("height").getAsDouble());
            element.setX(obj.get("x").getAsInt());
            element.setY(obj.get("y").getAsInt());
            diagram.getElements().add(element);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(UMLDiagramEndpoint.class.getName()).log(Level.SEVERE, null, ex);
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
        br.com.models.Element element = findElement(diagram, obj.get("id").getAsInt(), type);
        if (element != null) {
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
        if (typeElement != null) {           // Excluir elemento
            br.com.models.Element element = findElement(diagram, obj.get("id").getAsInt(), typeElement);
            diagram.getElements().remove(element);
            ArrayList<Relation> relations = findRelations(diagram, element);
            for (Relation rel : relations) {
                deleteElement(diagram, parser.parse(gson.toJson(rel, Relation.class)).getAsJsonObject());
            }
        } else if (typeRelation != null) {   // Excluir relacao
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
        br.com.models.Element element = findElement(diagram, obj.get("id").getAsInt(), type);

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
        br.com.models.Element source = findElement(diagram, obj.get("source").getAsJsonObject().get("id").getAsInt(), sourceType);

        ElementType targetType = null;
        switch (obj.get("target").getAsJsonObject().get("type").getAsString()) {
            case "ACTOR":
                targetType = ElementType.ACTOR;
                break;
            case "USE_CASE":
                targetType = ElementType.USE_CASE;
                break;
        }
        br.com.models.Element target = findElement(diagram, obj.get("target").getAsJsonObject().get("id").getAsInt(), targetType);

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
            br.com.models.Element el = null;
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
            br.com.models.Element source = findElement(diagram, list.get(i).getAsJsonObject().get("source").getAsJsonObject().get("id").getAsInt(), sourceType);

            ElementType targetType = null;
            switch (list.get(i).getAsJsonObject().get("target").getAsJsonObject().get("type").getAsString()) {
                case "ACTOR":
                    targetType = ElementType.ACTOR;
                    break;
                case "USE_CASE":
                    targetType = ElementType.USE_CASE;
                    break;
            }
            br.com.models.Element target = findElement(diagram, list.get(i).getAsJsonObject().get("target").getAsJsonObject().get("id").getAsInt(), targetType);

            Relation relation = new Relation();
            relation.setId(list.get(i).getAsJsonObject().get("id").getAsInt());
            relation.setType(RelationType.getFromStringType(list.get(i).getAsJsonObject().get("type").getAsString()));
            relation.setSource(source);
            relation.setTarget(target);

            diagram.getRelations().add(relation);
        }
        list = obj.getAsJsonArray("collaborators");
        for (int i = 0; i < list.size(); i++) {
            diagram.getCollaborators().add(gson.fromJson(list.get(i).toString(), br.com.models.User.class));
        }
    }

    private void renameDiagram(UseCaseDiagram diagram, JsonObject obj) {
        diagram.setName(obj.get("newName").getAsString());
    }

    private br.com.models.Element findElement(UseCaseDiagram diagram, int id, ElementType type) {
        for (br.com.models.Element el : diagram.getElements()) { // Atores e Casos de Uso podem ter o mesmo ID!!!
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

    private ArrayList<Relation> findRelations(UseCaseDiagram diagram, br.com.models.Element element) {
        ArrayList<Relation> relations = new ArrayList<>();
        for (Relation rel : diagram.getRelations()) {
            if (rel.getSource() == element || rel.getTarget() == element) {
                relations.add(rel);
            }
        }
        return relations;
    }

//    private MessagePoolSingleton messagePoolSingleton = MessagePoolSingleton.getInstance();
//    private final JsonParser parser = new JsonParser();
//    private final Gson gson = new Gson();
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        br.com.models.User user = (br.com.models.User) request.getSession().getAttribute("user");
//        String message = request.getParameter("msg");
//        JsonObject obj = parser.parse(message).getAsJsonObject();
//        if (obj.get("msg").getAsString().equals("initDiagram")) {
//            if (!messagePoolSingleton.isOpened()) {
//                initDiagram(messagePoolSingleton.getDiagram(request.getParameter("id")), obj.getAsJsonObject("diagram"));
//                messagePoolSingleton.open();
//            }
//            JsonObject msg = new JsonObject();
//            msg.addProperty("msg", "openDiagram");
//            msg.add("diagram", parser.parse(gson.toJson(messagePoolSingleton.getDiagram(request.getParameter("id")))));
//            messagePoolSingleton.addMessage(user.getEmail(), request.getParameter("id"), msg.toString());
//        } else {
//            processMessage(messagePoolSingleton.getDiagram(request.getParameter("id")), obj);
//            messagePoolSingleton.addMessage(user.getEmail(), request.getParameter("id"), message);
//        }
//    }
//
//    private void initDiagram(UseCaseDiagram diagram, JsonObject obj) {
//        diagram.setName(obj.get("name").getAsString());
//        JsonArray list = obj.getAsJsonArray("elements");
//        for (int i = 0; i < list.size(); i++) {
//            br.com.models.Element el = null;
//
//            switch (list.get(i).getAsJsonObject().get("type").getAsString()) {
//                case "ACTOR":
//                    el = (Actor) gson.fromJson(list.get(i).toString(), Actor.class
//                    );
//                    break;
//
//                case "USE_CASE":
//                    el = (UseCase) gson.fromJson(list.get(i).toString(), UseCase.class
//                    );
//                    break;
//            }
//            diagram.getElements().add(el);
//        }
//        list = obj.getAsJsonArray("relations");
//        for (int i = 0; i < list.size(); i++) {
//            ElementType sourceType = null;
//            switch (list.get(i).getAsJsonObject().get("source").getAsJsonObject().get("type").getAsString()) {
//                case "ACTOR":
//                    sourceType = ElementType.ACTOR;
//                    break;
//                case "USE_CASE":
//                    sourceType = ElementType.USE_CASE;
//                    break;
//            }
//            br.com.models.Element source = findElement(diagram, list.get(i).getAsJsonObject().get("source").getAsJsonObject().get("id").getAsInt(), sourceType);
//
//            ElementType targetType = null;
//            switch (list.get(i).getAsJsonObject().get("target").getAsJsonObject().get("type").getAsString()) {
//                case "ACTOR":
//                    targetType = ElementType.ACTOR;
//                    break;
//                case "USE_CASE":
//                    targetType = ElementType.USE_CASE;
//                    break;
//            }
//            br.com.models.Element target = findElement(diagram, list.get(i).getAsJsonObject().get("target").getAsJsonObject().get("id").getAsInt(), targetType);
//
//            Relation relation = new Relation();
//            relation.setId(list.get(i).getAsJsonObject().get("id").getAsInt());
//            relation.setType(RelationType.getFromStringType(list.get(i).getAsJsonObject().get("type").getAsString()));
//            relation.setSource(source);
//            relation.setTarget(target);
//
//            diagram.getRelations().add(relation);
//        }
//        list = obj.getAsJsonArray("collaborators");
//        for (int i = 0; i < list.size(); i++) {
//            diagram.getCollaborators().add(gson.fromJson(list.get(i).toString(), br.com.models.User.class
//            ));
//        }
//    }
//
//    private void processMessage(UseCaseDiagram diagram, JsonObject obj) {
//        String msg = obj.get("msg").getAsString();
//        // Avaliar msg para processar dados
//        JsonObject element = null;
//        if (obj.get("element") != null) {
//            element = obj.get("element").getAsJsonObject();
//        }
//        switch (msg) {
//            case "createUMLActor":
//                addElement(diagram, element, "Actor");
//                break;
//            case "createUMLUseCase":
//                addElement(diagram, element, "UseCase");
//                break;
//            case "dropElement":
//                dropElement(diagram, element);
//                break;
//            case "deleteElement":
//                deleteElement(diagram, element);
//                break;
//            case "renameElement":
//                renameElement(diagram, element);
//                break;
//            case "addUMLCommunication":
//                addRelation(diagram, obj, RelationType.COMMUNICATION);
//                break;
//            case "addUMLInclude":
//                addRelation(diagram, obj, RelationType.INCLUSION);
//                break;
//            case "addUMLExtend":
//                addRelation(diagram, obj, RelationType.EXTENSION);
//                break;
//            case "addUMLGeneralization":
//                addRelation(diagram, obj, RelationType.GENERALIZATION);
//                break;
//            case "renameDiagram":
//                renameDiagram(diagram, obj);
//                break;
//        }
//    }
//
//    private void addElement(UseCaseDiagram diagram, JsonObject obj, String elementName) {
//        try {
//            Class el = Class.forName("com.models.".concat(elementName));
//            br.com.models.Element element = (br.com.models.Element) el.newInstance();
//            element.setId(obj.get("id").getAsInt());
//            element.setName(obj.get("name").getAsString());
//            element.setWidth(obj.get("width").getAsDouble());
//            element.setHeight(obj.get("height").getAsDouble());
//            element.setX(obj.get("x").getAsInt());
//            element.setY(obj.get("y").getAsInt());
//            diagram.getElements().add(element);
//
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
//            Logger.getLogger(UMLDiagramEndpoint.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void dropElement(UseCaseDiagram diagram, JsonObject obj) {
//        ElementType type = null;
//        switch (obj.get("type").getAsString()) {
//            case "ACTOR":
//                type = ElementType.ACTOR;
//                break;
//            case "USE_CASE":
//                type = ElementType.USE_CASE;
//                break;
//        }
//        br.com.models.Element element = findElement(diagram, obj.get("id").getAsInt(), type);
//        if (element != null) {
//            element.setX(obj.get("x").getAsInt());
//            element.setY(obj.get("y").getAsInt());
//            element.setWidth(obj.get("width").getAsDouble());
//            element.setHeight(obj.get("height").getAsDouble());
//        }
//    }
//
//    private void deleteElement(UseCaseDiagram diagram, JsonObject obj) {
//        ElementType typeElement = null;
//        RelationType typeRelation = null;
//        switch (obj.get("type").getAsString()) {
//            case "ACTOR":
//                typeElement = ElementType.ACTOR;
//                break;
//            case "USE_CASE":
//                typeElement = ElementType.USE_CASE;
//                break;
//            case "COMMUNICATION":
//                typeRelation = RelationType.COMMUNICATION;
//                break;
//            case "INCLUSION":
//                typeRelation = RelationType.INCLUSION;
//                break;
//            case "EXTENSION":
//                typeRelation = RelationType.EXTENSION;
//                break;
//            case "GENERALIZATION":
//                typeRelation = RelationType.GENERALIZATION;
//                break;
//        }
//        if (typeElement != null) {           // Excluir elemento
//            br.com.models.Element element = findElement(diagram, obj.get("id").getAsInt(), typeElement);
//            diagram.getElements().remove(element);
//            ArrayList<Relation> relations = findRelations(diagram, element);
//
//            for (Relation rel : relations) {
//                deleteElement(diagram, parser.parse(gson.toJson(rel, Relation.class
//                )).getAsJsonObject());
//            }
//        } else if (typeRelation != null) {   // Excluir relacao
//            Relation relation = findRelation(diagram, obj.get("id").getAsInt(), typeRelation);
//            diagram.getRelations().remove(relation);
//        }
//
//    }
//
//    private void renameElement(UseCaseDiagram diagram, JsonObject obj) {
//        ElementType type = null;
//        switch (obj.get("type").getAsString()) {
//            case "ACTOR":
//                type = ElementType.ACTOR;
//                break;
//            case "USE_CASE":
//                type = ElementType.USE_CASE;
//                break;
//        }
//        br.com.models.Element element = findElement(diagram, obj.get("id").getAsInt(), type);
//
//        element.setName(obj.get("name").getAsString());
//    }
//
//    private void addRelation(UseCaseDiagram diagram, JsonObject obj, RelationType type) {
//        ElementType sourceType = null;
//        switch (obj.get("source").getAsJsonObject().get("type").getAsString()) {
//            case "ACTOR":
//                sourceType = ElementType.ACTOR;
//                break;
//            case "USE_CASE":
//                sourceType = ElementType.USE_CASE;
//                break;
//        }
//        br.com.models.Element source = findElement(diagram, obj.get("source").getAsJsonObject().get("id").getAsInt(), sourceType);
//
//        ElementType targetType = null;
//        switch (obj.get("target").getAsJsonObject().get("type").getAsString()) {
//            case "ACTOR":
//                targetType = ElementType.ACTOR;
//                break;
//            case "USE_CASE":
//                targetType = ElementType.USE_CASE;
//                break;
//        }
//        br.com.models.Element target = findElement(diagram, obj.get("target").getAsJsonObject().get("id").getAsInt(), targetType);
//
//        Relation relation = new Relation();
//        relation.setSource(source);
//        relation.setTarget(target);
//        relation.setType(type);
//        diagram.getRelations().add(relation);
//    }
//
//    private void renameDiagram(UseCaseDiagram diagram, JsonObject obj) {
//        diagram.setName(obj.get("newName").getAsString());
//    }
//
//    private br.com.models.Element findElement(UseCaseDiagram diagram, int id, ElementType type) {
//        for (br.com.models.Element el : diagram.getElements()) { // Atores e Casos de Uso podem ter o mesmo ID!!!
//            if (el.getId() == id && el.getType().equals(type)) {
//                return el;
//            }
//            if (el.getId() == id && el.getType().equals(type)) {
//                return el;
//            }
//        }
//        return null;
//    }
//
//    private Relation findRelation(UseCaseDiagram diagram, int id, RelationType type) {
//        for (Relation rel : diagram.getRelations()) {
//            if (rel.getId() == id && rel.getType().equals(type)) {
//                return rel;
//            }
//            if (rel.getId() == id && rel.getType().equals(type)) {
//                return rel;
//            }
//        }
//        return null;
//    }
//
//    private ArrayList<Relation> findRelations(UseCaseDiagram diagram, br.com.models.Element element) {
//        ArrayList<Relation> relations = new ArrayList<>();
//        for (Relation rel : diagram.getRelations()) {
//            if (rel.getSource() == element || rel.getTarget() == element) {
//                relations.add(rel);
//            }
//        }
//        return relations;
//    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
