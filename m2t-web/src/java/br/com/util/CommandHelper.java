 package br.com.util;
 
 import br.com.commands.ICommand;
 
 public class CommandHelper
 {
   public static ICommand create(String command)
   {
     String result = "br.com.commands.".concat(command);
     ICommand retorno = null;
     try
     {
       Class classe = Class.forName(result);
       retorno = (ICommand)classe.newInstance();
     } catch (ClassNotFoundException|InstantiationException|IllegalAccessException e) {
       System.out.println("ERRO CREATE COMMAND: " + e);
     }
     
     return retorno;
   }
 }


