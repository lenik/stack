package com.bee32.icsf.principal;

import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.ACLCriteria;
import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class UserCriteria
        extends CriteriaSpec {

    public static CriteriaElement ownedByCurrentUser() {
        return impliedByCurrentUser("owner");
    }

    public static CriteriaElement impliedByCurrentUser(String propertyName) {
        Set<Integer> imSet = SessionUser.getInstance().getImSet();
        return PrincipalCriteria.inImSet(propertyName, imSet);
    }

    public static CriteriaElement responsibleByCurrentUser(String propertyName) {
        Set<Integer> invSet = SessionUser.getInstance().getInvSet();
        return PrincipalCriteria.inInvSet(propertyName, invSet);
    }

    public static ICriteriaElement distinctACLsForCurrentUserImply(Permission permission) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return ACLCriteria.distinctACLsImply(currentUser, permission);
    }

}
