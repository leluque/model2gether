 package br.com.models;
 
 public abstract class Element extends Entity
 {
   private String name;
   private int x;
   private int y;
   private double width;
   private double height;
   private ElementType type;
   
   public Element() {}
   
   public Element(String name, int x, int y, double width, double height) {
     this.name = name;
     this.x = x;
     this.y = y;
     this.width = width;
     this.height = height;
   }
   
 
 
   public String getName()
   {
     return this.name;
   }
   
 
 
   public void setName(String name)
   {
     this.name = name;
   }
   
 
 
   public int getX()
   {
     return this.x;
   }
   
 
 
   public void setX(int x)
   {
     this.x = x;
   }
   
 
 
   public int getY()
   {
     return this.y;
   }
   
 
 
   public void setY(int y)
   {
     this.y = y;
   }
   
 
 
   public double getWidth()
   {
     return this.width;
   }
   
 
 
   public void setWidth(double width)
   {
     this.width = width;
   }
   
 
 
   public double getHeight()
   {
     return this.height;
   }
   
 
 
   public void setHeight(double height)
   {
     this.height = height;
   }
   
 
 
   public ElementType getType()
   {
     return this.type;
   }
   
 
 
   public int getIntType()
   {
     return this.type.getType();
   }
   
 
 
   public void setType(ElementType type)
   {
     this.type = type;
   }
 }


/* Location:              C:\Users\leand\Desktop\br.zip!\br\com\models\Element.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */