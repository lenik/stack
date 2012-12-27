package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

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
