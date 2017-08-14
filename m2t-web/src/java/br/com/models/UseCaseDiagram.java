 package br.com.models;
 
 import java.util.ArrayList;
 import java.util.List;
 
 public class UseCaseDiagram extends Diagram
 {
   private List<Relation> relations;
   
   public UseCaseDiagram()
   {
     super.setElements(new ArrayList());
     super.setCollaborators(new ArrayList());
     this.relations = new ArrayList();
   }
   
   public UseCaseDiagram(String name, List<Element> elements, List<Relation> relations) {
     setName(name);
     setElements(elements);
     this.relations = relations;
   }
   
 
 
   public List<Relation> getRelations()
   {
     return this.relations;
   }
   
 
 
   public void setRelations(List<Relation> relations)
   {
     this.relations = relations;
   }
   
   public String toString()
   {
     StringBuilder sb = new StringBuilder();
     
     sb.append("Diagrama: ")
       .append(getName()).append("\n")
       .append("\tElementos:\n");
     
     for (Element e : getElements()) {
       sb.append("\t\t").append(e.getName()).append("\n");
     }
     
     sb.append("\tRelacoes:\n");
     
     for (Relation r : this.relations)
     {
 
 
       sb.append("\t\t").append(r.getSource().getName()).append(" ").append(r.getType()).append(" ").append(r.getTarget().getName()).append("\n");
     }
     
     return sb.toString();
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\models\UseCaseDiagram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */