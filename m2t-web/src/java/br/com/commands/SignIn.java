package br.com.commands;

import br.com.dao.UserDao;
import br.com.models.Entity;
import br.com.models.User;
import br.com.util.UserUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignIn
        extends ICommand {

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("name");
        String pass = request.getParameter("pass");

        String msgResponse = "sign-in.ok";
        String page;
        if ((pass != null) && (username != null) && (!username.trim().isEmpty()) && (!pass.trim().isEmpty())) {
            User usrDB = new User(username, pass);
            UserDao dao = new UserDao();
            User usr = (User) dao.find((Entity) usrDB);
            if (usr != null) {
                HttpSession session = request.getSession();
                usr.setPassword("");
                session.setAttribute("user", usr);
                UserUtil.setUserON(usr);
                Cookie userName = new Cookie("name", username);
                response.addCookie(userName);
                page = "index.jsp";
            } else {
                page = "signIn.jsp";
                msgResponse = "sign-in.inexistentUser";
            }
        } else if (UserUtil.getLoggedUser() != null) {
            User usrDB = UserUtil.getLoggedUser();
            UserDao dao = new UserDao();
            User usr = (User) dao.find((Entity) usrDB);
            if (usr != null) {
                HttpSession session = request.getSession();
                usr.setPassword("");
                session.setAttribute("user", usr);
                UserUtil.setUserON(usr);
                Cookie userName = new Cookie("name", username);
                response.addCookie(userName);
                page = "index.jsp";
            } else {
                page = "signIn.jsp";
                msgResponse = "sign-in.inexistentUser";
            }
        } else {
            page = "signIn.jsp";
        }
        request.setAttribute("message", msgResponse);
        return page;
    }

    public int getRedirectionType() {
        return 2;
    }
}
