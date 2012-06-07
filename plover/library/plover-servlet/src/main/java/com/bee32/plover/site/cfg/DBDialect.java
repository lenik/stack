package com.bee32.plover.site.cfg;

import java.util.Collection;

public class DBDialect
        extends SiteConfigEnum<String, DBDialect> {

    private static final long serialVersionUID = 1L;

    final String dialectClass;
    final String driverClass;
    final String urlFormat;

    private DBDialect(String name, String dialectClass, String driverClass, String urlFormat) {
        super(urlFormat, name);
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

    public static Collection<DBDialect> values() {
        return values(DBDialect.class);
    }

    public static DBDialect forName(String altName) {
        return forName(DBDialect.class, altName);
    }

    public static DBDialect forValue(String value) {
        return forValue(DBDialect.class, value);
    }

    public static final DBDialect H2 = new DBDialect("h2", //
            "org.hibernate.dialect.H2Dialect", //
            "org.h2.Driver", //
            "jdbc:h2://H2_HOME/%s;DB_CLOSE_ON_EXIT=FALSE");

    public static final DBDialect HSQL = new DBDialect("hsql",//
            "org.hibernate.dialect.HSQLDialect", //
            "org.hsql.Driver", //
            "jdbc:hsql://HOME/%s");

    public static final DBDialect PostgreSQL = new DBDialect("postgresql", //
            "org.hibernate.dialect.PostgreSQLDialect", //
            "org.postgresql.Driver", //
            "jdbc:postgresql://localhost:1063/%s");

    public static final DBDialect Oracle = new DBDialect("oracle",//
            "org.hibernate.dialect.OracleDialect", //
            "com.oracle.jdbc.Driver", //
            "jdbc:oracle://localhost/%s");

    public static final DBDialect MySQL = new DBDialect("mysql",//
            "org.hibernate.dialect.MySQLDialect", //
            "org.mysql.Driver", //
            "jdbc:mysql://localhost/%s");

}
