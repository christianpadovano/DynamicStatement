package com.dynamicstatement.builder.interf;

public interface IOrderby {

    public IOrderby field(String fieldname, boolean ascendingOrder, boolean... isConditionVerified);
    public IOrderby field(String... fieldname);
    public IOrderby count(String fieldName,boolean ascendingOrder,boolean... isConditionVerified);



}
