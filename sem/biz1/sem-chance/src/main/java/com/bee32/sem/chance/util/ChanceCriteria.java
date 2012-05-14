package com.bee32.sem.chance.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;

public class ChanceCriteria extends CriteriaSpec {

    public static CriteriaElement subjectLike(String keyword, boolean ignoreCase) {
        if (keyword == null || keyword.isEmpty())
            return null;
        if (ignoreCase)
            return likeIgnoreCase("subject", keyword, MatchMode.ANYWHERE);
        else
            return like("subject", keyword, MatchMode.ANYWHERE);
    }

    public static CriteriaElement stageOf(String stageId) {
        if (stageId == null || stageId.isEmpty())
            return null;
        return equals("stage.id", stageId);
    }

    public static CriteriaElement sourceTypeOf(String sourceTypeId) {
        if (sourceTypeId == null || sourceTypeId.isEmpty())
            return null;
        return equals("source.id", sourceTypeId);
    }

    public static CriteriaElement actedByCurrentUser() {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return actedBy(currentUser);
    }

    public static CriteriaElement actedBy(IUserPrincipal user) {
        if (user.getDisplayName().equals("admin"))
            return null;
        else
            return equals("actor.id", user.getId());
    }

    @LeftHand(ChanceAction.class)
    public static CriteriaElement danglingChance() {
        return isNull("chance");
    }

    @LeftHand(Chance.class)
    public static ICriteriaElement actionSubjectLike(String pattern) {
        return compose(alias("chance", "cha"),//
                likeIgnoreCase("cha.subject", pattern, MatchMode.ANYWHERE));
    }

    @LeftHand(Chance.class)
    public static CriteriaElement actionContentLike(String pattern) {
        return likeIgnoreCase("moreInfo", pattern, MatchMode.ANYWHERE);
    }

    @LeftHand(Chance.class)
    public static CriteriaElement isPlan(boolean flag){
        return equals("plan", flag);
    }
}
