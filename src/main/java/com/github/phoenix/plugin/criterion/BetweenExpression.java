package com.github.phoenix.plugin.criterion;

/**
 * Between
 *
 * @author HuQingmiao
 */
class BetweenExpression extends Criteria {

    private String colLabel;
    private Object lo;
    private Object hi;

    protected BetweenExpression(String colLabel, Object lo, Object hi) {
        this.colLabel = colLabel;

        if (lo instanceof String) {
            this.lo = "\'" + lo.toString() + "\'";
        } else {
            this.lo = lo.toString();
        }

        if (hi instanceof String) {
            this.hi = "\'" + hi.toString() + "\'";
        } else {
            this.hi = hi.toString();
        }
    }


    public String toSqlString() {
        return colLabel + " BETWEEN " + lo + " AND " + hi;
    }
}
