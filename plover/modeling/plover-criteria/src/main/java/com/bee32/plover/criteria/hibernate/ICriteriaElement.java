package com.bee32.plover.criteria.hibernate;

import org.hibernate.Criteria;

public interface ICriteriaElement {

    void apply(Criteria criteria);

}
