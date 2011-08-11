package com.bee32.plover.servlet.test;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.servlet.test.ServletTestCase.LocalSTL;

public abstract class OuterWac<T extends ServletTestCase>
        extends AbstractWebAppConfigurer {

    final Class<T> outerDeclType;

    public OuterWac() {
        outerDeclType = ClassUtil.infer1(getClass(), OuterWac.class, 0);
    }

    T getOuter(ServletTestLibrary stl) {
        if (!(stl instanceof LocalSTL))
            return null;
        LocalSTL localStl = (LocalSTL) stl;
        ServletTestCase outer = localStl.getOuter();

        if (!outerDeclType.isInstance(outer))
            return null;

        return outerDeclType.cast(outer);
    }

    protected void configureServer(ServletTestLibrary stl, T outer) {
    }

    protected void configureContext(ServletTestLibrary stl, T outer) {
    }

    protected void configureServlets(ServletTestLibrary stl, T outer) {
    }

    protected void onServerStartup(ServletTestLibrary stl, T outer) {
    }

    protected void onServerShutdown(ServletTestLibrary stl, T outer) {
    }

    @Override
    public final void configureServer(ServletTestLibrary stl) {
        T outer = getOuter(stl);
        if (outer != null)
            configureServer(stl, outer);
    }

    @Override
    public final void configureContext(ServletTestLibrary stl) {
        T outer = getOuter(stl);
        if (outer != null)
            configureContext(stl, outer);
    }

    @Override
    public final void configureServlets(ServletTestLibrary stl) {
        T outer = getOuter(stl);
        if (outer != null)
            configureServlets(stl, outer);
    }

    @Override
    public final void onServerStartup(ServletTestLibrary stl) {
        T outer = getOuter(stl);
        if (outer != null)
            onServerStartup(stl, outer);
    }

    @Override
    public final void onServerShutdown(ServletTestLibrary stl) {
        T outer = getOuter(stl);
        if (outer != null)
            onServerShutdown(stl, outer);
    }

}
