package com.dynamicstatement.builder.interf;

public interface IHavingConditions {

    public IHavingConditions eq(String target,boolean... isConditionVerified);
    public IHavingConditions neq(String target,boolean... isConditionVerified);
    public IHavingConditions greaterThan(String target,boolean... isConditionVerified);
    public IHavingConditions lessThan(String target,boolean... isConditionVerified);

    public IHaving or(boolean... isConditionVerified);
    public IHaving and(boolean... isConditionVerified);
}
