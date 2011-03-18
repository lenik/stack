package com.bee32.plover.servlet.test;

import com.bee32.plover.test.AssembledTestCase;

public abstract class ServletTestCase
        extends AssembledTestCase {

    protected final ServletTesterLibrary stl;

    public ServletTestCase() {
        install(stl = new LocalSTL());
    }

    class LocalSTL
            extends ServletTesterLibrary {

        @Override
        protected void configureServlets()
                throws Exception {
            super.configureServlets();

            ServletTestCase.this.configureBuiltinServlets();
            ServletTestCase.this.configureServlets();
        }

    }

    void configureBuiltinServlets() {
    }

    protected abstract void configureServlets();

}