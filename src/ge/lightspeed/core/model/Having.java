package ge.lightspeed.core.model;

public class Having {

    private String value;

    public Having(String value) {
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
        return "Having{" +
                "value='" + value + '\'' +
                '}';
    }
}
