package com.bee32.zebra.oa;

import net.bodz.bas.meta.decl.Priority;

public @interface OaGroups {

    /** 会计 */
    @Priority(0)
    @interface Accounting {
    }

    /** 分类 */
    @interface Classification {
    }

    /** 颜色管理 */
    @interface ColorManagement {
    }

    /** 定制扩展 */
    @interface CustomExtension {
    }

    /** 标识 */
    @interface Identity {
    }

    /** 包装 */
    @interface Packaging {
    }

    /** 位置 */
    @interface Position {
    }

    /** 日程 */
    @interface Schedule {
    }

    /** 设置选项 */
    @interface Setting {
    }

    /** 运输 */
    @interface Transportation {
    }

    /** 用户交互 */
    @interface UserInteraction {
    }

}
