package com.github.phoenix.plugin.criterion;

/**
 * Not 反向选择条件
 *
 * @author HuQingmiao
 */
class NotExpression extends Criteria {

    private Criteria criterion;

    protected NotExpression(Criteria criterion) {
        this.criterion = criterion;
    }

    public String toSqlString() {
        return " not " + criterion.toSqlString();
    }

}
