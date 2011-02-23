package com.bee32.plover.expr.criterion;

import org.hibernate.criterion.Restrictions;

import com.bee32.plover.expr.AbstractExpressionVisitor;
import com.bee32.plover.expr.BinaryExpr;

public class CriterionBuilder
        extends AbstractExpressionVisitor {

    @Override
    public void visit(BinaryExpr evalExpr) {

        // if evalExpr == eq
        String propertyName = null;
        Object value = null;
        // SimpleExpression eq =
        Restrictions.eq(propertyName, value);
    }

}
