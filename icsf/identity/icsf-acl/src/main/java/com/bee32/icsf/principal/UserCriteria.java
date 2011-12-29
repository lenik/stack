package com.bee32.icsf.principal;

import com.bee32.icsf.login.SessionUser;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class UserCriteria {

    public static ICriteriaElement impliedByCurrentUser(String propertyName) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return PrincipalCriteria.inImSet(propertyName, currentUser);
    }

    public static CriteriaElement responsibleByCurrentUser(String propertyName) {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return PrincipalCriteria.inInvSet(propertyName, currentUser);
    }

}
