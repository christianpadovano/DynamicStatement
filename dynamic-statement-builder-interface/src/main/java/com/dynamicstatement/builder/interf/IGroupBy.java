package com.dynamicstatement.builder.interf;

public interface IGroupBy {

    public IGroupBy field(String fieldname,  boolean... isConditionVerified);
    public IHaving having( boolean... isConditionVerified);
}
