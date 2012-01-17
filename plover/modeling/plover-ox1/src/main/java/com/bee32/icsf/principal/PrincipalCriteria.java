package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.ox1.util.CommonCriteria;

public class PrincipalCriteria
        extends CriteriaSpec {

    @LeftHand(Principal.class)
    public static ICriteriaElement namedLike(String pattern) {
        return namedLike(pattern, false);
    }

    @LeftHand(Principal.class)
    public static ICriteriaElement namedLike(String pattern, boolean ignoreCase) {
        return or(
                //
                CommonCriteria.namedLike(pattern, ignoreCase), //
                ignoreCase ? likeIgnoreCase("fullName", pattern, MatchMode.ANYWHERE) : like("fullName", pattern,
                        MatchMode.ANYWHERE));
    }

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
