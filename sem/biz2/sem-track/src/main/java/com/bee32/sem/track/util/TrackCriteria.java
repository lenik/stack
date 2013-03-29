package com.bee32.sem.track.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueReply;

public class TrackCriteria
        extends CriteriaSpec {

    @LeftHand(Issue.class)
    public static ICriteriaElement observedByCurrentUser() {
        Set<Integer> imIdSet = SessionUser.getInstance().getImIdSet();
        if (imIdSet == null)
            throw new IllegalStateException("imSet");

        return compose(//
                // proj(distinct(property("id"))),//
                alias("observers", "ob"), //
                PrincipalCriteria.inImSet("ob.observer", imIdSet));
    }

    @LeftHand(Issue.class)
    public static ICriteriaElement inFavBoxOfCurrentUser() {
        Set<Integer> imIdSet = SessionUser.getInstance().getImIdSet();
        if (imIdSet == null)
            throw new IllegalStateException("imSet");

        return compose(//
                // proj(distinct(property("id"))),//
                alias("observers", "ob"), //
                PrincipalCriteria.inImSet("ob.observer", imIdSet), //
                equals("fav", true));
    }

    @LeftHand(IssueReply.class)
    public static ICriteriaElement repliesForIssue(long issueId, boolean ascending) {
        return compose(//
                equals("issue.id", issueId), //
                ascending ? ascOrder("createdDate") : descOrder("createdDate"));
    }

    static Set<Integer> FINALIZED_STATES;
    static {
        FINALIZED_STATES = new HashSet<Integer>(Arrays.<Integer> asList(//
                IssueState.CLOSED.getValue(), //
                IssueState.DONE.getValue(), //
                IssueState.DUP.getValue(), //
                IssueState.FIXED.getValue(), //
                IssueState.INVALID.getValue(), //
                IssueState.WONT_FIX.getValue() //
                ));
    }

    @LeftHand(Issue.class)
    public static ICriteriaElement finalized() {
        return in("state", FINALIZED_STATES);
    }

}
