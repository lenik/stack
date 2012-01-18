package com.bee32.icsf.principal;

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
        User currentUser = SessionUser.getInstance().getInternalUser();
        return PrincipalCriteria.inImSet(propertyName, currentUser);
    }

    public static CriteriaElement responsibleByCurrentUser(String propertyName) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return PrincipalCriteria.inInvSet(propertyName, currentUser);
    }

    public static ICriteriaElement distinctACLsForCurrentUserImply(Permission permission) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return ACLCriteria.distinctACLsImply(currentUser, permission);
    }

}
