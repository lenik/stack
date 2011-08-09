package com.bee32.plover.servlet.test;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.OrderComparator;

public class WebAppConfigure
        implements IWebAppConfigurer {

    static Logger logger = LoggerFactory.getLogger(WebAppConfigure.class);

    TreeSet<IWebAppConfigurer> configurers;

    public WebAppConfigure() {
        configurers = new TreeSet<IWebAppConfigurer>(OrderComparator.INSTANCE);
        for (IWebAppConfigurer c : ServiceLoader.load(IWebAppConfigurer.class))
            configurers.add(c);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void configureServer(ServletTestLibrary stl) {
        for (IWebAppConfigurer c : configurers)
            c.configureServer(stl);
    }

    @Override
    public void configureContext(ServletTestLibrary stl) {
        for (IWebAppConfigurer c : configurers) {
            logger.debug("Configure servlet context from " + c.getClass());
            c.configureContext(stl);
        }
    }

    @Override
    public void configureServlets(ServletTestLibrary stl) {
        for (IWebAppConfigurer c : configurers) {
            logger.debug("Configure servlets from " + c.getClass());
            c.configureServlets(stl);
        }
    }

    @Override
    public void onServerStartup(ServletTestLibrary stl) {
        Iterator<IWebAppConfigurer> iter = configurers.iterator();
        while (iter.hasNext())
            iter.next().onServerShutdown(stl);
    }

    @Override
    public void onServerShutdown(ServletTestLibrary stl) {
        Iterator<IWebAppConfigurer> iter = configurers.descendingIterator();
        while (iter.hasNext())
            iter.next().onServerShutdown(stl);
    }

    static WebAppConfigure INSTANCE = new WebAppConfigure();

    public static WebAppConfigure getInstance() {
        return INSTANCE;
    }

    // Dump all
    public static void main(String[] args) {
        System.out.println("WAC Dump: ");
        for (IWebAppConfigurer c : getInstance().configurers) {
            System.out.println("  WAC Service: " + c);
        }
    }

}
