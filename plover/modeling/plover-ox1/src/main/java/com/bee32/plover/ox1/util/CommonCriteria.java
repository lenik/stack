package com.bee32.plover.ox1.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class CommonCriteria
        extends CriteriaSpec {

    public static CriteriaElement namedLike(String pattern) {
        return namedLike(pattern, false);
    }

    public static CriteriaElement namedLike(String pattern, boolean ignoreCase) {
        if (pattern == null || pattern.isEmpty())
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

}
