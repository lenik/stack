package com.bee32.plover.servlet.mvc;

import java.util.Random;

public class SpringMVCConfig {

    public static final String MVC_NONCE = "nonce";

    public static final boolean nonceEnabled;
    public static final String suffix;

    static {
        String nonce = System.getProperty(MVC_NONCE);
        if (nonce == null) {
            int rand = new Random().nextInt();
            nonce = Integer.toHexString(rand);
        } else if (nonce.isEmpty())
            nonce = null;

        String runmode = System.getProperty("runmode");
        if ("production".equals(runmode))
            nonce = null;

        String _suffix = ".do";

        if (nonce == null)
            nonceEnabled = false;
        else {
            nonceEnabled = true;
            _suffix += "_" + nonce;
        }

        suffix = _suffix;
    }

}
