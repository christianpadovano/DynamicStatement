package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.IFrom;
import com.dynamicstatement.builder.operator.StandardsOperators;

public abstract class AFrom implements IFrom {


    protected StringBuffer fromStatement;
    protected ASelect selectStatement;



    public AFrom() {
        fromStatement=new StringBuffer(StandardsOperators.FROM.operatorName());
    }


    public AFrom(ASelect selectImplementation) {
        this();
        selectStatement=selectImplementation;
    }



    String build() {
        return  new StringBuffer(selectStatement.build()).append(this.fromStatement).toString();
    }
}
