 package br.com.commands;
 
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 
 
 
 
 
 
 
 public abstract class ICommand
 {
   public abstract String execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
     throws Exception;
   
   public int getRedirectionType()
   {
     return 1;
   }
 }


