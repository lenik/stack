package com.bee32.plover.orm.context;

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

    public static final String JDBC_URL = "jdbc:h2:target/testdb;DB_CLOSE_ON_EXIT=FALSE";

    public TestH2DataSource() {
        setDriverClassName("org.h2.Driver");

        setUrl(JDBC_URL);
        setUsername("sa");
        setPassword("");

        setInitialSize(1);

        setDefaultAutoCommit(false);
    }

}
