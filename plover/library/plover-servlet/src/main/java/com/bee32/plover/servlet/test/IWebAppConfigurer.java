package com.bee32.plover.servlet.test;

import com.bee32.plover.arch.util.IOrdered;

public interface IWebAppConfigurer
        extends IOrdered {

    void configureServer(ServletTestLibrary stl);

    void configureContext(ServletTestLibrary stl);

    void configureServlets(ServletTestLibrary stl);

    void onServerStartup(ServletTestLibrary stl);

    void onServerShutdown(ServletTestLibrary stl);

}
