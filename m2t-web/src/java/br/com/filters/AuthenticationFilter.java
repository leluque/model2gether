 package br.com.filters;
 
 import br.com.models.User;
 import br.com.util.UserUtil;
 import java.util.Calendar;
 import java.util.logging.Logger;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.annotation.WebFilter;
 import javax.servlet.http.HttpServletRequest;
 
 @WebFilter(urlPatterns={"/*"})
 public class AuthenticationFilter implements javax.servlet.Filter
 {
   private static final Logger logger = Logger.getLogger(AuthenticationFilter.class.getName());
   
   public void init(FilterConfig filterConfig)
     throws ServletException
   {}
   
   public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException
   {
     String page = ((HttpServletRequest)servletRequest).getRequestURI();
     User user = (User)((HttpServletRequest)servletRequest).getSession().getAttribute("user");
     if (user != null) {
       long now = Calendar.getInstance().getTimeInMillis();
       long diff = now - UserUtil.timer.getTimeInMillis();
       HttpServletRequest request = (HttpServletRequest)servletRequest;
       
 
 
 
 
 
 
 
 
 
 
       chain.doFilter(servletRequest, response);
     }
     else if ((page.equals(servletRequest.getServletContext().getContextPath() + "/ServletController1")) || 
       (page.startsWith(servletRequest.getServletContext().getContextPath() + "/assets/")) || 
       (page.contains("endpoint")) ||         
       (page.equals(servletRequest.getServletContext().getContextPath() + "/signUp.jsp")) || 
       (page.equals(servletRequest.getServletContext().getContextPath() + "/signIn.jsp"))) {
       chain.doFilter(servletRequest, response);
     } else {
       servletRequest.setAttribute("message", "filter.authentication.notLogged");
       servletRequest.getRequestDispatcher("signIn.jsp").forward(servletRequest, response);
     }
   }
   
   public void destroy() {}
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\filters\AuthenticationFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */