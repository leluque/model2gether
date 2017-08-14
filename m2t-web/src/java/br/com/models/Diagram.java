 package br.com.models;
 
 import java.util.List;
 
 
 public abstract class Diagram
   extends Entity
 {
   private String name;
   private List<Element> elements;
   private List<User> collaborators;
   
   public String getName()
   {
     return this.name;
   }
   
 
 
   public void setName(String name)
   {
     this.name = name;
   }
   
 
 
   public List<Element> getElements()
   {
     return this.elements;
   }
   
 
 
   public void setElements(List<Element> elements)
   {
     this.elements = elements;
   }
   
 
 
   public List<User> getCollaborators()
   {
     return this.collaborators;
   }
   
 
 
   public void setCollaborators(List<User> collaborators)
   {
     this.collaborators = collaborators;
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\models\Diagram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */