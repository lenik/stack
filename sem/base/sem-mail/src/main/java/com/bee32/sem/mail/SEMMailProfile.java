package com.bee32.sem.mail;

import com.bee32.plover.arch.AppProfile;

public class SEMMailProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMMailMenu.class, SEMMailMenu.ENABLED, true);
    }

}
