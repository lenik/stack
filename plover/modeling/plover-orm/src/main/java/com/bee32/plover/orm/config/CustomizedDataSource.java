package com.bee32.plover.orm.config;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.thirdparty.hibernate.util.HibernateProperties;
import com.p6spy.engine.common.P6SpyOptions;
import com.p6spy.engine.common.PropertiesRefreshListener;
import com.p6spy.engine.common.PropertiesRefresher;

public abstract class CustomizedDataSource
        extends BasicDataSource
        implements HibernateProperties {

    static Logger logger = LoggerFactory.getLogger(CustomizedDataSource.class);

    public static String defaultDriver = "org.h2.Driver";
    public static String defaultUrlFormat = "jdbc:h2:" + DataConfig.DATA_DIR + "/%s;DB_CLOSE_ON_EXIT=FALSE";
    public static String defaultUsername = "sa";
    public static String defaultPassword = "";

    private Properties properties;
    private String realDriver;

    public CustomizedDataSource(String name) {

        properties = DataConfig.getProperties(name);

        realDriver = properties.getProperty(connectionDriverClass, getDriverClassName());

        if (isP6SpyEnabled()) {
            setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");

            TestP6SpyConfigurer testConfigurer = new TestP6SpyConfigurer();

            PropertiesRefresher.addPropertiesRefreshListener(testConfigurer);
        } else {
            setDriverClassName(realDriver);
        }

        String defaultUrl = String.format(defaultUrlFormat, name);
        setUrl(properties.getProperty(connectionUrl, defaultUrl));

        setUsername(properties.getProperty(connectionUsername, defaultUsername));
        setPassword(properties.getProperty(connectionPassword, defaultPassword));

        setDefaultAutoCommit(false);
    }

    protected boolean isP6SpyEnabled() {
        return false;
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
