package com.bee32.sem.purchase.util;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.purchase.entity.PurchaseRequest;

public class PurchaseCriteria
        extends CriteriaSpec {

    @LeftHand(PurchaseRequest.class)
    public static ICriteriaElement boundRequest(long planId) {
        return compose(alias("plans", "plan"), equals("plan.id", planId));
    }

}
