package com.bee32.plover.orm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.cfg.DBDialect;
import com.bee32.plover.site.cfg.OptimizationLevel;
import com.bee32.plover.site.cfg.VerboseLevel;
import com.bee32.plover.site.scope.PerSite;
import com.p6spy.engine.common.P6SpyOptions;
import com.p6spy.engine.common.PropertiesRefreshListener;
import com.p6spy.engine.common.PropertiesRefresher;

@Component
@PerSite
public class SiteDataSource
        extends BasicDataSource
// implements IHibernatePropertyNames //
{

    static Logger logger = LoggerFactory.getLogger(SiteDataSource.class);

    SiteInstance site;
    String realDriver;

    public SiteDataSource()
            throws LoadSiteException {
        site = ThreadHttpContext.getSiteInstance();

        DBDialect dialect = site.getDbDialect();
        realDriver = dialect.getDriverClass();

        if (isP6SpyEnabled()) {
            logger.info("P6spy is enabled, configure it.");

            setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");

            // realDriver 在 configurer 中转。
            TestP6SpyConfigurer testConfigurer = new TestP6SpyConfigurer();

            PropertiesRefresher.addPropertiesRefreshListener(testConfigurer);

        } else {
            logger.info("P6spy is disabled.");
            setDriverClassName(realDriver);
        }

        String database = site.getDbName();
        String user = site.getDbUser();
        String password = site.getDbPass();
        String urlFormat = dialect.getUrlFormat();
        String url = String.format(urlFormat, database);

        setUrl(url);
        setUsername(user);
        setPassword(password);

        setDefaultAutoCommit(false);

        OptimizationLevel opt = site.getOptimizationLevel();
        if (opt.compareTo(OptimizationLevel.LOW) <= 0) {
            // Set very small pool initial size for "debug" mode.
            setInitialSize(1);
        }
    }

    protected boolean isP6SpyEnabled() {
        VerboseLevel verbose = site.getVerboseLevel();
        boolean showSql = verbose.compareTo(VerboseLevel.SQL) >= 0;
        return showSql;
    }

    class TestP6SpyConfigurer
            implements PropertiesRefreshListener {

        @Override
        public void refresh() {
            refreshP6SpyProperties();
        }
    }

    protected void refreshP6SpyProperties() {
        P6SpyOptions.addRealDriver(realDriver);
    }

}
