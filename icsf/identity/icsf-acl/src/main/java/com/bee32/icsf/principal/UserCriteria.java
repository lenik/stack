package com.bee32.icsf.principal;

import java.util.Set;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.acl.ACLCriteria;
import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;

public class UserCriteria
        extends CriteriaSpec {

    static class ctx
            extends DefaultDataAssembledContext {
    }

    public static CriteriaElement ownedByCurrentUser() {
        return impliedByCurrentUser("owner");
    }

    public static CriteriaElement impliedByCurrentUser(String propertyName) {
        Set<Integer> imSet = SessionUser.getInstance().getImIdSet();
        if (imSet == null)
            throw new IllegalStateException("imSet");

        // Admin-exception is only applied in this method.
        Role _adminRole = Role._adminRole();
        if (_adminRole != null && imSet.contains(_adminRole.getId()))
            return null;

        return PrincipalCriteria.inImSet(propertyName, imSet);
    }

    public static CriteriaElement responsibleByCurrentUser(String propertyName) {
        Set<Integer> invSet = SessionUser.getInstance().getInvIdSet();
        return PrincipalCriteria.inInvSet(propertyName, invSet);
    }

    public static ICriteriaElement distinctACLsForCurrentUserImply(Permission permission) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return ACLCriteria.distinctACLsImply(currentUser, permission);
    }

}
