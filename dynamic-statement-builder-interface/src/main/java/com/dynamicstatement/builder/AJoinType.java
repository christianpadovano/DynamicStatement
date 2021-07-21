package com.dynamicstatement.builder;

import com.dynamicstatement.builder.operator.JoinTypeEnum;

public abstract class  AJoinType {

    protected JoinTypeEnum join;

    public AJoinType(JoinTypeEnum joinType) {
        this.join=joinType;
    }

    public abstract String descr();
}
