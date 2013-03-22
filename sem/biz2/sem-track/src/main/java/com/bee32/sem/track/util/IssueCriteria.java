package com.bee32.sem.track.util;

import java.util.Set;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class IssueCriteria
        extends CriteriaSpec {

    public static CriteriaElement observedByCurrentUser() {
        Set<Integer> imIdSet = SessionUser.getInstance().getImIdSet();
        if (imIdSet == null)
            throw new IllegalStateException("imSet");

        return compose(//
                alias("observers", "ob"), //
                PrincipalCriteria.inImSet("ob.user", imIdSet));
    }

}
