package com.bee32.icsf.test;

import javax.servlet.http.HttpSessionEvent;

import org.eclipse.jetty.util.resource.Resource;
import org.springframework.context.ApplicationContext;

import com.bee32.icsf.login.LoginManager;
import com.bee32.icsf.principal.User;
import com.bee32.plover.faces.test.FaceletsTestCase;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.restful.RESTfulConfig;

@Import(WiredDaoTestCase.class)
public abstract class LoginedTestCase
        extends FaceletsTestCase {

    protected String PREFIX = RESTfulConfig.preferredPrefix;

    protected ApplicationContext appctx;

    static {
        // XXX zip-closed fix. could be a bug of jetty.
        Resource.setDefaultUseCaches(false);
    }

    @Override
    protected void applicationInitialized(ApplicationContext context) {
        this.appctx = context;
        // context.getBeansOfType(SamplesLoaderActivator.class);
    }

    protected String getLoggedInUser() {
        return "admin";
    }

    @Override
    public void initSession(HttpSessionEvent event) {
        String userName = getLoggedInUser();

        if (userName != null) {
            if (appctx == null)
                throw new IllegalStateException("Application context isn't initalized, yet.");

            CommonDataManager dataManager = appctx.getBean(CommonDataManager.class);
            User user = dataManager.asFor(User.class).getByName(userName);

            LoginManager loginManager = LoginManager.getInstance();
            loginManager.logIn(user);
        }
    }

}
