package com.bee32.plover.site;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.free.SystemProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SiteManager {

    static Logger logger = LoggerFactory.getLogger(SiteManager.class);

    static File SITE_HOME;
    static {
        String userHome = SystemProperties.getUserHome();
        File home = new File(userHome);
        SITE_HOME = new File(home, ".bee32/sites");
        SITE_HOME.mkdirs();
    }

    Map<String, SiteInstance> siteMap = new TreeMap<String, SiteInstance>();
    Map<String, String> aliasMap = new HashMap<String, String>();

    public Collection<SiteInstance> getSites() {
        return siteMap.values();
    }

    public SiteInstance getSiteOpt(String nameOrAlias) {
        String name = aliasMap.get(nameOrAlias);
        if (name == null)
            name = nameOrAlias;
        return siteMap.get(name);
    }

    public synchronized SiteInstance getOrCreateSite(String nameOrAlias)
            throws LoadSiteException {
        SiteInstance site = getSiteOpt(nameOrAlias);
        if (site == null) {
            site = loadSite(nameOrAlias);
            String name = site.getName();
            addSite(name, site);
        }
        return site;
    }

    public void addSite(String siteName, SiteInstance site) {
        siteMap.put(siteName, site);
        Set<String> aliases = site.getAliases();
        for (String alias : aliases) {
            // always overwrite existings.
            aliasMap.put(alias, siteName);
        }
    }

    protected SiteInstance loadSite(String siteName)
            throws LoadSiteException {
        File configFile = new File(SITE_HOME, siteName + SiteInstance.CONFIG_EXTENSION);
        SiteInstance site;
        try {
            site = new SiteInstance(configFile);
        } catch (Exception e) {
            throw new LoadSiteException("Failed to load site config " + configFile, e);
        }
        return site;
    }

    protected SiteInstance removeSite(String name) {
        SiteInstance site = siteMap.get(name);
        if (site != null)
            removeSite(site);
        return site;
    }

    protected void removeSite(SiteInstance site) {
        String name = site.getName();
        Set<String> aliases = site.getAliases();
        for (String alias : aliases)
            aliasMap.remove(alias);
        siteMap.remove(name);
    }

    public void scan(File siteHome) {
        for (File _file : siteHome.listFiles()) {
            if (!_file.isFile())
                continue;
            String fileName = _file.getName();
            if (!fileName.endsWith(SiteInstance.CONFIG_EXTENSION))
                continue;

            String siteName = fileName.substring(0, fileName.length() - SiteInstance.CONFIG_EXTENSION.length());
            try {
                SiteInstance site = getOrCreateSite(siteName);
                logger.info("Loaded site: " + site);
            } catch (LoadSiteException e) {
                logger.error("Failed to load site: " + _file, e);
            }
        }
    }

    private static final SiteManager instance;

    static {
        instance = new SiteManager();
        instance.scan(SITE_HOME);
    }

    public static SiteManager getInstance() {
        return instance;
    }

}
