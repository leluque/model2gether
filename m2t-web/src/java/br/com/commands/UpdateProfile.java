 package br.com.commands;
 
 import br.com.dao.UserDao;
 import br.com.models.User;
 import br.com.util.UserUtil;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 
 
 
 
 
 
 public class UpdateProfile
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String input = request.getParameter("update");
     User user = UserUtil.getLoggedUser();
     UserDao dao = new UserDao();
     boolean edit = false;
     if ((input != null) && (input.equals("profile"))) {
       String mail = request.getParameter("mail");
       user.setEmail(mail);
       edit = dao.edit(user);
     } else if ((input != null) && (input.equals("security"))) {
       String pass = request.getParameter("passwd2");
       String name = request.getParameter("username");
       user.setPassword(pass);
       user.setName(name);
       edit = dao.edit(user);
     }
     
     if (edit) {
       request.setAttribute("message", "atualizado");
     } else {
       request.setAttribute("message", "erro");
     }
     
     return "profile.jsp";
   }
 }


