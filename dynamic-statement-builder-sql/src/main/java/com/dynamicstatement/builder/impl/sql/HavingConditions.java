package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.interf.IHaving;
import com.dynamicstatement.builder.interf.IHavingConditions;
import com.dynamicstatement.builder.operator.StandardsOperators;

public class HavingConditions implements IHavingConditions {

    protected StringBuilder havingStatement;
    protected IHaving having;

    HavingConditions(StringBuilder parentStatement,IHaving havingInstance) {
        this.havingStatement=parentStatement;
        this.having=havingInstance;
    }

    @Override
    public IHavingConditions eq(String target,boolean... isConditionVerified) {
        this.havingStatement.append(StandardsOperators.EQ.operatorName()).append(target);
        return this;
    }

    @Override
    public IHavingConditions neq(String target,boolean... isConditionVerified) {
         return createHavingConditions(target, StandardsOperators.NOTEQ, isConditionVerified);
   }

    @Override
    public IHavingConditions greaterThan(String target,boolean... isConditionVerified) {
         return createHavingConditions(target, StandardsOperators.GREATER_THAN, isConditionVerified);
    }

    @Override
    public IHavingConditions lessThan(String target,boolean... isConditionVerified) {
        return createHavingConditions(target, StandardsOperators.LESS_THAN, isConditionVerified);
    }



    @Override
    public IHaving or( boolean... isConditionVerified) {
        if (isConditionVerified.length==0 || (isConditionVerified.length == 1 && isConditionVerified[0])) {
            this.havingStatement.append(StandardsOperators.OR.operatorName());
        }
        return having;
    }

    @Override
    public IHaving and(boolean... isConditionVerified) {
        if (isConditionVerified.length==0 || (isConditionVerified.length == 1 && isConditionVerified[0])) {
            this.havingStatement.append(StandardsOperators.AND.operatorName());
        }
        return having;
    }


    private HavingConditions createHavingConditions(Object target, StandardsOperators operator, boolean[] isConditionVerified) {
        if (isConditionVerified.length==0 || (isConditionVerified.length == 1 && isConditionVerified[0])) {
            this.havingStatement.append(operator.operatorName()).append(target);
        }
        return this;
    }

}
