package com.bee32.sem.account;

import com.bee32.plover.arch.AppProfile;

public class SEMAccountPayProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMAccountPayMenu.class, SEMAccountPayMenu.ENABLED, true);
    }

}
