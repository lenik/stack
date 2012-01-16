package com.bee32.plover.criteria.hibernate;

import java.lang.reflect.Method;

import javax.free.Nullables;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class IdEquals
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final Object value;

    public IdEquals(Object value) {
        this.value = value;
    }

    @Override
    public void apply(Criteria criteria, int options) {
    }

    @Override
    protected Criterion buildCriterion(int options) {
        return Restrictions.idEq(value);
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // ((Entity<?>)obj).getId()
        Object id;
        try {
            Method getter = obj.getClass().getMethod("getId");
            id = getter.invoke(obj);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return Nullables.equals(id, value);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(id-equals ");
        out.append(value);
        out.append(")");
    }

}
