 package br.com.models;
 
 public class UseCase extends Element
 {
   public UseCase() {
     super.setType(ElementType.USE_CASE);
   }
   
   public UseCase(String name, int x, int y, double width, double height) {
     super(name, x, y, width, height);
     super.setType(ElementType.USE_CASE);
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\models\UseCase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */