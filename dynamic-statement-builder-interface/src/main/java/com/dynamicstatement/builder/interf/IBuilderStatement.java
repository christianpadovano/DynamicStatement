package com.dynamicstatement.builder.interf;

import com.dynamicstatement.builder.base.AFrom;
import com.dynamicstatement.builder.base.ASelect;

public interface IBuilderStatement {

    public ASelect select();

    public AFrom from();

    public IWhere where();

    public IOrderby orderby();

    public IGroupBy groupBy();

    public String build();

    public IStatement getStatement() throws IllegalAccessException;


}
