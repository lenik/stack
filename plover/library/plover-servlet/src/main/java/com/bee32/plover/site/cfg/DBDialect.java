package com.bee32.plover.site.cfg;

public enum DBDialect {

    H2(//
            "org.hibernate.dialect.H2Dialect", //
            "org.h2.Driver", //
            "jdbc:h2://H2_HOME/%s;DB_CLOSE_ON_EXIT=FALSE"),

    HSQL(//
            "org.hibernate.dialect.HSQLDialect", //
            "org.hsql.Driver", //
            "jdbc:hsql://HOME/%s"),

    PostgreSQL(//
            "org.hibernate.dialect.PostgreSQLDialect", //
            "org.postgresql.Driver", //
            "jdbc:postgresql://localhost:1063/%s"),

    Oracle(//
            "org.hibernate.dialect.OracleDialect", //
            "com.oracle.jdbc.Driver", //
            "jdbc:oracle://localhost/%s"),

    MySQL(//
            "org.hibernate.dialect.MySQLDialect", //
            "org.mysql.Driver", //
            "jdbc:mysql://localhost/%s"),

    ;

    final String dialectClass;
    final String driverClass;
    final String urlFormat;

    private DBDialect(String dialectClass, String driverClass, String urlFormat) {
        this.dialectClass = dialectClass;
        this.driverClass = driverClass;
        this.urlFormat = urlFormat;
    }

    public String getDialectClass() {
        return dialectClass;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUrlFormat() {
        return urlFormat;
    }

}
