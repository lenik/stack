package com.bee32.sem.test;

import com.bee32.icsf.test.LoginedTestCase;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.util.WiredDaoTestCase;

/**
 * 关于调试配置：
 *
 * 默认设置为： 关闭 Facelets DEBUG 日志，刷新时间为 300 秒。
 *
 * 在具体的测试单元中，通过下面两个方法覆盖这两个参数：
 * <ul>
 * <li>
 * {@link #isDebugMode()} 默认 <code>false</code>。这一选项只影响 DEBUG 日志，其实没什么用。
 * <li> {@link #getRefreshPeriod()} 默认 300 秒，请在自己的单元中设置合适的值。
 * </ul>
 *
 * @see #isDebugMode()
 * @see #getRefreshPeriod()
 */
@Import(WiredDaoTestCase.class)
public abstract class SEMTestCase
        extends LoginedTestCase {

    public SEMTestCase() {
        stl.welcomeList.add("index-rich.jsf");
    }

}
