package com.bee32.plover.ox1.util;

import java.util.Date;

import org.hibernate.criterion.MatchMode;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.Role;
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

    public static CriteriaElement labelledWith(String string) {
        return labelledWith(string, false);
    }

    public static CriteriaElement labelledWith(String string, boolean ignoreCase) {
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
    public static CriteriaElement between(String property, Date beginTime, Date endTime) {
        CriteriaElement _begin = _greaterOrEquals(property, beginTime);
        CriteriaElement _end = _lessThan(property, endTime);
        if (_begin == null)
            return _end;
        if (_end == null)
            return _begin;
        return and(_begin, _end);
    }

    public static CriteriaElement dateBetween(Date beginTime, Date endTime) {
        return between("date", beginTime, endTime);
    }

    public static CriteriaElement createdBetween(Date beginTime, Date endTime) {
        return between("createdDate", beginTime, endTime);
    }

    public static CriteriaElement lastModifiedBetween(Date beginTime, Date endTime) {
        return between("lastModified", beginTime, endTime);
    }

    public static CriteriaElement beginEndDateBetween(Date beginTime, Date endTime) {
        CriteriaElement _begin = or(isNull("beginTime"), _greaterOrEquals("beginTime", beginTime));
        CriteriaElement _end = or(isNull("endTime"), _lessThan("endTime", beginTime));
        if (_begin == null)
            return _end;
        if (_end == null)
            return _begin;
        return and(_begin, _end);
    }

    /**
     * Between the expanded date range.
     */
    public static CriteriaElement betweenEx(String property, Date beginDate, Date endDate) {
        return between(property, //
                LocalDateUtil.beginOfTheDay(beginDate), //
                LocalDateUtil.endOfTheDay(endDate));
    }

    public static CriteriaElement createdBetweenEx(Date beginDate, Date endDate) {
        return betweenEx("createdDate", beginDate, endDate);
    }

}
