package com.bee32.sem.asset.util;

import java.util.Date;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.asset.entity.AccountSnapshot;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.asset.service.AssetQueryOptions;

public class AssetCriteria
        extends CriteriaSpec {

    @LeftHand(AccountSnapshot.class)
    public static ICriteriaElement latestSnapshotBefore(Date date) {
        return compose(//
                limit(0, 1), //
                lessThan("endTime", date), //
                descOrder("endTime"));
    }

    @LeftHand(BudgetRequest.class)
    public static ICriteriaElement haveNoCorrespondingTicket() {
        return compose(alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                new IsNull("ticket.id"));
    }

    @LeftHand(AccountTicketItem.class)
    public static ICriteriaElement select(AssetQueryOptions options, Date optBaseTime) {
        return compose(//
                // alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                // VERIFIED?
                _greaterThan("endTime", optBaseTime), //
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
