package com.bee32.sem.people.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.sem.user.util.SessionLoginInfo;

public class PeopleCriteria {

    public static Criterion ownedBy(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        return Restrictions.eq("owner.id", user.getId());
    }

    public static Criterion ownedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.requireCurrentUser();
        return ownedBy(currentUser);
    }

    public static Criterion nameLike(String keyword) {
        return Restrictions.like("name", "%" + keyword + "%");
    }

}
