package ge.lightspeed.core.model;

import ge.lightspeed.core.model.enums.SortType;

public class Sort {

    private String columnName;
    private SortType sortType;

    public Sort(String columnName, SortType sortType) {
        this.columnName = columnName;
        this.sortType = sortType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "columnName='" + columnName + '\'' +
                ", sortType=" + sortType +
                '}';
    }
}
