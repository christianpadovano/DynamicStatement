package com.dynamicstatement.builder.base;

public class StatementMetadata {

    private String fieldName;
    private String operator;
    private String value;

    public StatementMetadata(String fieldName, String operator) {
        this.fieldName = fieldName;
        this.operator = operator;
    }

    public StatementMetadata(String fieldName, String operator,String value) {
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
