package com.bee32.plover.site;

import java.util.Map;
import java.util.TreeMap;

public class SiteManager {

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
        SiteInstance site = new SiteInstance(siteName);
        return site;
    }

    private static SiteManager instance = new SiteManager();

    public static SiteManager getInstance() {
        return instance;
    }

}
