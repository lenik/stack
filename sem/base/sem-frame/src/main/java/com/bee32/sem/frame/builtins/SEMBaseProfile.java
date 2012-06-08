package com.bee32.sem.frame.builtins;

import com.bee32.plover.arch.AppProfile;

public class SEMBaseProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMContextMenu.class, SEMContextMenu.ENABLED, true);
        setParameter(SEMFrameMenu.class, SEMFrameMenu.ENABLED, true);
    }

}
