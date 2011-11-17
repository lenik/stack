package com.bee32.sem.asset.util;

import org.hibernate.criterion.CriteriaSpecification;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.asset.entity.BudgetRequest;

public class AssetCriteria
        extends CriteriaSpec {

    @LeftHand(BudgetRequest.class)
    public static ICriteriaElement haveNoCorrespondingTicket() {
        return compose(alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                new IsNull("ticket.id"));
    }
}
