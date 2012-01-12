package com.bee32.plover.ox1.util;

import java.util.Date;

import org.hibernate.criterion.MatchMode;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.Role;
import com.bee32.plover.criteria.hibernate.Between;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.util.date.LocalDateUtil;

public class CommonCriteria
        extends CriteriaSpec {

    public static CriteriaElement ownedBy(Principal user) {
        if (user == null)
            throw new NullPointerException("user");
        return _ownedBy(user);
    }

    public static CriteriaElement _ownedBy(Principal user) {
        if (user == null)
            return null;

        if (user.implies(Role.adminRole))
            return null;

        return PrincipalCriteria.inImSet("owner", user);
    }

    public static CriteriaElement namedLike(String pattern) {
        return namedLike(pattern, false);
    }

    public static CriteriaElement namedLike(String pattern, boolean ignoreCase) {
        if (pattern == null)
            return null;
        pattern = pattern.trim();
        if (pattern.isEmpty())
            return null;
        if (ignoreCase)
            return likeIgnoreCase("name", pattern, MatchMode.ANYWHERE);
        else
            return like("name", pattern, MatchMode.ANYWHERE);
    }

    public static CriteriaElement labeledWith(String string) {
        return labeledWith(string, false);
    }

    public static CriteriaElement labeledWith(String string, boolean ignoreCase) {
        if (string == null || string.isEmpty())
            return null;
        if (ignoreCase)
            return likeIgnoreCase("label", string, MatchMode.ANYWHERE);
        else
            return like("label", string, MatchMode.ANYWHERE);
    }

    public static CriteriaElement describedWith(String string) {
        return describedWith(string, false);
    }

    public static CriteriaElement describedWith(String string, boolean ignoreCase) {
        if (string == null || string.isEmpty())
            return null;
        if (ignoreCase)
            return likeIgnoreCase("description", string, MatchMode.ANYWHERE);
        else
            return like("description", string, MatchMode.ANYWHERE);
    }

    /**
     * Between the expanded date range.
     */
    public static CriteriaElement betweenEx(String property, Date beginDate, Date endDate) {
        return new Between(property, //
                LocalDateUtil.beginOfTheDay(beginDate), //
                LocalDateUtil.endOfTheDay(endDate));
    }

    public static CriteriaElement createdBetweenEx(Date beginDate, Date endDate) {
        return betweenEx("createdDate", beginDate, endDate);
    }

}
