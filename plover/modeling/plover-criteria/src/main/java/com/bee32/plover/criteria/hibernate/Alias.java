package com.bee32.plover.criteria.hibernate;

import javax.free.IllegalUsageException;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.springframework.expression.EvaluationContext;

public class Alias
        extends CriteriaElement {

    private static final long serialVersionUID = 1L;

    final String associationPath;
    final String alias;
    final int joinType;

    public Alias(String associationPath, String alias) {
        this(associationPath, alias, CriteriaSpecification.INNER_JOIN);
    }

    /**
     * @see CriteriaSpecification#INNER_JOIN
     * @see CriteriaSpecification#FULL_JOIN
     * @see CriteriaSpecification#LEFT_JOIN
     */
    public Alias(String associationPath, String alias, int joinType) {
        if (associationPath == null)
            throw new NullPointerException("associationPath");
        if (alias == null)
            throw new NullPointerException("alias");
        this.associationPath = associationPath;
        this.alias = alias;
        this.joinType = joinType;
    }

    @Override
    public void apply(Criteria criteria, int options) {
        criteria.createAlias(associationPath, alias, joinType);
    }

    @Override
    protected Criterion buildCriterion(int options) {
        return null;
    }

    @Override
    public boolean filter(Object obj, EvaluationContext context) {
        // context.setVariable(alias, associationPath);
        return true;
    }

    String getJoinTypeName() {
        switch (joinType) {
        case CriteriaSpecification.FULL_JOIN:
            return "FULL JOIN";
        case CriteriaSpecification.INNER_JOIN:
            return "INNER JOIN";
        case CriteriaSpecification.LEFT_JOIN:
            return "LEFT JOIN";
        }
        throw new IllegalUsageException("Bad join type: " + joinType);
    }

    @Override
    public void format(StringBuilder out) {
        out.append("(alias ");
        out.append(alias);
        out.append(" ");
        out.append(getJoinTypeName());
        out.append(" ");
        out.append(associationPath);
        out.append(")");
    }

}
