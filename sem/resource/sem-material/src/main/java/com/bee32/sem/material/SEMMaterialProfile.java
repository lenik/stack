package com.bee32.sem.material;

import com.bee32.plover.arch.AppProfile;

public class SEMMaterialProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMMaterialMenu.class, SEMMaterialMenu.ENABLED, true);
    }

}
