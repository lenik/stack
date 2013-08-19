package com.bee32.sem.material;

import com.bee32.plover.arch.AppProfile;

/**
 * 物料管理
 *
 * <p lang="en">
 * Material Management
 */
public class SEMMaterialProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMMaterialMenu.class, SEMMaterialMenu.ENABLED, true);
    }

}
