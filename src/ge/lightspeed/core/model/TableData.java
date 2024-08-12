package ge.lightspeed.core.model;

public abstract class TableData {

    protected String name;

    protected String alias;

    public TableData() {
    }

    public TableData(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
