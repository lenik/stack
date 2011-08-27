package com.bee32.icsf.login;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.ox1.principal.User;

public class LoginCriteria
        extends CriteriaSpec {

    @LeftHand(UserPassword.class)
    public static CriteriaElement forUser(User user) {
        if (user == null)
            throw new NullPointerException("user");
        return equals("user", user);
    }

}
