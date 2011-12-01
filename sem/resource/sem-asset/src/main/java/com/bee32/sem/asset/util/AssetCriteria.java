package com.bee32.sem.asset.util;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.asset.service.AssetQueryOptions;

public class AssetCriteria
        extends CriteriaSpec {

    @LeftHand(BudgetRequest.class)
    public static ICriteriaElement haveNoCorrespondingTicket() {
        return compose(alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                new IsNull("ticket.id"));
    }

    @LeftHand(AccountTicketItem.class)
    public static ICriteriaElement select(AssetQueryOptions options) {
        return compose(//
                // alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                // VERIFIED?
                lessOrEquals("endTime", options.getTimestamp()), //

                // LIKE "01%"
                // _equals("subject", options.getSubject()), //
                _in("subject", options.getSubjects()), //

                _in("party", options.getParties()));
    }

    @LeftHand(AccountSubject.class)
    public static ICriteriaElement subjectLike(String code, String label) {
        return compose(//
                like("id", code, MatchMode.ANYWHERE), //
                like("label", label, MatchMode.ANYWHERE));
    }

}
