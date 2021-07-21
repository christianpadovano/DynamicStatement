package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.base.AOrderBy;
import com.dynamicstatement.builder.interf.IOrderby;

/**
 * @author christian padovano
 * @version 1.0
 */
public class OrderBy extends AOrderBy {

    /**
     * Order By statement is added by the build() method
     * This is an
     */
    OrderBy() {
        super();
    }

    @Override
    public IOrderby field(String fieldname, boolean ascendingOrder, boolean... isConditionVerified) {

        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            addField(fieldname, ascendingOrder);
        } else if (isConditionVerified.length==0) {
            addField(fieldname, ascendingOrder);
        }

        return this;
    }


    @Override
    public IOrderby field(String... fieldname) {
        for (int i = 0; i < fieldname.length;  i++) {
            field(fieldname[i],false);
        }
        return this;
    }


    @Override
    public IOrderby count(String fieldName, boolean ascendingOrder, boolean... isConditionVerified) {
        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            addField(" count(".concat(fieldName).concat(")"), ascendingOrder);
        } else if (isConditionVerified.length==0) {
            addField(" count(".concat(fieldName).concat(")"), ascendingOrder);
        }

        return this;
    }



}
