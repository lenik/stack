package com.bee32.zebra.sys;

import net.bodz.bas.meta.build.MainVersion;
import net.bodz.bas.t.project.AbstractJazzProject;

@MainVersion({ 3, 0 })
public class ZebraProject
        extends AbstractJazzProject {

    private static ZebraProject instance = new ZebraProject();

    public static ZebraProject getInstance() {
        return instance;
    }

}
