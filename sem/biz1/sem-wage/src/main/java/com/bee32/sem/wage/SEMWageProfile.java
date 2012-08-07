package com.bee32.sem.wage;

import com.bee32.plover.arch.AppProfile;

public class SEMWageProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMWageMenu.class, SEMWageMenu.ENABLED, true);
    }

}
