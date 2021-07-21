package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.AJoinType;
import com.dynamicstatement.builder.operator.JoinTypeEnum;

/**
 *  SQL-ANSI join command implementation
 */
public class JoinType extends AJoinType {


    public JoinType(JoinTypeEnum joinType) {
        super(joinType);
    }

    @Override
    public String descr() {
        switch(join) {
            case LEFT:
                return " left join";

            case FETCH:
                throw new IllegalArgumentException("Not supported in SQL");

            case INNER:
                return " join";

            case OUTER:
                return " outer join";
        }
        return null;
    }
}
