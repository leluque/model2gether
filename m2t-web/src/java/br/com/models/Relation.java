 package br.com.models;
 
 public class Relation extends Entity
 {
   private Element source;
   private Element target;
   private RelationType type;
   
   public Relation() {}
   
   public Relation(Element source, Element target, RelationType type)
   {
     this.source = source;
     this.target = target;
     this.type = type;
   }
   
 
 
   public Element getSource()
   {
     return this.source;
   }
   
 
 
   public void setSource(Element source)
   {
     this.source = source;
   }
   
 
 
   public Element getTarget()
   {
     return this.target;
   }
   
 
 
   public void setTarget(Element target)
   {
     this.target = target;
   }
   
 
 
   public RelationType getType()
   {
     return this.type;
   }
   
 
 
   public int getIntType()
   {
     return this.type.getType();
   }
   
 
 
   public void setType(RelationType type)
   {
     this.type = type;
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\models\Relation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */