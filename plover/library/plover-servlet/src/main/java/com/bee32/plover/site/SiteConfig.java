package com.bee32.plover.site;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteConfig {

    static Logger logger = LoggerFactory.getLogger(SiteConfig.class);

    public static final String NAME_KEY = "name";
    public static final String ALIASES_KEY = "aliases";

    FormatProperties properties;
    File configFile;

    public SiteConfig(File configFile) {
        this.properties = new FormatProperties();

    }

    public SiteConfig(FormatProperties properties, File configFile) {
        if (properties == null)
            throw new NullPointerException("properties");
        this.properties = properties;
        this.configFile = configFile;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public String getName() {
        return properties.getProperty(NAME_KEY);
    }

    public void setName(String name) {
        properties.setProperty(NAME_KEY, name);
    }

    public Set<String> getAliases() {
        Set<String> aliases = new LinkedHashSet<String>();
        String _aliases = properties.getProperty(ALIASES_KEY);
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
        properties.setProperty(ALIASES_KEY, _aliases);
    }

}
