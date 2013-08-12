package com.bee32.plover.orm;

import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

/**
 * Plover ORM 框架
 *
 * Plover 关系对象表示（核心）数据单元
 *
 * <p lang="en">
 * Plover ORM Module
 *
 * The kernel ORM support provided by Plover Framework.
 */
@Oid({ 3, 12, PloverOids.Library, PloverOids.library.ORM })
public class PloverORMModule
        extends ERModule {

    public static final String PREFIX = "/3/12/3/4";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
