package com.bee32.sem.test;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.User;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.context.OSIVFilter;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.Using;
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

    protected ApplicationContext appContext;

    public SEMTestCase() {
        Using useUnit = getClass().getAnnotation(Using.class);
        if (useUnit == null)
            throw new IllegalUsageException("@Using isn't defined on " + getClass());

        PersistenceUnit unit = UsingUtil.getUsingUnit(getClass());
        CustomizedSessionFactoryBean.setForceUnit(unit);

        stl.welcomeList.add("index-rich.jsf");
    }

    @Override
    protected final void configureBuiltinServlets() {
        super.configureBuiltinServlets();

        // OSIV filter should before dispatch filter.
        stl.addFilter(OSIVFilter.class, "/*", 0);

        // Also enable Restful service.
        stl.addServlet(DispatchServlet.class, PREFIX + "/*");
    }

    @Override
    protected void applicationInitialized(ApplicationContext context) {
        this.appContext = context;
        SamplesLoader samplesLoader = context.getBean(SamplesLoader.class);
        samplesLoader.loadNormalSamples();
    }

    protected String getLoggedInUser() {
        return "admin";
    }

    @Override
    public void initSession(HttpSession session) {
        String userName = getLoggedInUser();

        if (userName != null) {
            if (appContext == null)
                throw new IllegalStateException("Application context isn't initalized, yet.");

            CommonDataManager dataManager = appContext.getBean(CommonDataManager.class);
            IEntityAccessService<User, String> userService = dataManager.access(User.class);
            User user = userService.load(userName);

            SessionLoginInfo.setCurrentUser(user);
        }
    }

}
