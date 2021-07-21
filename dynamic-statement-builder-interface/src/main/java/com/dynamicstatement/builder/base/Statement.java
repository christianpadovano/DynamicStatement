package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.IOrderby;
import com.dynamicstatement.builder.interf.IStatement;
import com.dynamicstatement.builder.interf.IWhere;

public class Statement implements IStatement {

    private  AWhere whereImpl;




    public Statement(AWhere statement) {
        this.whereImpl=statement;
    }



    @Override
    public String build() {
        StringBuffer statementAssembling = new StringBuffer( whereImpl.build());
        return statementAssembling.toString();
    }



}
