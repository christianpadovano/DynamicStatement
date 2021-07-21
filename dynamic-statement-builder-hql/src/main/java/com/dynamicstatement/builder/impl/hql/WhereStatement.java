package com.dynamicstatement.builder.impl.hql;

import com.dynamicstatement.builder.base.AFrom;
import com.dynamicstatement.builder.base.AWhere;
import com.dynamicstatement.builder.base.StatementMetadata;
import com.dynamicstatement.builder.interf.IStatement;
import com.dynamicstatement.builder.interf.IWhere;
import com.dynamicstatement.builder.operator.StandardsOperators;

/**
 * Implements the where comamnd for the HQL/JPL language
 * @author christian padovano
 * @version 1.0
 */
public class WhereStatement extends AWhere {

    /* last command in the buffer */
    private StatementMetadata lastStatement;

    private int totStatementNumber=0;


    //if setted to true it means that the last command ahs not been added to the statement
    private Boolean lastCommandConditionVerified;


    WhereStatement(AFrom fromStatement) {
       super(fromStatement);
    }




    @Override
    public IWhere and(String freecondition, boolean... isConditionVerified) {
        addStatement(freecondition,StandardsOperators.AND.operatorName(),isConditionVerified);
        return this;
    }



    @Override
    public IWhere or(String freecondition, boolean... isConditionVerified) {
        addStatement(freecondition,StandardsOperators.OR.operatorName(),isConditionVerified);
        return this;
    }

    @Override
    public IWhere and() {
        if ( lastCommandConditionVerified!=null && lastCommandConditionVerified ) {
            addLogicOperator(StandardsOperators.AND.operatorName());
        } else if (lastCommandConditionVerified==null) {
            addLogicOperator(StandardsOperators.AND.operatorName());
        }
        return this;
    }



    @Override
    public IWhere or() {
        if ( lastCommandConditionVerified!=null && lastCommandConditionVerified ) {
            addLogicOperator(StandardsOperators.OR.operatorName());
        } else if (lastCommandConditionVerified==null) {
            addLogicOperator(StandardsOperators.OR.operatorName());
        }
        return this;
    }

    @Override
    public IWhere greater(String sourceField, String targetField, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        StringBuffer buf=new StringBuffer(sourceField);
        addStatement(buf.append(StandardsOperators.GREATER_THAN.operatorName()).
                         append(targetField).toString(),
                null,
                isConditionVerified);
        return this;
    }

    @Override
    public IWhere lessthan(String sourceField, String targetField, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        StringBuffer buf=new StringBuffer(sourceField);
        addStatement(buf.append("<").append(targetField).toString(),null,isConditionVerified);
        return this;
    }

    @Override
    public IWhere greaterOrEq(String sourceField, String targetField, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        StringBuffer buf=new StringBuffer(sourceField);
        addStatement(buf.append(">=").append(targetField).toString(),null,isConditionVerified);
        return this;
    }

    @Override
    public IWhere lessthanOrEq(String sourceField, String targetField, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        StringBuffer buf=new StringBuffer(sourceField);
        addStatement(buf.append("<=").append(targetField).toString(),null,isConditionVerified);
        return this;
    }

    @Override
    public IWhere between(String fieldName, String parameterNameFrom, String parameterNameTo, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        StringBuffer statement=new StringBuffer(fieldName);
        statement.append(StandardsOperators.BETWEEN.operatorName()).
                  append(parameterNameFrom).
                  append(StandardsOperators.AND.operatorName()).
                  append(parameterNameTo);
        addStatement(statement.toString(),null,isConditionVerified);
        return this;
    }

    @Override
    public IWhere isNull(String fieldName, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        addStatement(fieldName,StandardsOperators.IS_NULL.operatorName(),isConditionVerified);
        return this;
    }

    @Override
    public IWhere isNotNull(String fieldName, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        addStatement(fieldName,StandardsOperators.IS_NOT_NULL.operatorName(),isConditionVerified);
        return this;
    }

