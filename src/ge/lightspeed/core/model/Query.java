package ge.lightspeed.core.model;

import java.util.List;

public class Query {

    private final List<Column> columns;
    private final List<Table> tables;
    private final List<Join> joins;
    private final WhereClause whereClause;
    private final List<String> groupByColumns;
    private final List<Sort> sortColumns;
    private final Having having;
    private final Integer limit;
    private final Integer offset;

    private Query(Builder builder) {
        this.columns = builder.columns;
        this.tables = builder.tables;
        this.joins = builder.joins;
        this.whereClause = builder.whereClause;
        this.groupByColumns = builder.groupByColumns;
        this.sortColumns = builder.sortColumns;
        this.having = builder.having;
        this.limit = builder.limit;
        this.offset = builder.offset;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<Column> columns;
        private List<Table> tables;
        private List<Join> joins;
        private WhereClause whereClause;
        private List<String> groupByColumns;
        private List<Sort> sortColumns;
        private Having having;
        private Integer limit;
        private Integer offset;

        private Builder() {
        }

        public Builder columns(List<Column> columns) {
            this.columns = columns;
            return this;
        }

        public Builder tables(List<Table> tables) {
            this.tables = tables;
            return this;
        }

        public Builder joins(List<Join> joins) {
            this.joins = joins;
            return this;
        }

        public Builder whereClauses(WhereClause whereClauses) {
            this.whereClause = whereClauses;
            return this;
        }

        public Builder groupByColumns(List<String> groupByColumns) {
            this.groupByColumns = groupByColumns;
            return this;
        }

        public Builder sortColumns(List<Sort> sortColumns) {
            this.sortColumns = sortColumns;
            return this;
        }

        public Builder having(Having having) {
            this.having = having;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public Query build() {
            return new Query(this);
        }
    }

    @Override
    public String toString() {
        return "Query{" +
                "columns=" + columns +
                ", tables=" + tables +
                ", joins=" + joins +
                ", whereClauses=" + whereClause +
                ", groupByColumns=" + groupByColumns +
                ", sortColumns=" + sortColumns +
                ", having=" + having +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}
