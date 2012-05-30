package com.bee32.sem.file;

import com.bee32.plover.arch.AppProfile;

public class SEMFileProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMFileMenu.class, SEMFileMenu.ENABLED, true);
    }

}
