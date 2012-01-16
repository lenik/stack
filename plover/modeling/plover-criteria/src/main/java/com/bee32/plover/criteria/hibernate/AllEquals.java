package com.bee32.plover.criteria.hibernate;

import java.util.Map;
import java.util.Map.Entry;

import javax.free.Nullables;
import javax.free.UnexpectedException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

public class AllEquals
        extends SpelCriteriaElement {

    private static final long serialVersionUID = 1L;

    final Map<?, ?> propertyNameValues;

    public AllEquals(Map<?, ?> propertyNameValues) {
        this.propertyNameValues = propertyNameValues;
    }

    @Override
    protected Criterion buildCriterion(int options) {
        return Restrictions.allEq(propertyNameValues);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        if (context == null)
            context = getDefaultContext(obj);
        initContext(context, obj);

        for (Entry<?, ?> entry : propertyNameValues.entrySet()) {
            String propertyName = (String) entry.getKey();
            Object value = entry.getValue();

            Expression expr = compile(propertyName);
            Object var = expr.getValue(context);

            if (!Nullables.equals(var, value))
                return false;
        }

        return true;
    }

    @Override
    protected Expression compile() {
        throw new UnexpectedException();
    }

    @Override
    protected boolean filterValue(Object var) {
        throw new UnexpectedException();
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(all-equals");
        for (Entry<?, ?> entry : propertyNameValues.entrySet()) {
            Object name = entry.getKey();
            Object value = entry.getValue();
            out.append(" ");
            out.append(name);
            out.append("=");
            out.append(value);
        }
        out.append(")");
    }

}
