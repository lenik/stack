package com.bee32.plover.orm.config;

import java.util.Properties;

import org.hibernate.cache.NoCacheProvider;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.cfg.DBDialect;
import com.bee32.plover.site.cfg.OptimizationLevel;
import com.bee32.plover.thirdparty.hibernate.util.IHibernatePropertyNames;

public class SiteDatabaseConfigurer
        extends AbstractDatabaseConfigurer
        implements IHibernatePropertyNames {

    @Override
    public void configureHibernateProperties(Properties properties)
            throws Exception {

        SiteInstance site = ThreadHttpContext.getSiteInstance();
        // VerboseLevel verbose = site.getVerboseLevel();
        OptimizationLevel opt = site.getOptimizationLevel();
        DBDialect dialect = site.getDbDialect();
        boolean debugMode = opt.compareTo(OptimizationLevel.LOW) <= 0;

        // Set defaults at first
        if (debugMode)
            setTestDefaults(properties);
        else
            setMainDefaults(properties);

        // Overrides.
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
        properties.setProperty(hbm2ddlAutoKey, "update");

        // Debug
        properties.setProperty(showSqlKey, "false"); // opt. away in userconf
        properties.setProperty(formatSqlKey, "false");
        properties.setProperty(useSqlCommentsKey, "false");
        // properties.setProperty(generateStatistics, "true");

        // Performance
        properties.setProperty(connectionPoolSizeKey, "30");
        properties.setProperty(cacheProviderClassKey, NoCacheProvider.class.getName());
    }

    void setTestDefaults(Properties properties) {
        // Mapping
        properties.setProperty(hbm2ddlAutoKey, "create-drop"); // opt. away in userconf

        // Debug
        properties.setProperty(showSqlKey, "false"); // true
        properties.setProperty(formatSqlKey, "true");
        properties.setProperty(useSqlCommentsKey, "true");
        // properties.setProperty(generateStatistics, "true");

        // Performance
        properties.setProperty(connectionPoolSizeKey, "3");

        // TODO: The CacheProvider is deprecated.
        properties.setProperty(cacheProviderClassKey, NoCacheProvider.class.getName());
    }

}
