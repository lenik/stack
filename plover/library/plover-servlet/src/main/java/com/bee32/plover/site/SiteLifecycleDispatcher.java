package com.bee32.plover.site;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.OrderComparator;

public class SiteLifecycleDispatcher {

    static Logger logger = LoggerFactory.getLogger(SiteLifecycleDispatcher.class);

    static final Set<ISiteLifecycleListener> listeners;
    static {
        listeners = new TreeSet<ISiteLifecycleListener>(OrderComparator.INSTANCE);
        for (ISiteLifecycleListener listener : ServiceLoader.load(ISiteLifecycleListener.class)) {
            listeners.add(listener);
        }
    }

    public static void addSiteLifecycleListener(ISiteLifecycleListener listener) {
        listeners.add(listener);
    }

    public static void removeSiteLifecycleListener(ISiteLifecycleListener listener) {
        listeners.remove(listener);
    }

    public static void createSite(SiteInstance site) {
        for (ISiteLifecycleListener listener : listeners)
            listener.createSite(site);
    }

    public static void destroySite(SiteInstance site) {
        for (ISiteLifecycleListener listener : listeners)
            listener.destroySite(site);
    }

    public static void addSite(SiteInstance site) {
        for (ISiteLifecycleListener listener : listeners)
            listener.addSite(site);
    }

    public static void removeSite(SiteInstance site) {
        for (ISiteLifecycleListener listener : listeners)
            listener.removeSite(site);
    }

    public static void startSite(SiteInstance site) {
        for (ISiteLifecycleListener listener : listeners)
            listener.startSite(site);
    }

    public static void stopSite(SiteInstance site) {
        for (ISiteLifecycleListener listener : listeners)
            listener.stopSite(site);
    }

}
