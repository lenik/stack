package com.bee32.sem.track.util;

import java.util.Set;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.track.entity.Issue;

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

}
