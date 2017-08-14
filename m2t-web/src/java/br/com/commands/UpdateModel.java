 package br.com.commands;
 
 import br.com.dao.UseCaseDiagramDao;
 import br.com.models.UseCaseDiagram;
 import java.util.Locale;
 import java.util.ResourceBundle;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 
 
 public class UpdateModel
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String paramLanguage = request.getParameter("language");
     String reqLanguage = (String)request.getAttribute("language");
     
     String language = reqLanguage != null ? reqLanguage : paramLanguage != null ? paramLanguage : request.getLocale().getLanguage();
     ResourceBundle bundle = ResourceBundle.getBundle("br.com.i18n.resources");
     
     int id = 0;
     try {
       id = Integer.parseInt(request.getParameter("id"));
     } catch (Exception e) {
       request.setAttribute("message", bundle.getString("updateModel.unexpectedError"));
       return "index.jsp";
     }
     
 
     UseCaseDiagramDao dao = new UseCaseDiagramDao();
     
     if (request.getParameter("remove") != null) {
       try {
         dao.delete(id);
         request.setAttribute("message", bundle.getString("updateModel.successfullyDeleted"));
       } catch (Exception e) {
         e.printStackTrace();
         request.setAttribute("message", bundle.getString("updateModel.errorDeleting"));
       }
     } else if (request.getParameter("update") != null) {
       String newName = request.getParameter("name");
       if ((newName == null) || (newName.trim().isEmpty())) {
         request.setAttribute("message", bundle.getString("updateModel.successfullyUpdated"));
       }
       else {
         UseCaseDiagram caseDiagram = new UseCaseDiagram();
         caseDiagram.setId(id);
         caseDiagram.setName(newName);
         dao.edit(caseDiagram);
         request.setAttribute("message", bundle.getString("updateModel.errorUpdating"));
       }
     }
     return "index.jsp";
   }
   
   public int getRedirectionType()
   {
     return 2;
   }
 }


