package com.bee32.plover.site;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.free.IFile;
import javax.free.JavaioFile;
import javax.free.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteInstance {

    static Logger logger = LoggerFactory.getLogger(SiteInstance.class);

    public static final String CONFIG_EXTENSION = ".sif";

    public static final String NAME_KEY = "name";
    public static final String ALIASES_KEY = "aliases";
    public static final String LABEL_KEY = "label";
    public static final String DESCRIPTION_KEY = "description";

    public static final String DB_DIALECT_KEY = "db.dialect";
    public static final String DB_URL_FORMAT_KEY = "db.url";
    public static final String DB_NAME_KEY = "db.name";
    public static final String DB_USER_KEY = "db.user";
    public static final String DB_PASS_KEY = "db.pass";
    public static final String AUTODDL_KEY = "autoddl";
    public static final String SAMPLES_KEY = "samples";

    File configFile;
    FormatProperties properties;
    boolean dirty;

    Map<String, Object> attributes = new HashMap<String, Object>();

    public SiteInstance() {
        configFile = null;
        properties = new FormatProperties();

        setDbDialect(DBDialect.PostgreSQL);
        setProperty(DB_USER_KEY, "semsadmin");
        setProperty(DB_PASS_KEY, "MxDkUWl1");
        setAutoDDL(DBAutoDDL.Update);
        setSamples(SamplesSelection.STANDARD);
    }

    public SiteInstance(File file)
            throws IOException, ParseException {
        this();

        if (file == null)
            throw new NullPointerException("file");
        configFile = file;

        String name = file.getName();
        if (name.endsWith(CONFIG_EXTENSION))
            name = name.substring(0, name.length() - CONFIG_EXTENSION.length());
        properties.setProperty("name", name);

        loadConfig();
    }

    SiteInstance(FormatProperties properties, File configFile) {
        if (properties == null)
            throw new NullPointerException("properties");
        if (configFile == null)
            throw new NullPointerException("configFile");
        this.properties = properties;
        this.configFile = configFile;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        dirty = true;
    }

    public int getIntProperty(String key, int defaultValue) {
        Integer property = getIntProperty(key);
        if (property == null)
            return defaultValue;
        else
            return property;
    }

    public Integer getIntProperty(String key) {
        String property = properties.getProperty(key);
        if (property == null)
            return null;
        Integer _int = Integer.valueOf(property);
        return _int;
    }

    public void setIntProperty(String key, Integer value) {
        String property;
        if (value == null)
            property = null;
        else
            property = String.valueOf(value);
        properties.setProperty(key, property);
    }

    public Boolean getBooleanProperty(String key) {
        String property = properties.getProperty(key);
        if (properties == null)
            return null;
        if ("true".equals(property) || "1".equals(property))
            return true;
        else
            return false;
    }

    public void setBooleanProperty(String key, Boolean value) {
        String property;
        if (value == null)
            property = null;
        else
            property = String.valueOf(value);
        properties.setProperty(key, property);
    }

    public boolean containsAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object getAttribute(String attributeName) {
        if (attributeName == null)
            throw new NullPointerException("attributeName");
        return attributes.get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeName == null)
            throw new NullPointerException("attributeName");
        attributes.put(attributeName, attributeValue);
    }

    public Object removeAttribute(String attributeName) {
        return attributes.remove(attributeName);
    }

    public void reloadConfig()
            throws IOException, ParseException {
        loadConfig();
    }

    void loadConfig()
            throws IOException, ParseException {
        if (configFile.exists()) {
            IFile _file = new JavaioFile(configFile);
            properties.parse(_file);
        }
    }

    /**
     * Save attributes to the config file.
     */
    public synchronized void saveConfig()
            throws IOException {
        if (dirty) {
            JavaioFile _file = new JavaioFile(configFile);
            _file.forWrite().write(properties.toString());
            dirty = false;
        }
    }

    public String getName() {
        return getProperty(NAME_KEY);
    }

    public void setName(String name) {
        setProperty(NAME_KEY, name);

        // Set default db name to site_db.
        String dbName = getDbName();
        if (dbName == null) {
            String siteName = getName();
            if (siteName != null) {
                dbName = siteName + "_db";
                setDbName(dbName);
            }
        }
    }

    public String getLabel() {
        return getProperty(LABEL_KEY);
    }

    public void setLabel(String label) {
        setProperty(LABEL_KEY, label);
    }

    public String getDescription() {
        return getProperty(DESCRIPTION_KEY);
    }

    public void setDescription(String description) {
        setProperty(DESCRIPTION_KEY, description);
    }

    public Set<String> getAliases() {
        Set<String> aliases = new LinkedHashSet<String>();
        String _aliases = getProperty(ALIASES_KEY);
        if (_aliases != null) {
            StringTokenizer tokens = new StringTokenizer(_aliases, ",");
            while (tokens.hasMoreTokens()) {
                String _alias = tokens.nextToken().trim();
                aliases.add(_alias);
            }
        }
        return aliases;
    }

    public void setAliases(Set<String> aliases) {
        StringBuilder sb = new StringBuilder(aliases.size() * 20);
        int index = 0;
        for (String alias : aliases) {
            if (index++ > 0)
                sb.append(", ");
            sb.append(alias);
        }
        String _aliases = sb.toString();
        setProperty(ALIASES_KEY, _aliases);
    }

    public DBDialect getDbDialect() {
        String _dialect = getProperty(DB_DIALECT_KEY);
        DBDialect dialect = DBDialect.valueOf(_dialect);
        return dialect;
    }

    public void setDbDialect(DBDialect dialect) {
        String _dialect = dialect.name();
        setProperty(DB_DIALECT_KEY, _dialect);
        setProperty(DB_URL_FORMAT_KEY, dialect.getUrlFormat());
    }

    public String getDbUrlFormat() {
        String format = getProperty(DB_URL_FORMAT_KEY);
        return format;
    }

    public void setDbUrlFormat(String urlFormat) {
        setProperty(DB_URL_FORMAT_KEY, urlFormat);
    }

    public String getDbName() {
        String dbName = getProperty(DB_NAME_KEY);
        return dbName;
    }

    public void setDbName(String dbName) {
        setProperty(DB_NAME_KEY, dbName);
    }

    public String getDbUrl() {
        String urlFormat = getDbUrlFormat();
        String dbName = getDbName();
        String url = String.format(urlFormat, dbName);
        return url;
    }

    public String getDbUser() {
        String dbUser = getProperty(DB_USER_KEY);
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        setProperty(DB_USER_KEY, dbUser);
    }

    public String getDbPass() {
        String dbPass = getProperty(DB_PASS_KEY);
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        setProperty(DB_PASS_KEY, dbPass);
    }

    public DBAutoDDL getAutoDDL() {
        String _autoddl = getProperty(AUTODDL_KEY);
        DBAutoDDL autoddl = DBAutoDDL.valueOf(_autoddl);
        return autoddl;
    }

    public void setAutoDDL(DBAutoDDL autoddl) {
        String _autoddl = autoddl.name();
        setProperty(AUTODDL_KEY, _autoddl);
    }

    public SamplesSelection getSamples() {
        String property = getProperty(SAMPLES_KEY);
        SamplesSelection samples = SamplesSelection.valueOf(property);
        return samples;
    }

    public void setSamples(SamplesSelection samples) {
        if (samples == null)
            throw new NullPointerException("samples");
        String property = samples.name();
        setProperty(SAMPLES_KEY, property);
    }

    @Override
    public String toString() {
        return getName();
    }

}
