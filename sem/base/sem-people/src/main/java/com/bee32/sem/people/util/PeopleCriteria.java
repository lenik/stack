package com.bee32.sem.people.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.sem.people.entity.PartyTagname;

public class PeopleCriteria {

    public static Criterion ownedBy(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        return Restrictions.eq("owner.id", user.getId());
    }

    public static Criterion ownedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.requireCurrentUser();
        if (currentUser.getName().equals("admin"))
            return null;
        else
            return ownedBy(currentUser);
    }

    public static Criterion nameLike(String keyword) {
        return Restrictions.or(Restrictions.like("name", "%" + keyword + "%"),
                Restrictions.like("fullName", "%" + keyword + "%"));
    }

	public static Criterion outerPartyTagList(String name) {
		return Restrictions.not(Restrictions.in(name,
				new Object[] { PartyTagname.EMPLOYEE.getName() }));
	}

}
