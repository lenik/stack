package com.bee32.plover.criteria.hibernate;

import javax.free.UnexpectedException;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

public abstract class Property2CriteriaElement
        extends SpelCriteriaElement {

    private static final long serialVersionUID = 1L;

    final String propertyName;
    final String otherPropertyName;

    public Property2CriteriaElement(String propertyName, String otherPropertyName) {
        this.propertyName = propertyName;
        this.otherPropertyName = otherPropertyName;
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        if (context == null)
            context = getDefaultContext(obj);
        initContext(context, obj);

        Expression exp1 = compile(propertyName);
        Expression exp2 = compile(otherPropertyName);

        Object lhs = exp1.getValue(context);
        Object rhs = exp2.getValue(context);

        return filterValue(lhs, rhs);
    }

    protected abstract boolean filterValue(Object lhs, Object rhs);

    @Override
    protected final Expression compile() {
        throw new UnexpectedException();
    }

    @Override
    protected final boolean filterValue(Object val) {
        throw new UnexpectedException();
    }

    protected abstract String getOperator();

    public void format(StringBuilder out) {
        out.append("(property ");
        out.append(propertyName);
        out.append(" ");
        out.append(getOperator());
        out.append(" ");
        out.append(otherPropertyName);
        out.append(")");
    }

}
