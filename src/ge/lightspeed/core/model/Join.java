package ge.lightspeed.core.model;

import ge.lightspeed.core.model.enums.JoinType;

public class Join {

    private JoinType joinType;

    private String condition;

    private String onJoinTable;

    public Join() {
    }

    public Join(JoinType joinType, String condition, String onJoinTable) {
        this.joinType = joinType;
        this.condition = condition;
        this.onJoinTable = onJoinTable;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOnJoinTable() {
        return onJoinTable;
    }

    public void setOnJoinTable(String onJoinTable) {
        this.onJoinTable = onJoinTable;
    }

    @Override
    public String toString() {
        return "Join{" +
                "joinType=" + joinType +
                ", condition='" + condition + '\'' +
                ", onJoinTable='" + onJoinTable + '\'' +
                '}';
    }
}
