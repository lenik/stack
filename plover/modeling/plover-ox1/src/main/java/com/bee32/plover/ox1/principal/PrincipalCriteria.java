package com.bee32.plover.ox1.principal;

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
