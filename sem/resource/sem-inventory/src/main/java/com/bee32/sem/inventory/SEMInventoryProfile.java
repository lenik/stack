package com.bee32.sem.inventory;

import com.bee32.plover.arch.AppProfile;

public class SEMInventoryProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMInventoryMenu.class, SEMInventoryMenu.ENABLED, true);
    }

}
