package com.bee32.sem.purchase;

import com.bee32.plover.arch.AppProfile;

public class SEMSupplierProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMPurchaseMenu.class, SEMPurchaseMenu.ENABLED, true);
    }

}
