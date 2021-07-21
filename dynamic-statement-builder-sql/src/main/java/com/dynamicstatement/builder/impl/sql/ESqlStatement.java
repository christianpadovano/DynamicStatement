package com.dynamicstatement.builder.impl.sql;

public enum ESqlStatement {

    WHERE(" WHERE"),
    AND(" AND ");

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
