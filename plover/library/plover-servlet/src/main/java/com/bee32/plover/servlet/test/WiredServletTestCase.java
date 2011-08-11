package com.bee32.plover.servlet.test;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.test.WiredTestCase;

@Import(WiredTestCase.class)
public abstract class WiredServletTestCase
        extends ServletTestCase {

    ApplicationContext applicationContext;

    protected WiredServletTestCase() {
    }

    /**
     * Happened before applicationContext is initialized.
     */
    protected boolean isSpringMVCEnabled() {
        return true;
    }

    protected void applicationInitialized(ApplicationContext applicationContext) {
    }

    protected void applicationDestroyed(ApplicationContext applicationContext) {
    }

}
