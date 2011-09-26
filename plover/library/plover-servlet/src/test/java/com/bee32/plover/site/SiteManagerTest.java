package com.bee32.plover.site;

import org.junit.Assert;

public class SiteManagerTest
        extends Assert {

    public static void main(String[] args)
            throws Exception {
        SiteManager manager = SiteManager.getInstance();
        for (SiteInstance site : manager.getSites()) {
            System.out.println(site.getName());
        }

        SiteInstance d1 = manager.getSite("localhost");
        SiteInstance d2 = manager.getSite("localhost:80");
        SiteInstance d3 = manager.getSite("localhost:123");

        SiteInstance lenny1 = manager.getSite("lenny");
        SiteInstance lenny2 = manager.getSite("lenny:234");
lenny1.setProperty("new", "value");
        manager.saveAll();
        System.out.println();
    }

}
