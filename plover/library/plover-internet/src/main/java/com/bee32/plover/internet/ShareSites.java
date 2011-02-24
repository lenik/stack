package com.bee32.plover.internet;

import java.util.Collection;
import java.util.ServiceLoader;

import com.bee32.plover.model.qualifier.Group;

public class ShareSites {

    public static Collection<IShareSite> getShareSites() {
        for (IShareSite site : ServiceLoader.load(IShareSite.class)) {
            Group group;

        }
    }

}
