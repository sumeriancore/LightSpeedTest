package ge.lightspeed.core.model.enums;

import java.util.Locale;

public enum JoinType {
    INNER("INNER"),
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    FULL("FULL");

    private final String name;

    JoinType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static JoinType fromString(String name) {
        if ("JOIN".equals(name)) {
            return JoinType.INNER;
        }
        for (JoinType joinType : JoinType.values()) {
            if (joinType.getName().equals(name.toUpperCase(Locale.ROOT))) {
                return joinType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "JoinType{" +
                "name='" + name + '\'' +
                '}';
    }
}
