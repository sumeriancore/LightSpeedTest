package ge.lightspeed.core.model.enums;

public enum QueryBlockType {

    SELECT(7, "SELECT"),
    FROM(5, "FROM"),
    WHERE(6, "WHERE"),
    GROUP_BY(9, "GROUP BY"),
    ORDER_BY(9, "ORDER BY"),
    HAVING(7, "HAVING"),
    JOIN(5, "JOIN"),
    LIMIT(6, "LIMIT"),
    OFFSET(7, "OFFSET");

    private final Integer typeLength;
    private final String type;

    QueryBlockType(Integer typeLength, String type) {
        this.typeLength = typeLength;
        this.type = type;
    }

    public Integer getTypeLength() {
        return typeLength;
    }

    public String getType() {
        return type;
    }
}
