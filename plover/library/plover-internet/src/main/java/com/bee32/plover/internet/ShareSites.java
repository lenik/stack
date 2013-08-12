package com.bee32.plover.internet;

import java.util.Collection;
import java.util.ServiceLoader;

import org.apache.commons.collections15.list.TreeList;

/**
 * 共享站点
 *
 * <p lang="en">
 * Share Sites
 */
public class ShareSites {

    static TreeList<IShareSite> shareSites;

    public static synchronized Collection<IShareSite> getShareSites() {
        if (shareSites == null) {
            shareSites = new TreeList<IShareSite>();

            for (IShareSite site : ServiceLoader.load(IShareSite.class)) {
                // Group group = site.getQualifier(Group.class);
                shareSites.add(site);
            }
        }
        return shareSites;
    }

}
