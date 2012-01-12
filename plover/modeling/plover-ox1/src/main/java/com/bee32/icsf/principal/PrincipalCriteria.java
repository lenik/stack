package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        Collection<Principal> imSet = principal.getImSet();
        List<Integer> idSet = new ArrayList<Integer>();
        for (Principal im : imSet)
            idSet.add(im.getId());
        return or(//
                isNull(propertyName + ".id"), //
                in(propertyName + ".id", idSet));
    }

    public static CriteriaElement inInvSet(String propertyName, Principal principal) {
        if (propertyName == null)
            throw new NullPointerException("propertyName");
        if (principal == null)
            return null;
        Collection<Principal> invSet = principal.getInvSet();
        List<Integer> idSet = new ArrayList<Integer>();
        for (Principal inv : invSet)
            idSet.add(inv.getId());
        return in(propertyName + ".id", idSet);
    }

}
