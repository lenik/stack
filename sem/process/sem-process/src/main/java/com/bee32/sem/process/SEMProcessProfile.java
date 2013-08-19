package com.bee32.sem.process;

import com.bee32.plover.arch.AppProfile;
import com.bee32.sem.event.SEMEventMenu;

/**
 * 过程支持
 *
 * <p lang="en">
 * Process Support
 */
public class SEMProcessProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMEventMenu.class, SEMEventMenu.ENABLED, true);
        setParameter(SEMProcessMenu.class, SEMProcessMenu.ENABLED, true);
    }

}
