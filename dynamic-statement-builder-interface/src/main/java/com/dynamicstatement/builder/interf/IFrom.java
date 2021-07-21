package com.dynamicstatement.builder.interf;

public interface IFrom   {

    public IFrom table(Class entityClass, String aliasName);

    public IFrom table(Class entityClass, String aliasName, boolean evaluateCondition);

    public IFrom table(String entityName, String aliasName);

    public IFrom table(String entityName, String aliasName, boolean evaluateCondition);

    public IFrom table(IJoin joinCommand, String entityName);

    public IFrom table(IJoin joinCommand, Class entityClass);

    public IFrom table(IJoin joinCommand, String entityName, boolean evaluateCondition);

    public IFrom table(IJoin joinCommand, Class entityClass, boolean evaluateCondition);

    public IFrom table(IJoin joinCommand , String entityName, String alias );

    public IFrom table(IJoin joinCommand , Class entityClass, String alias );

    public IFrom table(IJoin joinCommand , String entityName, String alias , boolean evaluateCondition);

    public IFrom table(IJoin joinCommand , Class entityClass, String alias , boolean evaluateCondition);








}
