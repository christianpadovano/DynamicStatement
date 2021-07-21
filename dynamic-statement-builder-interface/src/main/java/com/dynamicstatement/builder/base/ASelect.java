package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.IFrom;
import com.dynamicstatement.builder.interf.ISelect;

import java.util.ArrayList;
import java.util.List;

public abstract class ASelect implements ISelect {

    protected List<String> fieldName = new ArrayList<>();
    protected boolean isDistinctSelect;
    protected AFrom fromStatementImpl;

    public ASelect() {
        fieldName=new ArrayList<>();
        this.isDistinctSelect=false;
    }

    @Override
    public ISelect distinct(boolean distinctOnFields) {
        this.isDistinctSelect=distinctOnFields;
        return this;
    }


    /**
     * Friendly method must be used only from the custom command of the package
     * @return the final Select statement assembled
     */
    String build() {

        StringBuffer assembledSelectStatement = new StringBuffer(fieldName.isEmpty() ? "" : "select ");
        if (!fieldName.isEmpty() && this.isDistinctSelect) {
            assembledSelectStatement.append(" distinct ");
        }
        int totFields=fieldName.size();
        for (int f=0; f <= totFields-1; f++) {
            if (f<(totFields-1)) {
                assembledSelectStatement.append(fieldName.get(f)).append(",");
            } else {
                assembledSelectStatement.append(fieldName.get(f));
            }
        }
        return assembledSelectStatement.toString();
    }

}
