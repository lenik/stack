package com.bee32.plover.servlet.mvc;

import java.util.Random;

public class MVCConfig {

    // public static String defultContextPath = "";

    public static final String MVC_NONCE = "nonce";

    // public static String PREFIX;
    public static final String SUFFIX;

    public static boolean nonceEnabled = false;;
    public static final boolean nonceInUse;

    static {
        // PREFIX = "/spring/";

        String nonce = System.getProperty(MVC_NONCE);
        if (nonce == null) {
            int rand = new Random().nextInt();
            nonce = Integer.toHexString(rand);
        } else if (nonce.isEmpty())
            nonce = null;

        if (!nonceEnabled)
            nonce = null;
        else {
            String runmode = System.getProperty("runmode");
            if ("production".equals(runmode))
                nonce = null;
        }

        String _suffix = ".do";

        if (nonce == null)
            nonceInUse = false;
        else {
            nonceInUse = true;
            _suffix += "_" + nonce;
        }

        SUFFIX = _suffix;
    }

}
