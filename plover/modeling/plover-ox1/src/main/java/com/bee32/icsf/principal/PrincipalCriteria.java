package com.bee32.icsf.principal;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class PrincipalCriteria
        extends CriteriaSpec {

    /**
     * The property equals to the principal, or any in its im-set.
     */
    public static CriteriaElement inImSet(String propertyName, Principal principal) {
        if (propertyName == null)
            throw new NullPointerException("propertyName");
        if (principal == null)
            return null;
        return in(propertyName, principal.getImSet());
    }

    public static CriteriaElement inInvSet(String propertyName, Principal principal) {
        if (propertyName == null)
            throw new NullPointerException("propertyName");
        if (principal == null)
            return null;
        return in(propertyName, principal.getInvSet());
    }

}
