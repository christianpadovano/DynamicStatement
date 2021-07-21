package com.dynamicstatement.builder.operator;

import java.util.Arrays;

public enum StandardsOperators {

    FROM(" from "),
    WHERE (" where "),
    AND(" and "),
    OR(" or "),
    NOTEQ(" != "),
    EQ("= "),
    BETWEEN(" BETWEEN  "),
    NOT_IN(" not in  "),
    IN(" in  "),
    IS_NULL(" is null "),
    IS_NOT_NULL(" is not null "),
    GREATER_THAN(" > "),
    GREATER_EQ_THAN(" >= "),
    LESS_THAN(" < "),
    LESS_EQ_THAN(" <= "),
    LIKE( " like " ),
    EXISTS(" exists "),

    ORDER_BY(" order by "),
    GROUP_BY(" group by "),
    HAVING(" having ");

    private final String operatorName;

    StandardsOperators(String op) {
        this.operatorName=op;
    }

    public String operatorName() {
        return operatorName;
    }

    public static String trimUselessLogicOperator(String whereStatement) {
        String[] dummy=whereStatement.toLowerCase().split(" ");
        if (dummy[dummy.length-1].trim().startsWith(AND.operatorName.trim()) ||
            dummy[dummy.length-1].trim().startsWith(OR.operatorName.trim()))
        {
            dummy[dummy.length-1]="";
            StringBuffer filteredWhereConditions=new StringBuffer();
            for (String statement : dummy) {
                filteredWhereConditions.append(statement).append(" ");
            }
            return filteredWhereConditions.toString();
        }
        return whereStatement;

    }




}
