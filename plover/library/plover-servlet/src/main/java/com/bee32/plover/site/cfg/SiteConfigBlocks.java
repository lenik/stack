package com.bee32.plover.site.cfg;

import java.util.ServiceLoader;


public class SiteConfigBlocks {

    public static Iterable<ISiteConfigBlock> getExtensions() {
        return ServiceLoader.load(ISiteConfigBlock.class);
    }

}
