package com.bee32.zebra.oa.site;

import java.io.File;

import net.bodz.bas.c.m2.MavenPomDir;
import net.bodz.bas.err.IllegalConfigException;
import net.bodz.bas.html.servlet.PathDispatchServlet;
import net.bodz.bas.http.ctx.CurrentRequestContextTeller;
import net.bodz.bas.http.servlet.ClassResourceAccessorServlet;
import net.bodz.bas.http.servlet.FileAccessorServlet;
import net.bodz.bas.i18n.LocaleCtl;
import net.bodz.uni.echo._default.DefaultServerConfig;
import net.bodz.uni.echo.config.ServletDescriptor;

public class OaSiteServerConfig
        extends DefaultServerConfig {

    public OaSiteServerConfig() {
        configEnv();
        configServlets();
    }

    protected void configEnv() {
        LocaleCtl localeCtl = LocaleCtl.LOCALE;
        localeCtl.setTeller(new CurrentRequestContextTeller());
    }

    protected void configServlets() {
        ServletDescriptor webjarsLink = addServlet(ClassResourceAccessorServlet.class, "/webjars/*");
        webjarsLink.setInitParam(FileAccessorServlet.ATTRIBUTE_PATH, //
                "META-INF/resources/webjars");

        ServletDescriptor fontsLink = addServlet(FileAccessorServlet.class, "/fonts/*");
        fontsLink.setInitParam(FileAccessorServlet.ATTRIBUTE_PATH, //
                "/usr/share/fonts");

        ServletDescriptor javascriptLink = addServlet(FileAccessorServlet.class, "/js/*");
        javascriptLink.setInitParam(FileAccessorServlet.ATTRIBUTE_PATH, //
                "/usr/share/javascript");

        ServletDescriptor imgLink = addServlet(FileAccessorServlet.class, "/img/*");
        imgLink.setInitParam(FileAccessorServlet.ATTRIBUTE_PATH, //
                "/mnt/istore/projects/design/img");

        PathDispatchServlet.startObject = new OaSite();
        addServlet(PathDispatchServlet.class, "/*");
    }

    public static File getStackDirFromSrc() {
        File pomDir = MavenPomDir.fromClass(OaSite.class).getBaseDir();
        File layer1Dir = pomDir.getParentFile();
        File zebraDir = layer1Dir.getParentFile();
        File stackDir = zebraDir.getParentFile();
        if (stackDir == null || !stackDir.exists())
            throw new IllegalConfigException("Can't find base dir of the stack.");
        return stackDir;
    }

}
