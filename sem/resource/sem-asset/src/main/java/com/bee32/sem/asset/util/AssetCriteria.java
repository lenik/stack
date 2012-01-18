package com.bee32.sem.asset.util;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Disjunction;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.asset.entity.AccountSnapshot;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.asset.service.AssetQueryOptions;
import com.bee32.sem.process.verify.util.VerifyCriteria;

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
        List<AccountSubject> subjects = options.getSubjects();
        ICriteriaElement subjectSelection = null;
        if (options.isRecursive()) {
            if (subjects != null) {
                Disjunction disj = disj();
                for (AccountSubject subject : subjects) {
                    CriteriaElement like = _like("subject.id", subject.getId() + "%");
                    disj.add(like);
                }
                subjectSelection = disj;
            }
        } else {
            subjectSelection = _in("subject", subjects);
        }
        return compose(//
                // alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                options.isVerifiedOnly() ? VerifyCriteria.verified() : null, //
                _greaterThan("endTime", optBaseTime), //
                lessOrEquals("endTime", options.getTimestamp()), //

                subjectSelection, //

                _in("party", options.getParties()));
    }

    @LeftHand(AccountSubject.class)
    public static ICriteriaElement subjectLike(String code, String label) {
        return compose(//
                like("id", code, MatchMode.ANYWHERE), //
                like("label", label, MatchMode.ANYWHERE));
    }

}
