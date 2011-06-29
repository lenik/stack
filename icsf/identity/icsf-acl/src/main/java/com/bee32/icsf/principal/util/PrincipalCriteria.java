package com.bee32.icsf.principal.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.Principal;

public class PrincipalCriteria {

    public static Criterion implies(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        return Restrictions.eq("principal.id", principal.getId());
    }

}
