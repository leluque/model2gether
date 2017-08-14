 package br.com.commands;
 
 import br.com.dao.UserDao;
 import br.com.models.User;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class ShareDiagram
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String emailUser = request.getParameter("email");
     String idDiagram = request.getParameter("diagram");
     
     if ((emailUser != null) && (idDiagram != null) && (!emailUser.equals("")) && (!idDiagram.equals(""))) {
       User usrDB = new User();
       UserDao usrDao = new UserDao();
       usrDB.setEmail(emailUser);
       
 
 
       User find = (User)usrDao.find(usrDB, Integer.parseInt(idDiagram));
       if (find == null) {
         response.getWriter().write("notshared");
       } else {
         response.getWriter().write("shared");
       }
     }
     return "";
   }
 }


