package com.bee32.plover.servlet.test;

import java.io.IOException;

import com.bee32.plover.test.AssembledTestCase;

public abstract class ServletTestCase
        extends AssembledTestCase {

    protected ServletTesterLibrary stl;

    public ServletTestCase() {
        install(stl = new LocalSTL());

        Class<?> chain = getClass();
        while (chain != null) {
            OverlappedBases.add(chain);
            chain = chain.getSuperclass();
        }
    }

    public ServletTestCase(ServletTesterLibrary stl) {
        if (stl != null)
            install(this.stl = stl);
    }

    class LocalSTL
            extends ServletTesterLibrary {

        private LocalSTL() {
            super(ServletTestCase.this.getClass());
        }

        @Override
        protected void configureBuiltinServlets()
                throws Exception {
            super.configureBuiltinServlets();
            ServletTestCase.this.configureBuiltinServlets();
        }

        @Override
        protected void configureServlets()
                throws Exception {
            super.configureServlets();
            ServletTestCase.this.configureServlets();
        }

        @Override
        protected void configureFallbackServlets() {
            ServletTestCase.this.configureFallbackServlets();
            super.configureFallbackServlets();
        }

    }

    protected void configureBuiltinServlets() {
    }

    protected abstract void configureServlets();

    protected void configureFallbackServlets() {
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