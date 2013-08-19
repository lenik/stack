package com.bee32.sem.makebiz;

import com.bee32.plover.arch.AppProfile;
import com.bee32.sem.make.SEMMakeMenu;

/**
 * 订单生产管理
 *
 * <p lang="en">
 * Make Business
 */
public class SEMMakebizProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMMakeMenu.class, SEMMakeMenu.ENABLED, true);
        setParameter(SEMMakebizMenu.class, SEMMakebizMenu.ENABLED, true);
    }

}
