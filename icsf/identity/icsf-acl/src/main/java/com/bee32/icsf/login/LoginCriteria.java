package com.bee32.icsf.login;

import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;

/**
 * 登录滤集
 */
public class LoginCriteria
        extends CriteriaSpec {

    @LeftHand(UserPassword.class)
    public static CriteriaElement forUser(User user) {
        if (user == null)
            throw new NullPointerException("user");
        return equals("user", user);
    }

}
