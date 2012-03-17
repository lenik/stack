package com.bee32.plover.servlet.test;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.free.Strings;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;

import org.eclipse.jetty.testing.HttpTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.rabbits.OverlappedBases;
import com.bee32.plover.servlet.rabbits.RabbitServer;
import com.bee32.plover.test.AssembledTestCase;

public abstract class ServletTestCase
        extends AssembledTestCase<ServletTestCase> {

    public static final boolean searchClassLocalResources = true;

    protected final Logger logger;

    protected ServletTestLibrary stl;
    protected String contextPath;

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

        int rand = new Random().nextInt(10000);
        contextPath = "/app" + rand;
    }

    public static ServletTestCase getInstanceFromContext(ServletContext servletContext) {
        return getInstanceFromContext(servletContext, ServletTestCase.class);
    }

    public static <T extends ServletTestCase> T getInstanceFromContext(ServletContext servletContext, Class<T> stcClass) {
        RabbitServer rabbitServer = RabbitServer.getInstanceFromContext(servletContext);
        if (!(rabbitServer instanceof LocalSTL))
            return null;
        LocalSTL localSTL = (LocalSTL) rabbitServer;

        ServletTestCase outer = localSTL.getOuter();
        if (!stcClass.isInstance(outer))
            return null;

        T instance = stcClass.cast(outer);
        return instance;
    }

    final class LocalSTL
            extends ServletTestLibrary {

        private LocalSTL() {
            super(ServletTestCase.this.getClass());
        }

        ServletTestCase getOuter() {
            return ServletTestCase.this;
        }

        @Override
        public void startup()
                throws Exception {
            super.startup();
            getOuter().onServerStartup();
        }

        @Override
        public void shutdown()
                throws Exception {
            getOuter().onServerShutdown();
            super.shutdown();
        }

        @Override
        protected String getLocalHost() {
            return getOuter().getLocalHost();
        }

        @Override
        public int getPort() {
            return getOuter().getPort();
        }

    }

    protected String getLocalHost() {
        return "localhost";
    }

    protected int getPort() {
        return 0;
    }

    protected String getContextPath() {
        return contextPath;
    }

    protected void configureContext() {
        stl.setContextPath(getContextPath());
    }

    protected void configureServlets() {
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

    public void initSession(HttpSessionEvent event) {
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