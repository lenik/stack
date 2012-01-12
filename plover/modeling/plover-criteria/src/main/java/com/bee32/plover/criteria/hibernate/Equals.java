package com.bee32.plover.criteria.hibernate;

import javax.free.Nullables;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.Expression;

public class Equals
        extends PropertyCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object value;

    public Equals(String propertyName, Object value) {
        super(propertyName);
        this.value = value;
    }

    @Override
    protected Criterion buildCriterion() {
        return Restrictions.eq(propertyName, value);
    }

    @Override
    protected Expression compile() {
        return compile(propertyName);
    }

    @Override
    protected boolean filterValue(Object var) {
        // Class<?> exprType = expr.getClass();
        // ParserUtil.parse(exprType, value);
        return Nullables.equals(var, value);
    }

    @Override
    public String getOperator() {
        return "==";
    }

    @Override
    public String getOperatorName() {
        return "等于";
    }

    @Override
    protected void formatValue(StringBuilder out) {
        out.append(quoteValue(value));
    }

}
