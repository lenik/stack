package com.bee32.plover.servlet.test;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.test.WiredTestCase;

/**
 * See:
 * <ul>
 * <li>SpringWac -> context.contextConfigLocation = ContextConfigurationUtil() 用语配置路径
 * <li>RootContextLoaderScl -> ContextLoaderListener 用于设置 context.attr["ROOT...“]
 * </ul>
 */
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
