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

    public static final String DB_HOST_KEY = "db.host";
    public static final String DB_PORT_KEY = "db.port";
    public static final String DB_NAME_KEY = "db.name";
    public static final String DB_USER_KEY = "db.user";
    public static final String DB_PASS_KEY = "db.pass";
    public static final String SAMPLES_KEY = "scamples";

    static final String DEFAULT_DB_HOST = "localhost";
    static final int DEFAULT_DB_PORT = 1063;
    static final String DEFAULT_DB_USER = "semsadmin";
    static final String DEFAULT_DB_PASS = "MxDkUWl1";

    File configFile;
    FormatProperties properties;
    boolean dirty;

    Map<String, Object> attributes = new HashMap<String, Object>();

    public SiteInstance() {
        configFile = null;
        properties = new FormatProperties();
    }

    public SiteInstance(File file)
            throws IOException, ParseException {
        if (file == null)
            throw new NullPointerException("file");

        configFile = file;
        properties = new FormatProperties();

        String name = file.getName();
        if (name.endsWith(CONFIG_EXTENSION))
            name = name.substring(0, name.length() - CONFIG_EXTENSION.length());
        properties.setProperty("name", name);

        if (file.exists()) {
            IFile _file = new JavaioFile(file);
            properties.parse(_file);
        }
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

    @Override
    public String toString() {
        return getName();
    }

    public String getName() {
        return getProperty(NAME_KEY);
    }

    public void setName(String name) {
        setProperty(NAME_KEY, name);
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

    public String getDbHost() {
        String dbHost = getProperty(DB_HOST_KEY);
        if (dbHost == null)
            dbHost = DEFAULT_DB_HOST;
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        setProperty(DB_HOST_KEY, dbHost);
    }

    public int getDbPort() {
        return getIntProperty(DB_PORT_KEY, DEFAULT_DB_PORT);
    }

    public void setDbPort(int dbPort) {
        setIntProperty(DB_PORT_KEY, dbPort);
    }

    public String getDbName() {
        String dbName = getProperty(DB_NAME_KEY);
        if (dbName == null)
            dbName = getName() + "_db";
        return dbName;
    }

    public void setDbName(String dbName) {
        setProperty(DB_NAME_KEY, dbName);
    }

    public String getDbUser() {
        String dbUser = getProperty(DB_USER_KEY);
        if (dbUser == null)
            dbUser = DEFAULT_DB_USER;
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        setProperty(DB_USER_KEY, dbUser);
    }

    public String getDbPass() {
        String dbPass = getProperty(DB_PASS_KEY);
        if (dbPass == null)
            dbPass = DEFAULT_DB_PASS;
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        setProperty(DB_PASS_KEY, dbPass);
    }

    public SamplesSelection getSamples() {
        String property = getProperty(SAMPLES_KEY);
        SamplesSelection samples = null;
        if (property != null) {
            property = property.trim();
            if (!property.isEmpty())
                samples = SamplesSelection.valueOf(property);
        } else
            samples = SamplesSelection.STANDARD;
        return samples;
    }

    public void setSamples(SamplesSelection samples) {
        String property = samples == null ? "" : samples.name();
        setProperty(SAMPLES_KEY, property);
    }

}
