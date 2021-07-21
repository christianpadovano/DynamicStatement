package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.IHaving;
import com.dynamicstatement.builder.operator.StandardsOperators;

public abstract class AHaving implements IHaving {

    protected StringBuilder  havingStatement;
    protected boolean addHavingCondition;

    public AHaving(boolean...isConditionVerified) {
        this.havingStatement=new StringBuilder();
        this.addHavingCondition =(isConditionVerified.length==0 ? true :
                isConditionVerified.length==1 && isConditionVerified[0]);
    }



    String build() {
        if (this.addHavingCondition && havingStatement.length()>0) {
            StringBuffer headerCommand=new StringBuffer(StandardsOperators.HAVING.
                    operatorName());
            headerCommand.append(havingStatement);
            return headerCommand.toString();
        }
        return   "";
    }



}
