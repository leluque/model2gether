 package br.com.dao;
 
 import br.com.factory.ConnectionFactory;
 import br.com.models.Entity;
 import java.io.PrintStream;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.List;
 
 
 
 public abstract class Dao
 {
   protected Connection conn;
   protected PreparedStatement st;
   protected ResultSet rs;
   
   protected void open()
   {
     this.conn = ConnectionFactory.getInstance().getConnection();
   }
   
   protected void close()
   {
     try {
       if (this.conn != null)
         this.conn.close();
       if (this.st != null)
         this.st.close();
       if (this.rs != null)
         this.rs.close();
     } catch (SQLException ex) {
       System.out.println("ERRO CLOSE: " + ex);
     }
   }
   
   public abstract Entity add(Entity paramEntity);
   
   public abstract boolean edit(Entity paramEntity);
   
   public abstract Entity find(Entity paramEntity);
   
   public abstract boolean remove(Entity paramEntity);
   
   public abstract List<Entity> listAll();
 }


