package com.bee32.sem.people.util;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTagname;

public class PeopleCriteria
        extends CriteriaSpec {

    public static CriteriaElement ownedBy(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        return equals("owner", user.getId());
    }

    public static CriteriaElement ownedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.getUser();
        if (currentUser.getName().equals("admin"))
            return null;
        else
            return ownedBy(currentUser);
    }

    public static CriteriaElement namedLike(String keyword) {
        return or(like("name", "%" + keyword + "%"), like("fullName", "%" + keyword + "%"));
    }

    @LeftHand(PartyTagname.class)
    public static CriteriaElement externalTagname() {
        return not(in("id", new Object[] { PartyTagname.INTERNAL.getId() }));
    }

    @LeftHand(Party.class)
    public static ICriteriaElement internal() {
        return compose(alias("tags", "tag"), //
                in("tag.id", PartyTagname.INTERNAL.getId()));
    }

}
