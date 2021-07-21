package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.IGroupBy;
import com.dynamicstatement.builder.interf.IHaving;
import com.dynamicstatement.builder.operator.StandardsOperators;

public abstract class AGroupBy implements IGroupBy {

    protected StringBuilder groupByStatement;
    protected AHaving having;

    public AGroupBy() {
        this.groupByStatement=new StringBuilder();
    }


    String build() {
        if (groupByStatement.length()>0) {
            StringBuilder headerCommand=new StringBuilder(StandardsOperators.GROUP_BY.
                    operatorName());
            headerCommand.append(groupByStatement);

            if (having!=null) {
                headerCommand.append(this.having.build());
            }

            return headerCommand.toString();
        }
        return   groupByStatement.toString();
    }
}
