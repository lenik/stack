package com.bee32.plover.servlet.test;

import java.io.IOException;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.Strings;

import org.mortbay.jetty.testing.HttpTester;

import com.bee32.plover.test.AssembledTestCase;

public abstract class ServletTestCase
        extends AssembledTestCase {

    public static final boolean searchClassLocalResources = true;

    protected ServletTestLibrary stl;

    private boolean checkContext;;
    private boolean checkBuiltinServlets;
    private boolean checkFallbackServlets;

    public ServletTestCase() {
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
    }

    final class LocalSTL
            extends ServletTestLibrary {

        private LocalSTL() {
            super(ServletTestCase.this.getClass());
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

    protected void configureContext() {
        checkContext = true;
    }

    protected void configureBuiltinServlets() {
        checkBuiltinServlets = true;
    }

    protected void configureFallbackServlets() {
        checkFallbackServlets = true;
    }

    protected abstract void configureServlets();

    public HttpTester httpGet(String uriOrPath)
            throws Exception {
        return stl.httpGet(uriOrPath);
    }

    public HttpTester httpPost(String uri, String content, Map<String, String> map)
            throws Exception {
        return stl.httpPost(uri, content, map);
    }

    protected void browseAndWait()
            throws IOException {
        stl.browseAndWait();
    }

    protected void browseAndWait(String location)
            throws IOException {
        stl.browseAndWait(location);
    }

}