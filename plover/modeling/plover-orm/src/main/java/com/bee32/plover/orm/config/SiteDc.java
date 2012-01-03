package com.bee32.plover.orm.config;

import java.util.Properties;

import org.hibernate.cache.NoCacheProvider;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.LoadSiteException;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.cfg.DBDialect;
import com.bee32.plover.site.cfg.OptimizationLevel;
import com.bee32.plover.site.cfg.VerboseLevel;
import com.bee32.plover.thirdparty.hibernate.util.HibernateProperties;

public class SiteDc
        extends AbstractDc
        implements HibernateProperties {

    SiteInstance site;
    VerboseLevel verbose;
    OptimizationLevel opt;
    boolean debugMode;

    public SiteDc()
            throws LoadSiteException {
        site = ThreadHttpContext.getSiteInstance();
        verbose = site.getVerboseLevel();
        opt = site.getOptimizationLevel();
        debugMode = opt.compareTo(OptimizationLevel.LOW) <= 0;
    }

    @Override
    public void configureHibernateProperties(Properties properties)
            throws Exception {
        if (debugMode)
            setTestDefaults(properties);
        else
            setMainDefaults(properties);

        DBDialect dialect = site.getDbDialect();

        properties.put(dialectKey, dialect.getDialectClass());
        properties.put(connectionDriverClassKey, dialect.getDriverClass());
        properties.put(connectionUrlKey, site.getDbUrl());
        properties.put(connectionUsernameKey, site.getDbUser());
        properties.put(connectionPasswordKey, site.getDbPass());

        String autoddl = site.getAutoDDL().getHibernatePropertyValue();
        properties.put(hbm2ddlAutoKey, autoddl);
    }

    void setMainDefaults(Properties properties) {
        // Mapping
        properties.setProperty(hbm2ddlAutoKey, "create-drop");

        // Debug
        properties.setProperty(showSqlKey, "false"); // true
        properties.setProperty(formatSqlKey, "true");
        properties.setProperty(useSqlCommentsKey, "true");
        // properties.setProperty(generateStatistics, "true");

        // Performance
        properties.setProperty(connectionPoolSizeKey, "1");
        properties.setProperty(cacheProviderClassKey, NoCacheProvider.class.getName());
    }

    void setTestDefaults(Properties properties) {
        // Mapping
        properties.setProperty(hbm2ddlAutoKey, "validate"); // opt. away in userconf

        // Debug
        properties.setProperty(showSqlKey, "false"); // opt. away in userconf
        properties.setProperty(formatSqlKey, "false");
        properties.setProperty(useSqlCommentsKey, "false");

        // Performance
        properties.setProperty(connectionPoolSizeKey, "100");

        // TODO: The CacheProvider is deprecated.
        properties.setProperty(cacheProviderClassKey, NoCacheProvider.class.getName());
    }

}
