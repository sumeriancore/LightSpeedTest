package ge.lightspeed.core.service.impl;

import ge.lightspeed.core.factory.TableDataFactory;
import ge.lightspeed.core.factory.impl.ColumnFactory;
import ge.lightspeed.core.factory.impl.TableFactory;
import ge.lightspeed.core.model.*;
import ge.lightspeed.core.model.enums.JoinType;
import ge.lightspeed.core.model.enums.QueryBlockType;
import ge.lightspeed.core.model.enums.SortType;
import ge.lightspeed.core.service.SqlParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultFormattedSqlParser implements SqlParser {

    private static final String BLANK_SPACE = " ";
    private static final String SEMICOLON = ";";

    @Override
    public Query parseSqlQuery(String sqlQuery) {
        RawQuery rawQuery = parseRawQuery(sqlQuery);

        TableDataFactory<Table> tableFactory = new TableFactory();
        TableDataFactory<Column> columnFactory = new ColumnFactory();

        return Query.builder()
                .columns(parseTableInfo(rawQuery.getSelectRow(), columnFactory, QueryBlockType.SELECT))
                .tables(parseTableInfo(rawQuery.getFromRow(), tableFactory, QueryBlockType.FROM))
                .joins(parseJoins(rawQuery.getJoinRows()))
                .whereClauses(parseWhereClause(rawQuery.getWhereRow()))
                .groupByColumns(parseGroupBy(rawQuery.getGroupByRow()))
                .sortColumns(parseOrderBy(rawQuery.getOrderByRow()))
                .having(parseHaving(rawQuery.getHavingRow()))
                .limit(parseLimitOrOffset(rawQuery.getLimitRow()))
                .offset(parseLimitOrOffset(rawQuery.getOffsetRow()))
                .build();
    }

    private Integer parseLimitOrOffset(String limitOrOffsetRow) {
        limitOrOffsetRow = limitOrOffsetRow.trim().substring(limitOrOffsetRow.indexOf(BLANK_SPACE)).trim();
        return Integer.valueOf(
                limitOrOffsetRow.substring(
                                0,
                                limitOrOffsetRow.contains(SEMICOLON) ? limitOrOffsetRow.indexOf(SEMICOLON) : limitOrOffsetRow.length()
                        )
                        .trim()
        );
    }

    private Having parseHaving(String havingRow) {
        return new Having(havingRow.trim().substring(havingRow.indexOf(BLANK_SPACE)).trim());
    }

    private List<Sort> parseOrderBy(String orderByRow) {
        List<Sort> sorts = new ArrayList<>();
        String[] orderByColumns = orderByRow.substring(
                        QueryBlockType.ORDER_BY.getTypeLength(),
                        orderByRow.lastIndexOf(BLANK_SPACE)
                )
                .split(",");

        for (String column : orderByColumns) {
            SortType sortType = orderByRow.contains(SortType.DESC.getType()) ? SortType.DESC : SortType.ASC;
            column = column.trim();
            if (column.contains(SortType.ASC.getType()) || column.contains(SortType.DESC.getType())) {
                if (column.contains(SortType.DESC.getType())) {
                    sortType = SortType.DESC;
                }

                column = column.split(BLANK_SPACE)[0];
            }

            sorts.add(new Sort(column, sortType));
        }

        return sorts;
    }

    private List<String> parseGroupBy(String groupByRow) {
        return Arrays.asList(groupByRow.substring(QueryBlockType.GROUP_BY.getTypeLength()).split(","));
    }

    private WhereClause parseWhereClause(String whereRow) {
        return new WhereClause(whereRow.trim().substring(whereRow.indexOf(BLANK_SPACE)).trim());
    }

    private List<Join> parseJoins(List<String> joinRows) {
        List<Join> resultJoins = new ArrayList<>();

        for (String joinRow : joinRows) {
            boolean hasAlias = false;
            Join join = new Join();

            joinRow = joinRow.trim();

            if (joinRow.contains(" as ") || joinRow.contains(" AS ")) {
                hasAlias = true;
            }

            List<String> separatedJoinRow = Arrays.asList(joinRow.split(BLANK_SPACE));

            join.setJoinType(JoinType.fromString(separatedJoinRow.getFirst()));

            StringBuilder conditionBuilder = new StringBuilder();

            parseJoinCondition(separatedJoinRow, hasAlias, join, conditionBuilder);

            resultJoins.add(join);
        }

        return resultJoins;
    }

    private void parseJoinCondition(
            List<String> separatedJoinRow,
            boolean hasAlias,
            Join join,
            StringBuilder conditionBuilder
    ) {
        if (QueryBlockType.JOIN.getType().equals(separatedJoinRow.getFirst())) {
            int conditionIndexStart = hasAlias ? 5 : 3;
            fillJoin(conditionIndexStart, join, separatedJoinRow, conditionBuilder, 1);
        } else {
            int conditionIndexStart = hasAlias ? 6 : 4;
            fillJoin(conditionIndexStart, join, separatedJoinRow, conditionBuilder, 2);
        }
    }

    private void fillJoin(
            int conditionIndexStart,
            Join join,
            List<String> separatedJoinRow,
            StringBuilder conditionBuilder,
            Integer joinTableIndex
    ) {
        join.setOnJoinTable(separatedJoinRow.get(joinTableIndex).trim());

        for (int i = conditionIndexStart; i < separatedJoinRow.size(); i++) {
            conditionBuilder.append(separatedJoinRow.get(i));
        }

        join.setCondition(conditionBuilder.toString().trim());
    }

    private static <T extends DataSources> List<T> parseTableInfo(
            String sqlQueryChunk,
            TableDataFactory<T> factory,
            QueryBlockType type
    ) {
        List<T> resultTables = new ArrayList<>();

        List<String> tableDataAsStrings = Arrays.stream(
                sqlQueryChunk.trim().substring(type.getTypeLength()).split(",")
        ).toList();

        for (String tableDataString : tableDataAsStrings) {
            String name;
            String alias = "";

            tableDataString = tableDataString.trim();

            if (tableDataString.contains(" AS ") || tableDataString.contains(" as ") || tableDataString.contains(" ")) {
                alias = tableDataString.substring(tableDataString.lastIndexOf(BLANK_SPACE));
            }

            name = tableDataString.contains(BLANK_SPACE)
                    ? tableDataString.substring(0, tableDataString.indexOf(BLANK_SPACE))
                    : tableDataString;

            T tableDataInstance = factory.createTableData();
            tableDataInstance.setName(name.trim());
            tableDataInstance.setAlias(alias.trim());
            resultTables.add(tableDataInstance);
        }

        return resultTables;
    }

    private RawQuery parseRawQuery(String sqlQuery) {
        String[] sqlQueryLines = sqlQuery.split("\\n");
        RawQuery.Builder rawQueryBuilder = RawQuery.builder();
        for (String queryLine : sqlQueryLines) {
            if (queryLine.contains(QueryBlockType.SELECT.getType())) {
                rawQueryBuilder.selectRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.FROM.getType())) {
                rawQueryBuilder.fromRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.JOIN.getType())) {
                rawQueryBuilder.joinRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.WHERE.getType())) {
                rawQueryBuilder.whereRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.GROUP_BY.getType())) {
                rawQueryBuilder.groupByRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.ORDER_BY.getType())) {
                rawQueryBuilder.orderByRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.HAVING.getType())) {
                rawQueryBuilder.havingRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.LIMIT.getType())) {
                rawQueryBuilder.limitRow(queryLine);
            } else if (queryLine.contains(QueryBlockType.OFFSET.getType())) {
                rawQueryBuilder.offsetRow(queryLine);
            }
        }
        return rawQueryBuilder.build();
    }
}
