package com.bee32.sem.chance.util;

import java.util.Date;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.user.util.SessionLoginInfo;

public class ChanceCriteria {

    public static Criterion ownedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.requireCurrentUser();
        return ownedBy(currentUser);
    }

    public static Criterion ownedBy(IUserPrincipal user) {
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

}
