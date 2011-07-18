package com.bee32.icsf.login;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.CriteriaTemplate;

public class LoginCriteria
        extends CriteriaTemplate {

    /**
     * Example:
     *
     * for(UserPassword.class).unique(userOf(user)).
     */
    public static Criterion userOf(User user) {
        return Restrictions.eq("user.id", user.getId());
    }

}
