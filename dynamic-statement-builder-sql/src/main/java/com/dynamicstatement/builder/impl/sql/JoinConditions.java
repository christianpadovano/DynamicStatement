package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.AJoinType;
import com.dynamicstatement.builder.interf.IJoin;
import com.dynamicstatement.builder.operator.JoinTypeEnum;

import java.util.Map;


/**
 * join conditions SQL implenentation
 */
public class JoinConditions implements IJoin {

    private static final String AND = " AND ";
    private static final String OR = " OR  ";

    protected StringBuffer joinConditions;
    private AJoinType joinType;
    protected Map<String,String> whereStatement;

    //if setted to true it means that the last command ahs not been added to the statement
    private Boolean lastCommandConditionVerified;

    /* last command in the buffer */
    private String lastStatement;



    public JoinConditions(JoinTypeEnum joinType) {
        joinConditions=new StringBuffer();
        this.joinType=new JoinType(joinType);
    }




    @Override
    public String descr() {
        return this.joinType.descr();
    }

    @Override
    public IJoin conditions(String sqlStatement, boolean... isConditionVerified) {
        if (isConditionVerified.length>0 && isConditionVerified[0]) {
            this.joinConditions.append(sqlStatement.trim());
        } else if (isConditionVerified.length==0) {
            this.joinConditions.append(sqlStatement.trim());
        }
        return this;
    }


    @Override
    public IJoin and(boolean... isConditionVerified) {
        if (isConditionVerified.length>0 && isConditionVerified[0]) {
            this.joinConditions.append(AND);
        } else if (isConditionVerified.length==0) {
            this.joinConditions.append(AND);
        }
        return this;
    }

    @Override
    public IJoin or(boolean... isConditionVerified) {
        if (isConditionVerified.length>0 && isConditionVerified[0]) {
            this.joinConditions.append(OR);
        } else if (isConditionVerified.length==0) {
            this.joinConditions.append(OR);
        }
        return this;
    }


    @Override
    public String toString() {
        String joinConditionFilterFromUnusedOperator=joinConditions.toString();
        //looking for orphan statement that can create syntax error
        if (joinConditionFilterFromUnusedOperator.endsWith(AND) ||
            joinConditionFilterFromUnusedOperator.endsWith(OR)) {
            //clean the orphan statement
            joinConditionFilterFromUnusedOperator =
                    joinConditionFilterFromUnusedOperator.substring(joinConditionFilterFromUnusedOperator.length()-AND.length(),
                                                                    joinConditionFilterFromUnusedOperator.length());
        }
        return new StringBuffer().append(" ON (").
                append(joinConditionFilterFromUnusedOperator).
                append(")").
                toString();
    }



}
