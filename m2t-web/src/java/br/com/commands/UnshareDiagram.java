 package br.com.commands;
 
 import br.com.dao.UseCaseDiagramDao;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 
 
 
 
 
 public class UnshareDiagram
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String idDiagram = request.getParameter("iddiagram");
     String idCollaborator = request.getParameter("idcollaborator");
     
     if ((idDiagram != null) && (idCollaborator != null) && (!idDiagram.equals("")) && (!idCollaborator.equals(""))) {
       UseCaseDiagramDao dao = new UseCaseDiagramDao();
       System.out.println(idDiagram);
       System.out.println(idCollaborator);
       boolean ans = dao.unshareDiagram(Integer.parseInt(idDiagram), Integer.parseInt(idCollaborator));
       if (ans) {
         response.getWriter().write("unshared");
       } else {
         response.getWriter().write("fail-unshared");
       }
     }
     return "";
   }
 }


