package com.bee32.sem.account;

import com.bee32.plover.arch.AppProfile;

public class SEMAccountReceProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMAccountReceMenu.class, SEMAccountReceMenu.ENABLED, true);
    }

}
