package com.bee32.sem.chance;

import com.bee32.plover.arch.AppProfile;

public class SEMChanceProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMChanceMenu.class, "a", "b");
    }

}
