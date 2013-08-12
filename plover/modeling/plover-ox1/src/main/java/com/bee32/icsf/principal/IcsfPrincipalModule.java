package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

/**
 * ICSF Principal Module
 *
 * An ICSF module defines the principals, this includes the users, groups, and roles.
 *
 * <p lang="zh-cn">
 * ICSF安全主体模块
 *
 * ICSF安全框架中定义的安全主体对象，包括如用户、组、角色等。
 */
@Oid({ 3, 7, /* IcsfOids.Principal */1, })
public class IcsfPrincipalModule
        extends ERModule {

    public static final String PREFIX = "/3/7/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(User.class, "user");
    }

}
