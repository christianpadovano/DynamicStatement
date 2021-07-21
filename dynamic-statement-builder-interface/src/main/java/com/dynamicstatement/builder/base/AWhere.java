package com.dynamicstatement.builder.base;

import com.dynamicstatement.builder.interf.IBuilderStatement;
import com.dynamicstatement.builder.interf.IWhere;
import com.dynamicstatement.builder.operator.StandardsOperators;

import java.util.*;

/**
 * Base for the statement building
 */
public abstract class AWhere implements IWhere {

    protected List<StatementMetadata> whereStatement;
    protected AFrom fromStatementImpl;


    /* last command in the buffer */
    protected StatementMetadata lastStatement;

    protected int totStatementNumber=0;


    //if setted to true it means that the last command has not been added to the statement
    protected Boolean lastCommandConditionVerified;

    //if true the statement must be wrapped by "()"
    protected boolean openBracket;
    protected boolean closeBracket;


    public AWhere(AFrom fromStatement) {
        this.whereStatement=new ArrayList<>();
        this.fromStatementImpl = fromStatement;
        this.openBracket=closeBracket=false;
    }

    /**
     * add a statement to the queue
     * @param fieldname
     * @param operator
     * @param isConditionVerified
     */
    protected void addStatement(String fieldName, String operator, String value , boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if (isConditionVerified.length > 0 && isConditionVerified[0] ) {
            assembleCondition(operator, fieldName!=null ? fieldName.trim() : null,value);
        } else if (isConditionVerified.length==0) {
            //handle one or more conditions with the same field Name
            assembleCondition(operator, fieldName!=null ? fieldName.trim() : null,value);
        }
    }

    protected void addStatement(String fieldName, String operator , boolean... isConditionVerified) {
        this.addStatement(fieldName,operator,null,isConditionVerified);
    }

    private void assembleCondition(String operator, String fieldName, String value) {
        this.lastStatement= new StatementMetadata(fieldName,operator, value);
        whereStatement.add(this.lastStatement);
        totStatementNumber++;
    }



    public IWhere bracket(boolean openBracket) {
        lastStatement=new StatementMetadata(null,openBracket ? "(" : ")",null);
        whereStatement.add(lastStatement);
        return this;
    }


    public IWhere bracket(boolean openBracket, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            bracket(openBracket);
        }else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    /**
     * build the WHERE statement and clear the internal queue
     * @return
     */
    String build() {
        StringBuffer whereStatementAssembler = new StringBuffer();
        if (!whereStatement.isEmpty()) {
            whereStatementAssembler.append(StandardsOperators.WHERE.operatorName());
            for (StatementMetadata condition : whereStatement) {

                if (condition.getFieldName() != null && !condition.getFieldName().startsWith(" ")) {
                    whereStatementAssembler.append(condition.getFieldName());
                }


                String op = condition.getOperator();
                if (op != null) {
                    whereStatementAssembler.append(op);
                }

                String value = condition.getValue();
                if (value != null) {
                    whereStatementAssembler.append(value);
                }
            }
        }
        //whereStatement.clear();
        //Where statement filter. Trim the last useless operator avoiding syntax error
        String filteredStream = StandardsOperators.trimUselessLogicOperator(whereStatementAssembler.toString());
        return new StringBuffer(fromStatementImpl.build()).
                       append(filteredStream).toString();

    }


}
