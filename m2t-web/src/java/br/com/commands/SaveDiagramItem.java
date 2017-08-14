 package br.com.commands;
 
 import br.com.dao.UseCaseDiagramDao;
 import br.com.models.Actor;
 import br.com.models.Element;
 import br.com.models.Relation;
 import br.com.models.RelationType;
 import br.com.models.UseCase;
 import com.google.gson.Gson;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 public class SaveDiagramItem
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String item = request.getParameter("itemToSave");
     String itemType = request.getParameter("itemType");
     String itemJson = request.getParameter("itemJson");
     int idDiagram = Integer.parseInt(request.getParameter("idDiagram"));
     Gson gson = new Gson();
     UseCaseDiagramDao dao = new UseCaseDiagramDao();
     
     switch (item) {
     case "element": 
       Element element = null;
       if (itemType.equals("ACTOR")) {
         element = (Element)gson.fromJson(itemJson, Actor.class);
         element = (Element)dao.saveActor(element, idDiagram);
       } else if (itemType.equals("USE_CASE")) {
         element = (Element)gson.fromJson(itemJson, UseCase.class);
         element = (Element)dao.saveUseCase(element, idDiagram);
       }
       if (element != null) {
         response.getWriter().write(String.valueOf(element.getId()));
       } else
         response.getWriter().write("0");
       break;
     case "relation": 
       Relation relation = new Relation();
       RelationType relationType = null;
       if (itemType.equals("COMMUNICATION")) {
         relationType = RelationType.COMMUNICATION;
       } else if (itemType.equals("EXTENSION")) {
         relationType = RelationType.EXTENSION;
       } else if (itemType.equals("INCLUSION")) {
         relationType = RelationType.INCLUSION;
       } else if (itemType.equals("GENERALIZATION")) {
         relationType = RelationType.GENERALIZATION;
       }
       Class sourceClass = null;Class targetClass = null;
       JsonObject relationJson = new JsonParser().parse(itemJson).getAsJsonObject();
       JsonObject sourceJson = relationJson.get("source").getAsJsonObject();
       JsonObject targetJson = relationJson.get("target").getAsJsonObject();
       if (sourceJson.get("type").getAsString().equals("ACTOR")) {
         sourceClass = Actor.class;
       } else if (sourceJson.get("type").getAsString().equals("USE_CASE"))
         sourceClass = UseCase.class;
       if (targetJson.get("type").getAsString().equals("ACTOR")) {
         targetClass = Actor.class;
       } else if (targetJson.get("type").getAsString().equals("USE_CASE"))
         targetClass = UseCase.class;
       Element source = (Element)gson.fromJson(sourceJson, sourceClass);
       Element target = (Element)gson.fromJson(targetJson, targetClass);
       relation.setType(relationType);
       relation.setSource(source);
       relation.setTarget(target);
       relation = (Relation)dao.saveRelation(relation);
       if (relation != null) {
         response.getWriter().write(String.valueOf(relation.getId()));
       } else {
         response.getWriter().write("0");
       }
       break;
     }
     return "";
   }
 }


