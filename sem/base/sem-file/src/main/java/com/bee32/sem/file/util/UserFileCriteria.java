package com.bee32.sem.file.util;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class UserFileCriteria
        extends CriteriaSpec {
    public static CriteriaElement ownerByCurrentUser() {
        IUserPrincipal currentUser = SessionLoginInfo.getUser();

        if (currentUser.getDisplayName().equals("admin"))
            return null;
        else
            return equals("owner.id", currentUser.getId());
    }
}
