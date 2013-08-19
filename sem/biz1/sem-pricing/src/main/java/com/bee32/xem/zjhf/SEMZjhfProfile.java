package com.bee32.xem.zjhf;

import com.bee32.plover.arch.AppProfile;

/**
 * 报价管理
 *
 * <p lang="en">
 * Pricing Management
 */
public class SEMZjhfProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMZjhfMenu.class, SEMZjhfMenu.ENABLED, true);
    }

}
