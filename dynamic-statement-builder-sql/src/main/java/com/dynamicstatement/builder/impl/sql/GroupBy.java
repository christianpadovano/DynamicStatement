package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.base.AGroupBy;
import com.dynamicstatement.builder.interf.IGroupBy;
import com.dynamicstatement.builder.interf.IHaving;

/**
 * Group by builder statement
 * @author c.padovano
 */
public class GroupBy extends AGroupBy {

    GroupBy() {
        super();
    }


    @Override
    public IGroupBy field(String fieldname, boolean... isConditionVerified) {

        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            addField(fieldname);
        } else if (isConditionVerified.length==0) {
            addField(fieldname);
        }

        return this;
    }

    @Override
    public IHaving having(boolean... isConditionVerified) {
        having = new Having(isConditionVerified);
        return having;
    }


    private void addField(String fieldname) {
        if ("".equals(groupByStatement.toString()) ) {
            groupByStatement.append(fieldname);
        } else {
            groupByStatement.append(", ").append(fieldname);
        }


    }


}
