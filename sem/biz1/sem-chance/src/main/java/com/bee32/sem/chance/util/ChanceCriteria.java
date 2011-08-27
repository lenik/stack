package com.bee32.sem.chance.util;

import java.util.Date;

import com.bee32.icsf.login.LoginInfo;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.ox1.principal.IUserPrincipal;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.entity.ChanceAction;

public class ChanceCriteria
        extends CriteriaSpec {

    public static CriteriaElement subjectLike(String keyword) {
        if (keyword == null || keyword.isEmpty())
            return null;
        else
            return like("subject", "%" + keyword + "%");
    }

    public static CriteriaElement actedByCurrentUser() {
        IUserPrincipal currentUser = LoginInfo.getInstance().getUser();
        return actedBy(currentUser);
    }

    public static CriteriaElement actedBy(IUserPrincipal user) {
        if (user.getDisplayName().equals("admin"))
            return null;
        else
            return equals("actor.id", user.getId());
    }

    public static CriteriaElement beganWithin(Date start, Date end) {
        if (start == null)
            throw new NullPointerException("start");
        if (end == null)
            throw new NullPointerException("end");
        return between("beginTime", start, end);
    }

    public static CriteriaElement chanceEquals(ChanceDto chance) {
        return equals("chance.id", chance.getId());
    }

    public static CriteriaElement nameLike(String namePattern) {
        if (namePattern == null || namePattern.isEmpty())
            return null;
        return or(like("id", "%" + namePattern + "%"), //
                like("fullName", "%" + namePattern + "%"));
    }

    @LeftHand(ChanceAction.class)
    public static CriteriaElement danglingChance() {
        return isNull("chance");
    }

}
