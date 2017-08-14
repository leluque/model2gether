 package br.com.models;
 
 public class Actor extends Element
 {
   public Actor() {
     super.setType(ElementType.ACTOR);
   }
   
   public Actor(String name, int x, int y, double width, double height) {
     super(name, x, y, width, height);
     super.setType(ElementType.ACTOR);
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\models\Actor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */