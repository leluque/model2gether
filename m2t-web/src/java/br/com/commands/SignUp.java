 package br.com.commands;
 
 import br.com.dao.UserDao;
 import br.com.models.User;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class SignUp
   extends ICommand
 {
   public String execute(HttpServletRequest request, HttpServletResponse response)
     throws Exception
   {
     String name = null;String pass = null;String email = null;String type = null;String page = null;String personName = null;
     
     personName = request.getParameter("personName");
     name = request.getParameter("name");
     email = request.getParameter("email");
     pass = request.getParameter("pass");
     type = request.getParameter("representationMode");
     String visualImpairment = request.getParameter("visualImpairment");
     String studied = request.getParameter("studiedComputing");
     String messageType = "error";
     String exist = "sign-up.error";
     if ((!name.trim().isEmpty()) && (!email.trim().isEmpty()) && (!pass.trim().isEmpty()) && (type != null)) {
       User user = new User(name, pass);
       UserDao dao = new UserDao();
       boolean ans = dao.find(user);
       if (!ans) {
         user.setName(personName);
         user.setVisualImpairment(br.com.models.VisualImpairment.values()[Integer.valueOf(visualImpairment).intValue()]);
         user.setHasStudied(br.com.models.HasStudied.values()[Integer.valueOf(studied).intValue()]);
         user.setEmail(email);
         user.setPreferentialDiagramMode(type.charAt(0) & 0xF);
         try {
           dao.add(user);
           page = "signIn.jsp";
           messageType = "success";
           exist = "sign-up.success";
         } catch (Exception e) {
           System.out.println(e.getMessage());
           exist = "sign-up.existingUser";
           page = "signUp.jsp";
         }
       } else {
         page = "signUp.jsp";
       }
     }
     request.setAttribute("messageType", messageType);
     request.setAttribute("message", exist);
     return page;
   }
 }


