package com.bee32.plover.site;

import org.junit.Assert;

public class SiteManagerTest
        extends Assert {

    // @Test
    public void testSaveAllConfig()
            throws Exception {
        SiteManager manager = SiteManager.getInstance();
        for (SiteInstance site : manager.getSites()) {
            System.out.println(site.getName());
        }

        SiteInstance newsite = manager.getOrLoadSite("new");
        newsite.setProperty("hello", "world");

        manager.saveAllConfigs();
    }

}
