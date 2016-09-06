package com.github.phoenix.plugin.criterion;


/**
 * Like
 *
 * @author HuQingmiao
 */
class LikeExpression extends Criteria {

    private String colLabel;

    private String value;

    private Character escapeChar;

    protected LikeExpression(String colLabel, String value, Character escapeChar) {
        this.colLabel = colLabel;
        String v = value.toString();
        this.value = v.replace("\'", "''");// 将条件中的单引号替换成丙个单引号
        this.escapeChar = escapeChar;
    }

    protected LikeExpression(String colLabel, String value, char escapeChar) {
        this.colLabel = colLabel;
        String v = value.toString();
        this.value = v.replace("\'", "''");// 将条件中的单引号替换成丙个单引号
        this.escapeChar = new Character(escapeChar);
    }

    protected LikeExpression(String colLabel, String value) {
        this.colLabel = colLabel;
        String v = value.toString();
        this.value = v.replace("\'", "''");// 将条件中的单引号替换成丙个单引号
        this.escapeChar = null;
    }

    public String toSqlString() {

        StringBuffer buff = new StringBuffer();
        buff.append(this.colLabel).append(" like \'").append(value).append("\'");

        if (escapeChar != null) {
            buff.append(" escape \'").append(escapeChar).append("\'");
        }

        return buff.toString();
    }

}
