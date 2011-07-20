package com.bee32.icsf.principal.util;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class PrincipalCriteria
        extends CriteriaSpec {

    public static CriteriaElement implies(Principal principal) {
        if (principal == null)
            throw new NullPointerException("principal");

        return equals("principal.id", principal.getId());
    }

}
