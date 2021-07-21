package com.dynamicstatement.builder.interf;

public interface IHaving {

    public IHavingConditions count(String fieldName,boolean...isConditionVerified);

    public IHavingConditions max(String fieldName,boolean...isConditionVerified);

    public IHavingConditions min(String fieldName,boolean...isConditionVerified);

    public IHavingConditions avg(String fieldName,boolean...isConditionVerified);

    public IHavingConditions sum(String fieldName,boolean...isConditionVerified);

}
