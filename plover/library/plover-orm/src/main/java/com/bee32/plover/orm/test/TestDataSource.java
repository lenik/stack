package com.bee32.plover.orm.test;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.qualifier.TestPurpose;
import com.bee32.plover.thirdparty.hibernate.util.HibernateProperties;
import com.p6spy.engine.common.P6SpyOptions;
import com.p6spy.engine.common.PropertiesRefreshListener;
import com.p6spy.engine.common.PropertiesRefresher;

@Component
@TestPurpose
@Lazy
public class TestDataSource
        extends BasicDataSource
        implements HibernateProperties {

    public static String defaultDriver = "org.h2.Driver";
    public static String defaultUrl = //
    "jdbc:h2:" + DataConfig.DATA_DIR + "/test;DB_CLOSE_ON_EXIT=FALSE";

    public static String defaultUsername = "sa";
    public static String defaultPassword = "";

    Properties properties;
    String realDriver;

    public TestDataSource() {
        properties = DataConfig.getProperties("test");

        setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");
        realDriver = properties.getProperty(connectionDriverClass, defaultDriver);

        TestP6SpyConfigurer testConfigurer = new TestP6SpyConfigurer();
        PropertiesRefresher.addPropertiesRefreshListener(testConfigurer);

        setUrl(properties.getProperty(connectionUrl, defaultUrl));
        setUsername(properties.getProperty(connectionUsername, defaultUsername));
        setPassword(properties.getProperty(connectionPassword, defaultPassword));

        setInitialSize(1);

        setDefaultAutoCommit(false);
    }

    class TestP6SpyConfigurer
            implements PropertiesRefreshListener {

        @Override
        public void refresh() {
            refreshP6SpyProperties();
        }
    }

    protected void refreshP6SpyProperties() {
        // P6SpyProperties.setSpyProperties(tmp);
        P6SpyOptions.addRealDriver(realDriver);
    }

}
