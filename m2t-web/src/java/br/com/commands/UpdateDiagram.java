 package br.com.commands;
 
 import br.com.dao.UseCaseDiagramDao;
 import br.com.models.Actor;
 import br.com.models.Element;
 import br.com.models.UseCase;
 import br.com.models.UseCaseDiagram;
 import br.com.util.Util;
 import com.google.gson.Gson;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 public class UpdateDiagram
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String diagramJson = request.getParameter("diagram");
     UseCaseDiagramDao dao = new UseCaseDiagramDao();
     UseCaseDiagram diagram = Util.fromJSONToObject(diagramJson);
     System.out.println("DIAGRAM JSON: " + diagramJson);
     System.out.println("DIAGRAM:" + new Gson().toJson(diagram));
     
     dao.edit(diagram);
     for (Element element : diagram.getElements()) {
       if ((element instanceof Actor)) {
         dao.saveActor(element, diagram.getId());
       } else if ((element instanceof UseCase)) {
         dao.saveUseCase(element, diagram.getId());
       }
     }
     response.getWriter().write("1");
     
     return "";
   }
 }


