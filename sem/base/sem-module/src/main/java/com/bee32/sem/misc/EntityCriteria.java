package com.bee32.sem.misc;

import java.util.Date;

import com.bee32.icsf.login.LoginInfo;
import com.bee32.plover.criteria.hibernate.Between;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.ox1.principal.IUserPrincipal;

public class EntityCriteria
        extends CriteriaSpec {

    public static CriteriaElement ownedBy(IUserPrincipal user) {
        if (user == null)
            throw new NullPointerException("user");
        return equals("ownerId", user.getId());
    }

    public static CriteriaElement ownedByCurrentUser() {
        IUserPrincipal currentUser = LoginInfo.getInstance().getUser();
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
