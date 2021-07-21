package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.IOrderby;
import com.dynamicstatement.builder.operator.StandardsOperators;

/**
 * @author christian padovano
 * @version 1.0
 */
public abstract class AOrderBy implements IOrderby {

    protected StringBuffer  orderByStatement;

    public AOrderBy() {
        this.orderByStatement=new StringBuffer();
    }


    protected void addField(String fieldname, boolean ascendingOrder) {
        if ("".equals(orderByStatement.toString()) ) {
            orderByStatement.append(fieldname);
        } else {
            orderByStatement.append(", ").append(fieldname);
        }

        //if false is a descending order
        if (!ascendingOrder) {
            orderByStatement.append(" desc");
        }
    }



    String build() {
        if (orderByStatement.length()>0) {
            StringBuffer headerCommand=new StringBuffer(StandardsOperators.ORDER_BY.operatorName());
            headerCommand.append(orderByStatement);
            return headerCommand.toString();
        }
        return   orderByStatement.toString();
    }
}
