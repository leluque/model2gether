 package br.com.servlets;
 
 import br.com.commands.ICommand;
 import br.com.util.CommandHelper;
 import java.io.IOException;
 import java.io.PrintStream;
 import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletException;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @WebServlet(name="ServletController", urlPatterns={"/ServletController"})
 public class ServletController
   extends HttpServlet
 {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     response.setContentType("text/html;charset=UTF-8");
     String action = request.getParameter("action");
     ICommand command = CommandHelper.create(action);
     String pagina = null;
     try
     {
       pagina = command.execute(request, response);
     } catch (Exception e) {
         e.printStackTrace();
       System.out.println("Erro:" + e + "\nMensagem: " + e.getMessage() + "\nCausa: " + e.getCause());
     }
     
     if ((pagina != null) && (!pagina.equals(""))) {
       if (command.getRedirectionType() == 1) {
         request.getRequestDispatcher(pagina).forward(request, response);
       }
       else {
         response.sendRedirect(response.encodeRedirectURL(pagina + "?message=" + (String)request.getAttribute("message")));
       }
     }
   }
   
 
 
 
 
 
 
 
 
 
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     processRequest(request, response);
   }
   
 
 
 
 
 
 
 
 
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     processRequest(request, response);
   }
   
 
 
 
 
 
   public String getServletInfo()
   {
     return "Short description";
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\servlets\ServletController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */