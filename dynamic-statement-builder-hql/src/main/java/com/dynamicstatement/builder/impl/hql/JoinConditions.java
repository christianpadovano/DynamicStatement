package com.dynamicstatement.builder.impl.hql;

import com.dynamicstatement.builder.interf.IJoin;

/**
 * HQL join conditions  implementation
 */
public class JoinConditions implements IJoin {

    private JoinType join;

    public JoinConditions(JoinType joinType) {
       this.join=joinType;
    }


    @Override
    public String descr() {
        return this.join.descr();
    }

    @Override
    public IJoin conditions(String sqlStatement, boolean... isConditionVerified) {
        return null;
    }

    @Override
    public IJoin and(boolean... isConditionVerified) {
        return null;
    }

    @Override
    public IJoin or(boolean... isConditionVerified) {
        return null;
    }
}
