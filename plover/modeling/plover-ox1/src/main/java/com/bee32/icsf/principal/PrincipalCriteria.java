package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.ox1.util.CommonCriteria;

public class PrincipalCriteria
        extends CriteriaSpec {

    static Logger logger = LoggerFactory.getLogger(PrincipalCriteria.class);

    @LeftHand(Principal.class)
    public static ICriteriaElement namedLike(String pattern) {
        return namedLike(pattern, false);
    }

    @LeftHand(Principal.class)
    public static ICriteriaElement namedLike(String pattern, boolean ignoreCase) {
        if (StringUtils.isEmpty(pattern))
            return null;
        return or(
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
        Collection<Principal> imSet;
        try {
            imSet = principal.getImSet();
        } catch (Exception e) {
            logger.error("Failed to get im-set, fall back to self.", e);
            imSet = Arrays.asList(principal); // self only.
        }
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
        Collection<Principal> invSet;
        try {
            invSet = principal.getInvSet();
        } catch (Exception e) {
            logger.error("Failed to get inv-set, fall back to self.", e);
            invSet = Arrays.asList(principal); // self only.
        }
        List<Integer> idSet = new ArrayList<Integer>();
        for (Principal inv : invSet)
            idSet.add(inv.getId());
        return in(propertyName + ".id", idSet);
    }

}
