package com.bee32.sem.people.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTagname;

public class PeopleCriteria
        extends CriteriaSpec {

    public static Criterion ownedBy(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        return eq("owner.id", user.getId());
    }

    public static Criterion ownedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.requireCurrentUser();
        if (currentUser.getName().equals("admin"))
            return null;
        else
            return ownedBy(currentUser);
    }

    public static Criterion nameLike(String keyword) {
        return or(like("name", "%" + keyword + "%"), like("fullName", "%" + keyword + "%"));
    }

    public static Criterion outerPartyTagList(String name) {
        return not(in(name, new Object[] { PartyTagname.INTERNAL.getName() }));
    }

    public static DetachedCriteria hasTag(String pattern) {
        DetachedCriteria c = DetachedCriteria
                .forClass(Party.class)
                .createAlias("tags", "tag")
                .add(Restrictions.in("tag.id",
                        new Object[] { PartyTagname.INTERNAL.getId() }))
                .add(nameLike(pattern));
        return c;
    }

}
