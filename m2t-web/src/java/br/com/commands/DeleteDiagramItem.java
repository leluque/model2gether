 package br.com.commands;
 
 import br.com.dao.UseCaseDiagramDao;
 import br.com.models.Actor;
 import br.com.models.Relation;
 import br.com.models.UseCase;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 
 public class DeleteDiagramItem
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String type = request.getParameter("itemType");
     int id = Integer.parseInt(request.getParameter("itemId"));
     UseCaseDiagramDao dao = new UseCaseDiagramDao();
     boolean success = false;
     
     switch (type) {
     case "ACTOR": 
       Actor actor = new Actor();
       actor.setId(id);
       success = dao.removeActor(actor);
       break;
     case "USE_CASE": 
       UseCase useCase = new UseCase();
       useCase.setId(id);
       success = dao.removeUseCase(useCase);
       break;
     case "COMMUNICATION": 
     case "EXTENSION": 
     case "INCLUSION": 
     case "GENERALIZATION": 
       Relation relation = new Relation();
       relation.setId(id);
       success = dao.removeRelation(relation);
     }
     
     
     if (success) {
       response.getWriter().write("1");
     } else {
       response.getWriter().write("0");
     }
     return "";
   }
 }


