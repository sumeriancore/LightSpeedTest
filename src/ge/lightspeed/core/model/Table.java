package ge.lightspeed.core.model;

public class Table extends TableData{

    private String name;

    private String alias;

    public Table() {
        super();
    }

    public Table(String name, String alias) {
        super(name, alias);
        this.name = name;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
