package com.bee32.sem.asset;

import com.bee32.plover.arch.AppProfile;

/**
 * 资产管理
 *
 * <p lang="en">
 * Asset Management
 */
public class SEMAssetProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMAssetMenu.class, SEMAssetMenu.ENABLED, true);
    }

}
