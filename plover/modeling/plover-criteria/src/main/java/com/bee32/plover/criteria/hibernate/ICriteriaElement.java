package com.bee32.plover.criteria.hibernate;

import java.io.Serializable;

import org.hibernate.Criteria;

public interface ICriteriaElement
        extends Serializable {

    void apply(Criteria criteria);

}
