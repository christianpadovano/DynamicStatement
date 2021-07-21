package com.dynamicstatement.builder.impl.hql;

import com.dynamicstatement.builder.interf.IHaving;
import com.dynamicstatement.builder.interf.IHavingConditions;
import com.dynamicstatement.builder.operator.StandardsOperators;

public class HavingHQLConditions implements IHavingConditions {

    protected StringBuilder havingStatement;
    protected IHaving having;

    HavingHQLConditions(StringBuilder parentStatement,IHaving havingInstance) {
        this.havingStatement=parentStatement;
        this.having=havingInstance;
    }

    @Override
    public IHavingConditions eq(String target,boolean... isConditionVerified) {

        if (isConditionVerified.length == 1 && isConditionVerified[0]) {
            this.havingStatement.append(StandardsOperators.EQ.operatorName()).append(target);
        } else if (isConditionVerified.length==0) {
            this.havingStatement.append(StandardsOperators.EQ.operatorName()).append(target);
        }
        return this;
    }

    @Override
    public IHavingConditions neq(String target,boolean... isConditionVerified) {

        if (isConditionVerified.length == 1 && isConditionVerified[0]) {
            this.havingStatement.append(StandardsOperators.NOTEQ.operatorName()).append(target);
        } else if (isConditionVerified.length==0) {
            this.havingStatement.append(StandardsOperators.NOTEQ.operatorName()).append(target);
        }
        return this;
    }

    @Override
    public IHavingConditions greaterThan(String target,boolean... isConditionVerified) {

        if (isConditionVerified.length == 1 && isConditionVerified[0]) {
            this.havingStatement.append(StandardsOperators.GREATER_THAN.operatorName()).append(target);
        } else if (isConditionVerified.length==0) {
            this.havingStatement.append(StandardsOperators.GREATER_THAN.operatorName()).append(target);
        }
        return this;
    }

    @Override
    public IHavingConditions lessThan(String target,boolean... isConditionVerified) {

        if (isConditionVerified.length == 1 && isConditionVerified[0]) {
            this.havingStatement.append(StandardsOperators.LESS_THAN.operatorName()).append(target);
        } else if (isConditionVerified.length==0) {
            this.havingStatement.append(StandardsOperators.LESS_THAN.operatorName()).append(target);
        }
        return this;
    }

    @Override
    public IHaving or(boolean... isConditionVerified) {
        if (isConditionVerified.length == 1 && isConditionVerified[0]) {
            this.havingStatement.append(StandardsOperators.OR.operatorName());
        } else if (isConditionVerified.length==0) {
            this.havingStatement.append(StandardsOperators.OR.operatorName());
        }
        return having;
    }

    @Override
    public IHaving and(boolean... isConditionVerified) {
        if (isConditionVerified.length == 1 && isConditionVerified[0]) {
            this.havingStatement.append(StandardsOperators.AND.operatorName());
        } else if (isConditionVerified.length==0) {
            this.havingStatement.append(StandardsOperators.AND.operatorName());
        }
        return having;
    }
}
