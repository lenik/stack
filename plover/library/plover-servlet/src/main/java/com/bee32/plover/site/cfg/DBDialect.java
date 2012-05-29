package com.bee32.plover.site.cfg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

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

    static final Map<String, DBDialect> nameMap = new HashMap<String, DBDialect>();
    static final Map<String, DBDialect> valueMap = new HashMap<String, DBDialect>();

    public static Collection<DBDialect> values() {
        Collection<DBDialect> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static DBDialect forName(String altName) {
        DBDialect dialect = nameMap.get(altName);
        if (dialect == null)
            throw new NoSuchEnumException(DBDialect.class, altName);
        return dialect;
    }

    public static DBDialect forValue(String value) {
        if (value == null)
            return null;

        DBDialect dialect = valueMap.get(value);
        if (dialect == null)
            throw new NoSuchEnumException(DBDialect.class, value);

        return dialect;
    }

    public static final DBDialect H2 = new DBDialect("H2", //
            "org.hibernate.dialect.H2Dialect", //
            "org.h2.Driver", //
            "jdbc:h2://H2_HOME/%s;DB_CLOSE_ON_EXIT=FALSE");

    public static final DBDialect HSQL = new DBDialect("HSQL",//
            "org.hibernate.dialect.HSQLDialect", //
            "org.hsql.Driver", //
            "jdbc:hsql://HOME/%s");

    public static final DBDialect PostgreSQL = new DBDialect("PostgreSQL", //
            "org.hibernate.dialect.PostgreSQLDialect", //
            "org.postgresql.Driver", //
            "jdbc:postgresql://localhost:1063/%s");

    public static final DBDialect Oracle = new DBDialect("Oracle",//
            "org.hibernate.dialect.OracleDialect", //
            "com.oracle.jdbc.Driver", //
            "jdbc:oracle://localhost/%s");

    public static final DBDialect MySQL = new DBDialect("MySQL",//
            "org.hibernate.dialect.MySQLDialect", //
            "org.mysql.Driver", //
            "jdbc:mysql://localhost/%s");

}
