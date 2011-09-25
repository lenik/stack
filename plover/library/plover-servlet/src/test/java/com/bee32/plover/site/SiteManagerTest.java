package com.bee32.plover.site;

import org.junit.Assert;

public class SiteManagerTest
        extends Assert {

    public static void main(String[] args) {
        SiteManager manager = SiteManager.getInstance();
        for (SiteInstance site : manager.getSites()) {
            System.out.println(site.getName());
        }
    }

}
