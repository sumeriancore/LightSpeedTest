package ge.lightspeed.core.model;

import java.util.ArrayList;
import java.util.List;

public class RawQuery {

    private final String selectRow;
    private final String fromRow;
    private final List<String> joinRows;
    private final String whereRow;
    private final String groupByRow;
    private final String orderByRow;
    private final String havingRow;
    private final String limitRow;
    private final String offsetRow;

    private RawQuery(Builder builder) {
        this.selectRow = builder.selectRow;
        this.fromRow = builder.fromRow;
        this.joinRows = builder.joinRows;
        this.whereRow = builder.whereRow;
        this.groupByRow = builder.groupByRow;
        this.orderByRow = builder.orderByRow;
        this.havingRow = builder.havingRow;
        this.limitRow = builder.limitRow;
        this.offsetRow = builder.offsetRow;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String selectRow;
        private String fromRow;
        private List<String> joinRows = new ArrayList<>();
        private String whereRow;
        private String groupByRow;
        private String orderByRow;
        private String havingRow;
        private String limitRow;
        private String offsetRow;

        public Builder selectRow(String selectRow) {
            this.selectRow = selectRow;
            return this;
        }

        public Builder fromRow(String fromRow) {
            this.fromRow = fromRow;
            return this;
        }

        public Builder joinRow(String joinRow) {
            this.joinRows.add(joinRow);
            return this;
        }

        public Builder whereRow(String whereRow) {
            this.whereRow = whereRow;
            return this;
        }

        public Builder groupByRow(String groupByRow) {
            this.groupByRow = groupByRow;
            return this;
        }

        public Builder orderByRow(String orderByRow) {
            this.orderByRow = orderByRow;
            return this;
        }

        public Builder havingRow(String havingRow) {
            this.havingRow = havingRow;
            return this;
        }

        public Builder limitRow(String limitRow) {
            this.limitRow = limitRow;
            return this;
        }

        public Builder offsetRow(String offsetRow) {
            this.offsetRow = offsetRow;
            return this;
        }

        public RawQuery build() {
            return new RawQuery(this);
        }
    }

    public String getSelectRow() {
        return selectRow;
    }

    public String getFromRow() {
        return fromRow;
    }

    public List<String> getJoinRows() {
        return joinRows;
    }

    public String getWhereRow() {
        return whereRow;
    }

    public String getGroupByRow() {
        return groupByRow;
    }

    public String getOrderByRow() {
        return orderByRow;
    }

    public String getHavingRow() {
        return havingRow;
    }

    public String getLimitRow() {
        return limitRow;
    }

    public String getOffsetRow() {
        return offsetRow;
    }
}
