package com.dynamicstatement.builder.interf;


/**
 * Where condition declaration interface
 * Basic supported where conditions
 * @author c.padovano
 * @version 1.0
 */
public interface IWhere  {


    public IWhere and(String freecondition, boolean... isConditionVerified);
    public IWhere or(String freecondition, boolean... isConditionVerified);
    public IWhere and();
    public IWhere or();


    public IWhere greater(String sourceField, String targetField,boolean... isConditionVerified);
    public IWhere lessthan(String sourceField, String targetField,boolean... isConditionVerified);
    public IWhere greaterOrEq(String sourceField, String targetField,boolean... isConditionVerified);
    public IWhere lessthanOrEq(String sourceField, String targetField,boolean... isConditionVerified);


    public IWhere between(String fieldName, String parameterNameFrom, String parameterNameTo, boolean... isConditionVerified);
    public IWhere isNull(String fieldName, boolean... isConditionVerified);
    public IWhere isNotNull(String fieldName, boolean... isConditionVerified);

    public IWhere in(String fieldName, IStatement innerSelectStatement, boolean... isConditionVerified);

    /**
     * implements the 'IN' keyword
     * @param fieldName table field name
     * @param parameterName parameter name for the query builder
     * @param isConditionVerified true to add this condition to the statements
     * @return builder istance
     */
    public IWhere in(String fieldName, String[] parameterNameOrValues, boolean... isConditionVerified);

    public IWhere notin(String fieldName, IStatement innerSelectStatement, boolean... isConditionVerified);
    public IWhere notin(String fieldName, String[] parameterNameOrValues, boolean... isConditionVerified);

    public IWhere eq(String fieldNameSource, String fieldNameTarget);
    public IWhere eq(String fieldNameSource, IStatement innerSelectStatement,boolean... isConditionVerified);
    public IWhere eq(String sourceField, String targetField,boolean... isConditionVerified);

    public IWhere like(String fieldNameSource, String fieldNameTarget);
    public IWhere like(String sourceField, String targetField,boolean... isConditionVerified);

    public IWhere neq(String fieldNameSource, String fieldNameTarget);
    public IWhere neq(String fieldNameSource, IStatement innerSelectStatement,boolean... isConditionVerified);
    public IWhere neq(String sourceField, String targetField,boolean... isConditionVerified);

    public IWhere bracket(boolean openBracket);
    public IWhere bracket(boolean openBracket,boolean... isConditionVerified);


    public IWhere exists(IStatement innerSelectStatement, boolean... isConditionVerified);
}
