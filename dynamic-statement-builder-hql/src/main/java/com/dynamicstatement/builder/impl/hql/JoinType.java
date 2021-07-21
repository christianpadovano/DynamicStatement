package com.dynamicstatement.builder.impl.hql;

import com.dynamicstatement.builder.AJoinType;
import com.dynamicstatement.builder.operator.JoinTypeEnum;

public class  JoinType extends AJoinType {


    public JoinType(JoinTypeEnum joinType) {
        super(joinType);
    }

    @Override
    public String descr() {
        switch(join) {
            case LEFT:
                return " left join";
            case FETCH:
                return " join fetch";
            case INNER:
                return " inner join";
            case OUTER:
                return " outer join";
        }
        return "";
    }
}