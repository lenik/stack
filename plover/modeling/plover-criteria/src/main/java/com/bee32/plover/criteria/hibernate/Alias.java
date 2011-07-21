package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;

class Alias
        extends CriteriaElement {

    final String associationPath;
    final String alias;
    final int joinType;

    public Alias(String associationPath, String alias) {
        this(associationPath, alias, CriteriaSpecification.INNER_JOIN);
    }

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
    public void apply(Criteria criteria) {
        criteria.createAlias(associationPath, alias, joinType);
    }

    @Override
    protected Criterion buildCriterion() {
        return null;
    }

}
