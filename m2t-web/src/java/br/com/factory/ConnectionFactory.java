 package br.com.factory;
 
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
 
 
 
 
 
 
 
 
 public class ConnectionFactory
 {
   private static final String driver = "com.mysql.jdbc.Driver";
   private static final String pwd = "XJ67IR73";
   private static final String user = "root";
   private static final String local = "jdbc:mysql://localhost:3306/model2gether";
   private static ConnectionFactory connectionFactory = null;
   
   private ConnectionFactory() {
     try {
       Class.forName("com.mysql.jdbc.Driver");
     } catch (ClassNotFoundException e) {
       System.out.println("ERRO CLASS FOR NAME: " + e);
     }
   }
   
   public Connection getConnection() {
     try {
       return DriverManager.getConnection(local, user, pwd);
     } catch (SQLException e) {
       System.out.println("ERRO GET CONNECTION" + e.getMessage());
     }
     return null;
   }
   
   public static synchronized ConnectionFactory getInstance() {
     if (connectionFactory == null) {
       connectionFactory = new ConnectionFactory();
     }
     return connectionFactory;
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\factory\ConnectionFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */