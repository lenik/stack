package com.bee32.plover.faces.el;

import com.bee32.plover.rtx.location.ILocationConstants;

public class CoreConstants
        implements ILocationConstants {

    static final CoreConstants INSTANCE = new CoreConstants();

    /**
     * pc:consts()
     */
    public static CoreConstants consts() {
        return INSTANCE;
    }

    /**
     * pc:version()
     */
    public static String version() {
        return "plover-facelets 0.3.2-SNAPSHOT";
    }

}
