package com.bee32.sem.purchase;

import com.bee32.plover.arch.AppProfile;

/**
 * 采购
 *
 * <p lang="en">
 * Purchase
 */
public class SEMSupplierProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMPurchaseMenu.class, SEMPurchaseMenu.ENABLED, true);
    }

}
