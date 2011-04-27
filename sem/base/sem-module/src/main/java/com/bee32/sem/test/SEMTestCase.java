package com.bee32.sem.test;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.context.OSIVFilter;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.UsingUtil;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.restful.DispatchServlet;
import com.bee32.plover.restful.RESTfulConfig;
import com.bee32.plover.zk.test.ZkTestCase;

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
public class SEMTestCase
        extends ZkTestCase {

    protected String PREFIX = RESTfulConfig.preferredPrefix;

    public SEMTestCase() {
        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);
    }

    @Override
    protected void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        // OSIV filter should before dispatch filter.
        stl.addFilter(OSIVFilter.class, "/*", 0);

        // Also enable Restful service.
        stl.addServlet(DispatchServlet.class, PREFIX + "/*");
    }

    @Override
    protected void applicationInitialized(ApplicationContext applicationContext) {
        SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
        samplesLoader.loadNormalSamples();
    }

}
