package com.bee32.sem.people.util;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.entity.PersonLogin;
import com.bee32.sem.people.entity.PersonRole;

public class PeopleCriteria
        extends CriteriaSpec {

    public static CriteriaElement ownedBy(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        return equals("owner.id", user.getId());
    }

    public static CriteriaElement ownedByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.requireCurrentUser();
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

    @LeftHand(OrgUnit.class)
    public static CriteriaElement orgEquals(long orgId) {
        return equals("org.id", orgId);
    }

    @LeftHand(PersonRole.class)
    public static CriteriaElement orgUnitEquals(long orgUnitId) {
        return equals("orgUnit.id", orgUnitId);
    }

    @LeftHand(PersonLogin.class)
    public static CriteriaElement userEquals(String userId) {
        return equals("user.id", userId);
    }

}
