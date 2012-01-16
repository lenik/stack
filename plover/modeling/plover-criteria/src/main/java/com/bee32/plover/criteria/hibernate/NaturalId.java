package com.bee32.plover.criteria.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.Pair;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.NaturalIdentifier;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.EvaluationContext;

public class NaturalId
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    List<Pair<String, Object>> pairs = new ArrayList<Pair<String, Object>>();

    public NaturalId() {
    }

    @Override
    public void apply(Criteria criteria, int options) {
    }

    public void set(String property, Object value) {
        Pair<String, Object> pair = new Pair<String, Object>(property, value);
        pairs.add(pair);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        NaturalIdentifier nid = Restrictions.naturalId();
        for (Pair<String, Object> pair : pairs) {
            String property = pair.getKey();
            Object value = pair.getValue();
            nid.set(property, value);
        }
        return nid;
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // TODO
        throw new NotImplementedException();
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(natural-id");
        for (Pair<String, Object> pair : pairs) {
            String property = pair.getKey();
            Object value = pair.getValue();
            out.append(" ");
            out.append(property);
            out.append("=");
            out.append(value);
        }
        out.append(")");
    }

}
