package com.bee32.zebra.oa.site;

import java.io.File;

import net.bodz.bas.c.m2.MavenPomDir;
import net.bodz.bas.err.IllegalConfigException;
import net.bodz.bas.html.servlet.PathDispatchServlet;
import net.bodz.bas.http.ctx.CurrentRequestContextTeller;
import net.bodz.bas.i18n.LocaleCtl;
import net.bodz.bas.site.BasicSiteServerConfig;
import net.bodz.uni.echo.config.ServletDescriptor;

public class OaSiteServerConfig
        extends BasicSiteServerConfig {

    protected ServletDescriptor imgLink;
    protected ServletDescriptor dispatching;

    public OaSiteServerConfig() {
        configEnv();
        configServlets();
    }

    protected void configEnv() {
        LocaleCtl localeCtl = LocaleCtl.LOCALE;
        localeCtl.setTeller(new CurrentRequestContextTeller());
    }

    protected void configServlets() {
        imgLink = addLocalLink("/img", "/mnt/istore/projects/design/img", 100);

        dispatching = addServlet(PathDispatchServlet.class, "/*");
        dispatching.setInitParam(PathDispatchServlet.ROOT_CLASS, OaSite.class.getName());
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
