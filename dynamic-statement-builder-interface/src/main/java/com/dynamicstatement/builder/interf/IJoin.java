package com.dynamicstatement.builder.interf;

import com.dynamicstatement.builder.base.AWhere;

public interface IJoin {


    /**
     * return the SQL join type
     * @return
     */
    public String descr();

    IJoin conditions(String sqlStatement,boolean... isConditionVerified);

    public IJoin and(boolean... isConditionVerified);
    public IJoin or(boolean... isConditionVerified);


}
