package ge.lightspeed.core.model;

public class WhereClause {

    private String value;

    public WhereClause(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "WhereClause{" +
                "value='" + value + '\'' +
                '}';
    }
}
