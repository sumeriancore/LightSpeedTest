package ge.lightspeed.core.service.impl;

import ge.lightspeed.core.model.*;
import ge.lightspeed.core.model.enums.JoinType;
import ge.lightspeed.core.model.enums.SortType;
import ge.lightspeed.core.service.SqlParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parses a raw SQL query, meaning that keywords like 'FROM' can be written as 'From' or query can have some
 * not formatted spaces etc.
 */
public class DefaultRawSqlParser implements SqlParser {

    @Override
    public Query parseSqlQuery(String sqlQuery) {
        String[] limitAndOffset = parseLimitAndOffset(sqlQuery).split(",");

        return Query.builder()
                .columns(parseColumns(sqlQuery))
                .tables(parseTableNames(sqlQuery))
                .joins(parseJoins(sqlQuery))
                .whereClauses(parseWhereClause(sqlQuery))
                .groupByColumns(parseGroupBy(sqlQuery))
                .sortColumns(parseOrderBy(sqlQuery))
                .having(parseHaving(sqlQuery))
                .limit(!"null".equals(limitAndOffset[0]) ? Integer.parseInt(limitAndOffset[0]) : null)
                .offset(!"null".equals(limitAndOffset[1]) ? Integer.parseInt(limitAndOffset[1]) : null)
                .build();
    }

    private List<Column> parseColumns(String sqlQuery) {
        String selectClause = "";

        Pattern selectPattern = Pattern.compile("(?i)SELECT\\s+(.+?)\\s+FROM");
        Matcher selectMatcher = selectPattern.matcher(sqlQuery);
        if (selectMatcher.find()) {
            selectClause = selectMatcher.group(1).trim();
        }

        String[] columns = selectClause.split(",");

        List<Column> columnList = new ArrayList<>();

        for (String column : columns) {
            String[] columnParts = column.trim().split("\\s+AS\\s+|\\s+");
            String columnName = columnParts[0];
            String alias = (columnParts.length > 1) ? columnParts[columnParts.length - 1] : columnName;
            columnList.add(new Column(columnName, alias));
        }

        return columnList;
    }

    private List<Table> parseTableNames(String sqlQuery) {
        List<Table> tables = new ArrayList<>();
        String fromClause = "";

        Pattern fromPattern = Pattern.compile("(?i)FROM\\s+([^;]+?)(?=\\s+(INNER|LEFT|RIGHT|FULL|OUTER|CROSS|JOIN|ON|WHERE|GROUP BY|HAVING|ORDER BY|;|$))");
        Matcher fromMatcher = fromPattern.matcher(sqlQuery);

        if (fromMatcher.find()) {
            fromClause = fromMatcher.group(1).trim();
        }

        String[] possibleTables = fromClause.split(",");

        for (String possibleTable : possibleTables) {
            boolean hasAlias = possibleTable.contains(" as ") || possibleTable.contains(" AS ");
            String[] tableNameChunks = possibleTable.trim().split("\\s+");

            String alias = "";

            if (hasAlias && tableNameChunks.length > 2) {
                alias = tableNameChunks[tableNameChunks.length - 1];
            } else if (tableNameChunks.length > 1) {
                alias = tableNameChunks[1];
            }
            String tableName = tableNameChunks[0];
            tables.add(new Table(tableName, alias));
        }

        return tables;
    }

    private List<Join> parseJoins(String sqlQuery) {
        List<Join> joins = new ArrayList<>();
        String regex = "(?i)\\b(?<joinType>LEFT|RIGHT|INNER|OUTER|NATURAL)?\\s*JOIN\\s+(?<table>\\S+)\\s*(?:as\\s+(?<alias>\\w+))?\\s+ON\\s+(?<condition>.+?)(?=\\s+(?:LEFT|RIGHT|INNER|FULL|OUTER|WHERE|GROUP|ORDER|HAVING|LIMIT|OFFSET|$))";

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(sqlQuery);

        while (matcher.find()) {
            JoinType joinType = JoinType.fromString(matcher.group("joinType").split(" ")[0].trim());
            String tableName = matcher.group("table");
            String condition = matcher.group("condition");

            joins.add(new Join(joinType, condition, tableName));
        }

        return joins;
    }

    private WhereClause parseWhereClause(String sqlQuery) {
        String whereClause = "";

        Pattern wherePattern = Pattern.compile("(?i)WHERE\\s+([^;]+?)(?=\\s+(GROUP BY|ORDER BY|HAVING|LIMIT|OFFSET|;|$))");
        Matcher whereMatcher = wherePattern.matcher(sqlQuery);
        if (whereMatcher.find()) {
            whereClause = whereMatcher.group(1).trim();
        }

        return new WhereClause(whereClause);
    }

    private Having parseHaving(String sqlQuery) {
        String havingCondition = "";
        String regex = "(?i)HAVING\\s+(.+?)(?=\\s+(ORDER\\s+BY|LIMIT|$))";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(sqlQuery);

        if (matcher.find()) {
            havingCondition = matcher.group(1).trim();
        }

        return new Having(havingCondition);
    }

    private List<String> parseGroupBy(String sqlQuery) {
        List<String> groupByConditions = new ArrayList<>();
        String regex = "(?i)GROUP\\s+BY\\s+(.+?)(?=\\s+(HAVING|ORDER\\s+BY|LIMIT|$))";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(sqlQuery);

        if (matcher.find()) {
            groupByConditions = Arrays.stream(matcher.group(1).trim().split(",")).collect(Collectors.toList());
        }

        return groupByConditions;
    }

    private List<Sort> parseOrderBy(String sqlQuery) {
        List<Sort> orderByConditions = new ArrayList<>();
        String regex = "(?i)ORDER\\s+BY\\s+(.+?)(?=\\s+(HAVING|LIMIT|OFFSET|$))";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(sqlQuery);

        if (matcher.find()) {
            String columnsAsString = matcher.group(1);
            String[] columns = columnsAsString.trim().split(",");

            for (String column : columns) {
                column = column.trim();

                SortType sortType = columnsAsString.contains(SortType.DESC.getType()) ? SortType.DESC : SortType.ASC;

                if (column.contains(SortType.ASC.getType()) || column.contains(SortType.DESC.getType())) {

                    if (column.contains(SortType.DESC.getType())) {
                        sortType = SortType.DESC;
                    }

                    column = column.substring(0, column.indexOf(" "));
                }
                orderByConditions.add(new Sort(column, sortType));
            }
        }

        return orderByConditions;
    }

    private String parseLimitAndOffset(String sqlQuery) {
        String result = "";

        String limitOffsetRegex = "(?i)LIMIT\\s+([\\d]+)(?:\\s+OFFSET\\s+(\\d+))?";
        Pattern limitOffsetPattern = Pattern.compile(limitOffsetRegex);
        Matcher limitOffsetMatcher = limitOffsetPattern.matcher(sqlQuery);

        if (limitOffsetMatcher.find()) {
            String limit = limitOffsetMatcher.group(1);
            String offset = limitOffsetMatcher.group(2);

            result = limit + "," + offset;
        }
        return result;
    }
}
