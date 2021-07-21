package com.dynamicstatement.builder.impl.hql;

/**
 * @author christian padovano
 * @version 1.0
 */
public enum ESqlStatement {

    WHERE(" where"),
    AND(" and ");

    private String sqlOperator;

    ESqlStatement(String operator) {
        this.sqlOperator=operator;
    }

    public String getSqlOperator() {
        return sqlOperator;
    }

    public void setSqlOperator(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }
}
