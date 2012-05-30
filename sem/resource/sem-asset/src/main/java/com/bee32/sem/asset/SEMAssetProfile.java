package com.bee32.sem.asset;

import com.bee32.plover.arch.AppProfile;

public class SEMAssetProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMAssetMenu.class, SEMAssetMenu.ENABLED, true);
    }

}
