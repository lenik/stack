package com.bee32.sem.makebiz.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.makebiz.entity.MakeOrder;

public class MakebizCriteria
        extends CriteriaSpec {

    @LeftHand(MakeOrder.class)
    public static ICriteriaElement customerLike(String pattern){
        return compose(
                alias("customer", "customer"),//
                likeIgnoreCase("customer.label", pattern,
                        MatchMode.ANYWHERE));
    }
}