    @Override
    public IWhere in(String fieldName, IStatement innerSelectStatement, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            StringBuffer statement = new StringBuffer(StandardsOperators.IN.operatorName() + "(");
            addStatement(fieldName,
                    statement.append(innerSelectStatement.build()).append(")").toString(),
                    isConditionVerified);
        }else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere in(String fieldName, String[] parameterNameOrValues, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            //String.join(",",parameterNameOrValues)
            StringBuffer statement = new StringBuffer(StandardsOperators.IN.operatorName() + "(");
            addStatement(fieldName,
                    statement.append(String.join(",",parameterNameOrValues)).append(")").toString(),
                    isConditionVerified);
        } else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere notin(String fieldName, IStatement innerSelectStatement, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            StringBuffer statement = new StringBuffer("not in (");
            addStatement(fieldName,
                    statement.append(innerSelectStatement.build()).append(")").toString(),
                    isConditionVerified);
        } else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere notin(String fieldName, String[] parameterNameOrValues, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            StringBuffer statement = new StringBuffer("not in (");
            addStatement(fieldName,
                    statement.append(parameterNameOrValues).append(")").toString(),
                    isConditionVerified);
        } else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere eq(String fieldNameSource, String fieldNameTarget) {
        if ( lastCommandConditionVerified!=null && lastCommandConditionVerified ) {
            addStatement(fieldNameSource,StandardsOperators.EQ.operatorName()+fieldNameTarget);
        } else if (lastCommandConditionVerified==null) {
            addStatement(fieldNameSource,StandardsOperators.EQ.operatorName()+fieldNameTarget);
        }
        return this;
    }

    @Override
    public IWhere eq(String fieldNameSource, IStatement innerSelectStatement,boolean... isConditionVerified) {
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            StringBuilder statement = new StringBuilder(StandardsOperators.EQ.operatorName());
            addStatement(fieldNameSource,
                    statement.append("(").append(innerSelectStatement.build()).append(")").toString(),
                    isConditionVerified);
        }else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere eq(String sourceField, String targetField, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            addStatement(sourceField,StandardsOperators.EQ.operatorName()+targetField,isConditionVerified);
        } else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere neq(String fieldNameSource, String fieldNameTarget) {
        if ( lastCommandConditionVerified!=null && lastCommandConditionVerified ) {
            addStatement(fieldNameSource,StandardsOperators.NOTEQ.operatorName(),fieldNameTarget);
        } else if (lastCommandConditionVerified==null) {
            addStatement(fieldNameSource,StandardsOperators.NOTEQ.operatorName(),fieldNameTarget);
        }

        return this;
    }

    @Override
    public IWhere neq(String fieldNameSource, IStatement innerSelectStatement, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            StringBuffer statement = new StringBuffer(StandardsOperators.NOTEQ.operatorName());
            addStatement(fieldNameSource,
                    statement.append("(").append(innerSelectStatement.build()).append(")").toString(),
                    isConditionVerified);
        }else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere neq(String sourceField, String targetField, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            addStatement(sourceField,StandardsOperators.NOTEQ.operatorName(),targetField,isConditionVerified);
        } else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }

    @Override
    public IWhere exists(IStatement innerSelectStatement, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            addStatement(null,StandardsOperators.EXISTS.operatorName(),"(".concat(innerSelectStatement.build()).concat(")"),isConditionVerified);
        }else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }


    @Override
    public IWhere like(String fieldNameSource, String fieldNameTarget) {
        if ( lastCommandConditionVerified!=null && lastCommandConditionVerified ) {
            addStatement(fieldNameSource,StandardsOperators.LIKE.operatorName(),fieldNameTarget);
        } else if (lastCommandConditionVerified==null) {
            addStatement(fieldNameSource,StandardsOperators.LIKE.operatorName(),fieldNameTarget);
        }
        return this;
    }


    @Override
    public IWhere like(String sourceField, String targetField, boolean... isConditionVerified) {
        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
        if ((isConditionVerified.length==1 && isConditionVerified[0]) || isConditionVerified.length==0) {
            addStatement(sourceField,StandardsOperators.LIKE.operatorName(),targetField,isConditionVerified);
        } else if (isConditionVerified.length>1) {
            throw new IllegalArgumentException("Too many conditions given to the function! Only one boolean condition can be evaluted ");
        }
        return this;
    }




//    private void addStatement(String freecondition, String operator, boolean... isConditionVerified) {
//        lastCommandConditionVerified=isConditionVerified.length==1 ? Boolean.valueOf(isConditionVerified.length==1 && isConditionVerified[0]) : null;
//        if (isConditionVerified.length > 0 && isConditionVerified[0]) {
//            whereStatement.put(freecondition.trim(), operator);
//            this.lastStatement=freecondition.trim();
//            totStatementNumber++;
//        } else if (isConditionVerified.length==0) {
//            whereStatement.put(freecondition.trim(), operator);
//            this.lastStatement=freecondition.trim();
//            totStatementNumber++;
//        }
//    }


    private void addLogicOperator(String logicOperator) {
        lastStatement=new StatementMetadata(null,logicOperator,null);
        whereStatement.add(lastStatement);
    }
}
