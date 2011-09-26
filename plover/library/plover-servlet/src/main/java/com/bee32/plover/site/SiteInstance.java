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

    File configFile;
    FormatProperties properties;
    boolean dirty;

    Map<String, Object> attributes = new HashMap<String, Object>();

    public SiteInstance(File file)
            throws IOException, ParseException {
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

    public String getName() {
        return getProperty(NAME_KEY);
    }

    public void setName(String name) {
        setProperty(NAME_KEY, name);
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

    public synchronized void save()
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

}
