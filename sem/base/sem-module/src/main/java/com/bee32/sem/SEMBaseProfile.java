package com.bee32.sem;

import com.bee32.plover.arch.AppProfile;
import com.bee32.sem.frame.builtins.SEMContextMenu;
import com.bee32.sem.frame.builtins.SEMFrameMenu;

/**
 * 基本系统
 *
 * <p lang="en">
 * Base System
 */
public class SEMBaseProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMContextMenu.class, SEMContextMenu.ENABLED, true);
        setParameter(SEMFrameMenu.class, SEMFrameMenu.ENABLED, true);
        setParameter(SEMBaseMenu.class, SEMBaseMenu.ENABLED, true);
    }

}
