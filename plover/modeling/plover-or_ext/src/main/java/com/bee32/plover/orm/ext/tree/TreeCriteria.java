package com.bee32.plover.orm.ext.tree;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;

public class TreeCriteria
        extends CriteriaSpec {

    @LeftHand(TreeEntity.class)
    public static CriteriaElement root() {
        return isNull("parent");
    }

    @LeftHand(TreeEntity.class)
    public static <K extends Serializable> CriteriaElement childOf(K parentId) {
        return equals("parent.id", parentId);
    }

}
