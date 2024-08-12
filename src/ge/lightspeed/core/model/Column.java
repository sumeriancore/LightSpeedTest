package ge.lightspeed.core.model;

public class Column extends TableData{

    private String name;
    private String alias;

    public Column() {
        super();
    }

    public Column(String name, String alias) {
        super(name, alias);
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

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
