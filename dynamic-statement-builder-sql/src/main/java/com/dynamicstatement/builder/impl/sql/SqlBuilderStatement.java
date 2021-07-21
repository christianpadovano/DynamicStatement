package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.base.ABuilderStatement;
import com.dynamicstatement.builder.base.AFrom;
import com.dynamicstatement.builder.base.ASelect;
import com.dynamicstatement.builder.interf.IGroupBy;
import com.dynamicstatement.builder.interf.IOrderby;
import com.dynamicstatement.builder.interf.IWhere;

/**
 * Concrete HQL Builder implementation
 * @author c.padovano
 * @version 1.0
 */
public class SqlBuilderStatement extends ABuilderStatement {





    private SqlBuilderStatement() {
        this.selectImpl=new SelectStatement();
        this.fromImpl=new FromStatetement(this.selectImpl);
        this.whereImpl=new WhereStatement(this.fromImpl);
        this.orderbyImpl=new OrderBy();
        this.groupBy=new GroupBy();
    }

    public static ABuilderStatement create() {
        return new SqlBuilderStatement();
    }


    @Override
    public ASelect select() {

        return this.selectImpl;
    }

    @Override
    public AFrom from() {

        return this.fromImpl;
    }

    @Override
    public IWhere where() {

        return  this.whereImpl;
    }

    @Override
    public IOrderby orderby() {

        return this.orderbyImpl;
    }

    @Override
    public IGroupBy groupBy() {

        return this.groupBy;
    }
}
