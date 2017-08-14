package br.com.models;

/**
 *
 * @author Davi
 */
public enum RelationType {

    COMMUNICATION(1),
    EXTENSION(2),
    INCLUSION(3),
    GENERALIZATION(4);

    private final int type;

    private RelationType(int type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public int getType() {
        return this.type;
    }

    public static RelationType getFromIntType(int type) {
        for (RelationType t : RelationType.values()) {
            if (t.type == type) {
                return t;
            }
        }
        return null;
    }

    public static RelationType getFromStringType(String type) {
        for (RelationType t : RelationType.values()) {
            if (t.name().equals(type)) {
                return t;
            }
        }
        return null;
    }
}
