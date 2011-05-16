package com.bee32.plover.servlet.test;

import java.io.IOException;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.Strings;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import org.mortbay.jetty.testing.HttpTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.test.AssembledTestCase;

public abstract class ServletTestCase
        extends AssembledTestCase<ServletTestCase>
        implements ServletContextListener, ISessionMonitor {

    public static final boolean searchClassLocalResources = true;

    protected final Logger logger;

    protected ServletTestLibrary stl;

    private boolean checkContext;;
    private boolean checkBuiltinServlets;
    private boolean checkFallbackServlets;

    /**
     * This ServletContext is read from in-memory server.
     */
    protected ServletContext servletContext;

    public ServletTestCase() {

        logger = LoggerFactory.getLogger(getClass());

        install(stl = new LocalSTL());

        Class<?> head = getClass();

        if (searchClassLocalResources) {
            String headDir = head.getPackage().getName().replace('.', '/');
            String headBase = head.getSimpleName();
            headBase = Strings.lcfirst(headBase);
            String headPrefix = headDir + "/" + headBase;
            OverlappedBases.add(headPrefix);
        }

        Class<?> chain = head;
        while (chain != null) {
            OverlappedBases.add(chain);
            chain = chain.getSuperclass();
        }

        lastInstance = this;
    }

    private final class LocalSTL
            extends ServletTestLibrary {

        private LocalSTL() {
            super(ServletTestCase.this.getClass());
        }

        @Override
        public void startup()
                throws Exception {
            super.startup();
            ServletTestCase.this.onServerStartup();
        }

        @Override
        public void shutdown()
                throws Exception {
            ServletTestCase.this.onServerShutdown();
            super.shutdown();
        }

        @Override
        public int getPort() {
            return ServletTestCase.this.getPort();
        }

        @Override
        protected void configureContext() {
            super.configureContext();

            ServletTestCase.this.configureContext();

            if (!checkContext)
                throw new IllegalUsageException("configureContext is overrided.");
        }

        @Override
        protected void configureBuiltinServlets() {
            super.configureBuiltinServlets();

            ServletTestCase.this.configureBuiltinServlets();

            if (!checkBuiltinServlets)
                throw new IllegalUsageException("configureBuiltinServlets is overrided.");
        }

        @Override
        protected void configureServlets() {
            super.configureServlets();

            ServletTestCase.this.configureServlets();
        }

        @Override
        protected void configureFallbackServlets() {
            ServletTestCase.this.configureFallbackServlets();

            super.configureFallbackServlets();

            if (!checkFallbackServlets)
                throw new IllegalUsageException("configureFallbackServlets is overrided.");
        }

    }

    protected int getPort() {
        return 0;
    }

    protected void configureContext() {
        checkContext = true;
        stl.addEventListener(this);
    }

    protected void configureBuiltinServlets() {
        checkBuiltinServlets = true;

        stl.addFilter(SessionMonitorFilter.class, "/*", 0);
    }

    protected void configureFallbackServlets() {
        checkFallbackServlets = true;
    }

    protected abstract void configureServlets();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        logger.info("Test servlet context is initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Test servlet context is destroyed.");
        servletContext = null;
    }

    protected void onServerStartup() {
    }

    protected void onServerShutdown() {
    }

    public HttpTester httpGet(String uriOrPath)
            throws Exception {
        return stl.httpGet(uriOrPath);
    }

    public HttpTester httpPost(String uri, String content, Map<String, String> map)
            throws Exception {
        return stl.httpPost(uri, content, map);
    }

    @Override
    public void initSession(HttpSession session) {
    }

    public void mainLoop()
            throws IOException {
        stl.mainLoop();
    }

    public void mainLoop(String location)
            throws IOException {
        stl.mainLoop(location);
    }

    /**
     * This function bypass any state of current object.
     *
     * Instead, it creates another instance of the same class.
     */
    public void browseAndWait()
            throws IOException {
        unit()._browseAndWait();
    }

    /**
     * This function bypass any state of current object.
     *
     * Instead, it creates another instance of the same class.
     */
    public void browseAndWait(String location)
            throws IOException {
        unit()._browseAndWait(location);
    }

    public void _browseAndWait()
            throws IOException {
        stl.browseAndWait();
    }

    public void _browseAndWait(String location)
            throws IOException {
        stl.browseAndWait(location);
    }

    static ServletTestCase lastInstance;

    public static ServletTestCase getLastInstance() {
        return lastInstance;
    }

}