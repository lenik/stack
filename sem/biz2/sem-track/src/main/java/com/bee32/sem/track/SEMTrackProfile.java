package com.bee32.sem.track;

import com.bee32.plover.arch.AppProfile;

public class SEMTrackProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMTrackMenu.class, SEMTrackMenu.ENABLED, true);
    }

}
