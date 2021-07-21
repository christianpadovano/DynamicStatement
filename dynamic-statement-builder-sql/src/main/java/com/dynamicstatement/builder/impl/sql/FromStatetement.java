package com.dynamicstatement.builder.impl.sql;

import com.dynamicstatement.builder.base.AFrom;
import com.dynamicstatement.builder.base.ASelect;
import com.dynamicstatement.builder.interf.IFrom;
import com.dynamicstatement.builder.interf.IJoin;
import com.dynamicstatement.builder.operator.StandardsOperators;

public class FromStatetement extends AFrom {


    FromStatetement() {
       super();
    }


    FromStatetement(ASelect selectImplementation) {
       super(selectImplementation);
    }


    @Override
    public IFrom table(Class entityClass, String aliasName) {
        throw new IllegalArgumentException("Method not supported");
    }

    @Override
    public IFrom table(Class entityClass, String aliasName,boolean evaluateCondition) {
        throw new IllegalArgumentException("Method not supported");
    }

    @Override
    public IFrom table(String entityName, String aliasName) {
        if ( !StandardsOperators.FROM.operatorName().equals(fromStatement.toString()) &&
             !fromStatement.toString().endsWith(",") ) {
            fromStatement.append(",");
        }
        fromStatement.append(entityName.trim());
        if (aliasName!=null && !"".equals(aliasName))
            fromStatement.append(" "). append(aliasName);
        return this;
    }

    @Override
    public IFrom table(IJoin joinCommand, String entityName, String alias) {
        fromStatement.append( joinCommand.descr() ).append(" ");
        fromStatement.append( entityName.trim() ).append(" ").append(alias);
        fromStatement.append( joinCommand.toString() );
        return this;
    }

    @Override
    public IFrom table(IJoin joinCommand, Class entityClass, String alias) {
        throw new IllegalArgumentException("Method not supported");
    }

    @Override
    public IFrom table(IJoin joinCommand, String entityName) {
        fromStatement.append( joinCommand.descr() ).append(" ");
        fromStatement.append( entityName.trim() );
        return this;
    }

    @Override
    public IFrom table(IJoin joinCommand, Class entityClass) {
        throw new IllegalArgumentException("Method not supported");
    }

    @Override
    public IFrom table(IJoin joinCommand, String entityName, boolean evaluateCondition) {
        if (evaluateCondition) {
            table(joinCommand,entityName.trim());
        }
        return this;
    }

    @Override
    public IFrom table(IJoin joinCommand, Class entityClass, boolean evaluateCondition) {
        return this.table(joinCommand,entityClass.getName(),evaluateCondition);
    }

    @Override
    public IFrom table(String entityName, String aliasName, boolean evaluateCondition) {
        if (evaluateCondition) {
            table(entityName.trim(),aliasName);
        }
        return this;
    }

    @Override
    public IFrom table(IJoin joinCommand, String entityName, String alias, boolean evaluateCondition) {
        if (evaluateCondition) {
            table(joinCommand,entityName.trim(),alias);
        }
        return this;
    }

    @Override
    public IFrom table(IJoin joinCommand, Class entityClass, String alias, boolean evaluateCondition) {
        return this.table(joinCommand,entityClass.getName(),alias,evaluateCondition);
    }

}
