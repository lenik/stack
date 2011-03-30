package com.bee32.plover.web.faces.el;

import com.bee32.plover.servlet.context.LocationContext;
import com.bee32.plover.servlet.context.LocationContextConstants;

public class CoreConstants
        implements LocationContextConstants {

    static final CoreConstants INSTANCE = new CoreConstants();

    public LocationContext getJAVASCRIPT() {
        return JAVASCRIPT;
    }

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
        return "plover-facelets 0.2.1-SNAPSHOT";
    }

}
