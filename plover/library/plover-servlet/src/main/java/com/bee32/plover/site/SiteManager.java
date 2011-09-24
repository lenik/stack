package com.bee32.plover.site;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import javax.free.IFile;
import javax.free.JavaioFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteManager {

    static Logger logger = LoggerFactory.getLogger(SiteManager.class);

    Map<String, SiteInstance> sites = new TreeMap<String, SiteInstance>();

    public SiteInstance getSiteOpt(String name) {
        return sites.get(name);
    }

    public synchronized SiteInstance getOrCreateSite(String name) {
        SiteInstance site = getSiteOpt(name);
        if (site == null) {
            site = createDefaultSite(name);
            setSite(name, site);
        }
        return site;
    }

    public void setSite(String name, SiteInstance site) {
        sites.put(name, site);
    }

    protected SiteInstance createDefaultSite(String siteName) {
        new SiteConfig(properties, configFile);
        SiteInstance site = new SiteInstance(config);
        return site;
    }

    static final String SITE_EXTENSION = ".sif";

    public void scanSites(File siteHome) {
        for (File _file : siteHome.listFiles()) {
            if (!_file.isFile())
                continue;
            String fileName = _file.getName();
            if (!fileName.endsWith(SITE_EXTENSION))
                continue;

            FormatProperties properties = new FormatProperties();
            String defaultName = fileName.substring(0, fileName.length() - SITE_EXTENSION.length());
            properties.setProperty("name", defaultName);

            IFile file = new JavaioFile(_file);
            try {
                properties.parse(file);
            } catch (Exception e) {
                logger.error("Failed to load site config " + _file, e);
            }

            SiteConfig siteConfig = new SiteConfig(properties, _file);
            sites.put(siteConfig.getName(), siteConfig);
        }
    }

    private static final SiteManager instance;

    static {
        instance = new SiteManager();
        // instance.scan();
    }

    public static SiteManager getInstance() {
        return instance;
    }

}
