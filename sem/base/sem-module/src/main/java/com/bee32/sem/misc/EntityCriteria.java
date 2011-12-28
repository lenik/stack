package com.bee32.sem.misc;

import java.util.Date;

import com.bee32.icsf.login.LoginException;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Between;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class EntityCriteria
        extends CriteriaSpec {

    public static CriteriaElement ownedBy(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        return equals("owner", user);
    }

    public static CriteriaElement ownedByCurrentUser()
            throws LoginException {
        User currentUser = SessionUser.getInstance().getInternalUser();
        if (currentUser.getName().equals("admin"))
            return null;
        else
            return ownedBy(currentUser);
    }

    /**
     * Between the expanded date range.
     */
    public static CriteriaElement betweenEx(String property, Date beginDate, Date endDate) {
        return new Between(property, //
                LocalDateUtil.beginOfTheDay(beginDate), //
                LocalDateUtil.endOfTheDay(endDate));
    }

    public static CriteriaElement createdBetweenEx(Date beginDate, Date endDate) {
        return betweenEx("createdDate", beginDate, endDate);
    }

}
