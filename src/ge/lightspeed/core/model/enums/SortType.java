package ge.lightspeed.core.model.enums;

public enum SortType {
    ASC("ASC"),
    DESC("DESC");

    private final String type;

    SortType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "SortType{" +
                "type='" + type + '\'' +
                '}';
    }
}
