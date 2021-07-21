package com.dynamicstatement.builder.impl.hql;

import com.dynamicstatement.builder.base.AHaving;
import com.dynamicstatement.builder.interf.IHavingConditions;

public class HavingHql extends AHaving {

    public HavingHql(boolean... isConditionVerified) {
        super(isConditionVerified);
    }

    @Override
    public IHavingConditions count(String fieldName, boolean...isConditionVerified) {
        String _validatedVal=(fieldName!=null && !"".equals(fieldName) ? fieldName : "*");
        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            havingStatement.append(" count(").append(fieldName).append(")");
        } else if (isConditionVerified.length==0) {
            havingStatement.append(" count(").append(fieldName).append(")");
        }
        return new HavingHQLConditions(havingStatement,this);
    }

    @Override
    public IHavingConditions max(String fieldName, boolean... isConditionVerified) {
        String _validatedVal=(fieldName!=null && !"".equals(fieldName) ? fieldName : "*");
        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            havingStatement.append(" max(").append(fieldName).append(")");
        } else if (isConditionVerified.length==0) {
            havingStatement.append(" max(").append(fieldName).append(")");
        }
        return new HavingHQLConditions(havingStatement,this);
    }

    @Override
    public IHavingConditions min(String fieldName, boolean... isConditionVerified) {
        String _validatedVal=(fieldName!=null && !"".equals(fieldName) ? fieldName : "*");
        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            havingStatement.append(" min(").append(fieldName).append(")");
        } else if (isConditionVerified.length==0) {
            havingStatement.append(" min(").append(fieldName).append(")");
        }
        return new HavingHQLConditions(havingStatement,this);
    }

    @Override
    public IHavingConditions avg(String fieldName, boolean... isConditionVerified) {
        String _validatedVal=(fieldName!=null && !"".equals(fieldName) ? fieldName : "*");
        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            havingStatement.append(" avg(").append(fieldName).append(")");
        } else if (isConditionVerified.length==0) {
            havingStatement.append(" avg(").append(fieldName).append(")");
        }
        return new HavingHQLConditions(havingStatement,this);
    }

    @Override
    public IHavingConditions sum(String fieldName, boolean... isConditionVerified) {
        String _validatedVal=(fieldName!=null && !"".equals(fieldName) ? fieldName : "*");
        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            havingStatement.append(" sum(").append(fieldName).append(")");
        } else if (isConditionVerified.length==0) {
            havingStatement.append(" sum(").append(fieldName).append(")");
        }
        return new HavingHQLConditions(havingStatement,this);
    }
}
