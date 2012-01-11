package com.bee32.sem.sandbox;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public interface ICriteriaHolder
        extends Serializable {

    ICriteriaElement getCriteriaElement();

}
