package ge.lightspeed.core.model;

public abstract class DataSources {

    protected String name;

    protected String alias;

    public DataSources() {
    }

    public DataSources(String name, String alias) {
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
