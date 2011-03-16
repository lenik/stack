package com.bee32.plover.orm.test;

import java.io.File;

import javax.free.SystemProperties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.qualifier.TestPurpose;
import com.bee32.plover.inject.qualifier.Variant;

@Component
@TestPurpose
@Variant("h2")
@Lazy
public class TestH2DataSource
        extends BasicDataSource {

    static final String DATA_DIR;
    static {
        String userHome = SystemProperties.getUserHome();
        if (userHome == null) {
            userHome = System.getenv("HOME");
            if (userHome == null)
                userHome = ".";
        }
        DATA_DIR = userHome + "/.data";
        new File(DATA_DIR).mkdirs();
    }

    public static final String JDBC_URL = "jdbc:h2:" + DATA_DIR + "/test;DB_CLOSE_ON_EXIT=FALSE";

    public TestH2DataSource() {
        setDriverClassName("com.p6spy.engine.spy.P6SpyDriver");
        setUrl(JDBC_URL);
        setUsername("sa");
        setPassword("");

        setInitialSize(1);

        setDefaultAutoCommit(true);
    }

}
