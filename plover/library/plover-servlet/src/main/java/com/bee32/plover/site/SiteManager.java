package com.bee32.plover.site;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.free.IllegalUsageException;
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
    Map<String, String> wildHostAliasMap = new HashMap<String, String>();
    Map<String, String> hostAliasMap = new HashMap<String, String>();

    public Collection<SiteInstance> getSites() {
        return siteMap.values();
    }

    public SiteInstance getSite(String nameOrAlias) {
        HostSpec hostSpec = HostSpec.parse(nameOrAlias);
        String hostName = hostSpec.getHostName();
        String name = wildHostAliasMap.get(hostName);
        if (name == null)
            name = hostAliasMap.get(nameOrAlias);
        if (name == null)
            name = nameOrAlias;
        return siteMap.get(name);
    }

    public synchronized SiteInstance getOrCreateSite(String nameOrAlias)
            throws LoadSiteException {
        SiteInstance site = getSite(nameOrAlias);
        if (site == null) {
            String siteName = nameOrAlias;
            site = loadSite(siteName);
            addSite(site);
        }
        return site;
    }

    public void addSite(SiteInstance site) {
        String siteName = site.getName();

        // "Transactional"
        {
            if (siteMap.containsKey(siteName))
                throw new IllegalUsageException("Site is already existed: " + siteName);

            for (String alias : site.getAliases()) {
                HostSpec spec = HostSpec.parse(alias);
                if (spec.isWild()) {
                    if (wildHostAliasMap.containsKey(spec.getHostName()))
                        throw new IllegalUsageException(String.format("Wild alias %s:* for site %s is already used.", //
                                spec.getHostName(), siteName));
                } else {
                    if (hostAliasMap.containsKey(alias))
                        throw new IllegalUsageException(String.format("Alias %s for site %s is already used.", //
                                alias, siteName));
                }
            }
        }

        for (String alias : site.getAliases()) {
            HostSpec spec = HostSpec.parse(alias);
            String hostName = spec.getHostName();
            if (spec.isWild())
                wildHostAliasMap.put(hostName, siteName);
            else
                hostAliasMap.put(hostName, siteName);
        }

        siteMap.put(siteName, site);
    }

    /**
     * Create or load existing site.
     */
    public SiteInstance loadSite(String siteName)
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
        for (String alias : aliases) {
            HostSpec hostSpec = HostSpec.parse(alias);
            if (hostSpec.isWild())
                wildHostAliasMap.remove(hostSpec.getHostName());
            else
                hostAliasMap.remove(alias);
        }
        siteMap.remove(name);
    }

    protected void deleteSite(SiteInstance site) {
        removeSite(site);
        site.configFile.delete();
    }

    public void scanAndLoadSites(File siteHome) {
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

    /**
     * Save all config attributes to corresponding config files.
     */
    public void saveAllConfigs()
            throws IOException {
        for (SiteInstance site : getSites())
            site.saveConfig();
    }

    private static final SiteManager instance;

    static {
        instance = new SiteManager();
        instance.scanAndLoadSites(SITE_HOME);
    }

    public static SiteManager getInstance() {
        return instance;
    }

}
