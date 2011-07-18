package com.bee32.icsf.principal.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.criteria.hibernate.CriteriaTemplate;

public class PrincipalCriteria
        extends CriteriaTemplate {

    public static Criterion implies(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        return Restrictions.eq("principal.id", principal.getId());
    }

}
