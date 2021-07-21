package com.dynamicstatement.builder.impl.hql;

import com.dynamicstatement.builder.base.ASelect;
import com.dynamicstatement.builder.interf.IFrom;
import com.dynamicstatement.builder.interf.ISelect;

/**
 * Implements the field stament behaoviour
 * @author c.padovano
 */
public class SelectStatement  extends ASelect {


    SelectStatement() {
        super();
    }

    @Override
    public ISelect field(String fieldName) {
        this.fieldName.add(fieldName);
        return this;
    }

    @Override
    public ISelect field(String fieldName, Boolean condition) {
        if (condition) {
            this.fieldName.add(fieldName);
        }
        return this;
    }

    @Override
    public ISelect field(String fieldName, String aliasName, Boolean condition) {
        if (condition) {
            this.fieldName.add(fieldName +" AS " + aliasName);
        }
        return this;
    }

    @Override
    public ISelect field(String fieldName, String aliasName) {
        this.fieldName.add(fieldName +" AS " + aliasName);
        return this;
    }

    @Override
    public ISelect allFields() {
        return this;
    }

    @Override
    public ISelect count(String fieldName,boolean... isConditionVerified) {
        if (isConditionVerified.length==1 && isConditionVerified[0]) {
            this.fieldName.add(" count("+fieldName+") ");
        } else if (isConditionVerified.length==0) {
            this.fieldName.add(" count("+fieldName+") ");
        }
        return this;
    }




}
