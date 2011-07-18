package com.bee32.sem.chance.util;

import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.criteria.hibernate.CriteriaTemplate;
import com.bee32.sem.chance.entity.Chance;

public class ChanceCriteria
        extends CriteriaTemplate {

    public static Criterion ownedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.requireCurrentUser();
        return ownedBy(currentUser);
    }

    public static Criterion ownedBy(IUserPrincipal user) {
        if (user.getDisplayName().equals("admin"))
            return null;
        else
            return Restrictions.eq("owner.id", user.getId());
    }

    public static Criterion subjectLike(String keyword) {
        return Restrictions.like("subject", "%" + keyword + "%");
    }

    public static Criterion actedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.requireCurrentUser();
        return actedBy(currentUser);
    }

    public static Criterion actedBy(IUserPrincipal user) {
        if (user.getDisplayName().equals("admin"))
            return null;
        else
            return Restrictions.eq("actor.id", user.getId());
    }

    public static Criterion beginWithin(Date min, Date max) {
        if (min == null)
            throw new NullPointerException("start");
        if (max == null)
            throw new NullPointerException("end");
        return Restrictions.between("beginTime", min, max);
    }

    public static Criterion chanceOf(Chance chance) {
        return Restrictions.eq("chance.id", chance.getId());
    }

    public static Criterion nameLike(String namePattern) {
        if (namePattern.isEmpty())
            return null;
        return Restrictions.or(Restrictions.like("id", "%" + namePattern + "%"),
                Restrictions.like("fullName", "%" + namePattern + "%"));
    }

}
