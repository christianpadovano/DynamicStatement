package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.base.AHaving;
import com.dynamicstatement.builder.interf.IHavingConditions;

public class Having extends AHaving {

    public Having(boolean... isConditionVerified) {
        super(isConditionVerified);
    }

    @Override
    public IHavingConditions count(String fieldName, boolean...isConditionVerified) {
        return createHavingConditions(fieldName, isConditionVerified, " count");
    }

    @Override
    public IHavingConditions max(String fieldName, boolean... isConditionVerified) {
        return createHavingConditions(fieldName, isConditionVerified, " max");
    }

    @Override
    public IHavingConditions min(String fieldName, boolean... isConditionVerified) {
        return createHavingConditions(fieldName, isConditionVerified, " min");
    }

    @Override
    public IHavingConditions avg(String fieldName, boolean... isConditionVerified) {
        return createHavingConditions(fieldName, isConditionVerified, " avg");
    }

    @Override
    public IHavingConditions sum(String fieldName, boolean... isConditionVerified) {
        return createHavingConditions(fieldName, isConditionVerified, " sum");
    }

    private IHavingConditions createHavingConditions(String fieldName, boolean[] isConditionVerified, String command) {
        String _validatedVal = (fieldName != null && !"".equals(fieldName) ? fieldName : "*");
        if (isConditionVerified.length == 1 && isConditionVerified[0]) {
            havingStatement.append(command).append("(").append(fieldName).append(")");
        } else if (isConditionVerified.length == 0) {
            havingStatement.append(command).append("(").append(fieldName).append(")");
        }
        return new HavingConditions(havingStatement, this);
    }
}
