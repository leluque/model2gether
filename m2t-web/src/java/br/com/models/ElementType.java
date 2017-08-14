 package br.com.models;
/**
 *
 * @author Davi
 */
public enum ElementType {
    
    ACTOR(1),
    USE_CASE(2);
    
    private final int type;

    private ElementType(int type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public int getType() {
        return this.type;
    }
    
    public static ElementType getFromIntType(int type) {
        for (ElementType t : ElementType.values()) {
            if (t.type == type)
                return t;
        }
        return null;
    }
    
    public static ElementType getFromStringType(String type) {
        for (ElementType t : ElementType.values()) {
            if (t.name().equals(type))
                return t;
        }
        return null;
    }
}
