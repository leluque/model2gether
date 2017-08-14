 package br.com.servlets;
 
 import br.com.dsls.useCaseDiagram.ConvertModes;
 import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.ServletException;
 import javax.servlet.annotation.WebServlet;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @WebServlet(name="GraphicToText", urlPatterns={"/GraphicToText"})
 public class GraphicToText
   extends HttpServlet
 {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     response.setContentType("text/html;charset=UTF-8");
     
     String diagram = request.getParameter("diagram");
     
     String diagramText = ConvertModes.XMLToDSL(diagram);
     
     response.getWriter().write(diagramText);
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


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\servlets\GraphicToText.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */