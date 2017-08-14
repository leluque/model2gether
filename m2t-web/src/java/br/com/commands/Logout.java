 package br.com.commands;
 
 import br.com.util.UserUtil;
 import java.io.PrintStream;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;
 
 
 
 
 
 
 
 
 public class Logout
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     response.setContentType("text/html;charset=UTF-8");
     Cookie[] cookies = request.getCookies();
     String page = "signIn.jsp";
     
     if (cookies != null) {
       for (Cookie c : cookies) {
         if (c.getName().equals("JSESSIONID")) {
           System.out.println("JSESSIONID: " + c.getValue());
         }
         c.setMaxAge(0);
         response.addCookie(c);
       }
     }
     
     HttpSession session = request.getSession(false);
     if (session != null) {
       System.out.println("User: " + session.getAttribute("user"));
       session.invalidate();
     }
     UserUtil.logout();
     return page;
   }
 }


