package com.dynamicstatement.builder.interf;

public interface ISelect {

    public ISelect field(String fieldName);
    public ISelect field(String fieldName, Boolean condition);
    public ISelect field(String fieldName, String aliasName, Boolean condition);
    public ISelect field(String fieldName, String aliasName);
    public ISelect allFields();
    public ISelect distinct(boolean distinctOnFields);
    public ISelect count(String fieldName,boolean... isConditionVerified);

}
