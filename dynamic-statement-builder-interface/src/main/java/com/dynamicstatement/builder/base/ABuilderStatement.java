package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.*;

/**
 * Abstract Builder
 * @author c.padovano
 * @version 1.0
 */
public abstract class ABuilderStatement implements IBuilderStatement {


    protected IStatement statementImpl;
    protected ASelect selectImpl;
    protected AWhere whereImpl;
    protected AFrom fromImpl;
    protected AOrderBy orderbyImpl;
    protected AGroupBy groupBy;



    @Override
    public IStatement getStatement() throws IllegalAccessException {
        if (statementImpl==null) {
            throw new IllegalAccessException("ERROR! Invoke the build method first and then use getStatement() ");
        }
        return statementImpl;
    }

    public String build() {

        //this.fromImpl.setSelectStatement(this.selectImpl);
        //this.whereImpl.setFromStatement( this.fromImpl);
       this.statementImpl = new Statement(whereImpl);

       StringBuffer statementAssembling = new StringBuffer( statementImpl.build());

        if (this.groupBy!=null) {
            statementAssembling.append(this.groupBy.build());
        }
        if (this.orderbyImpl!=null) {
            statementAssembling.append(this.orderbyImpl.build());
        }
        return statementAssembling.toString();
    }

}
