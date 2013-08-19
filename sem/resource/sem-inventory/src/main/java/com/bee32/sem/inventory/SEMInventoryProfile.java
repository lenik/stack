package com.bee32.sem.inventory;

import com.bee32.plover.arch.AppProfile;

/**
 * 库存管理
 *
 * <p lang="en">
 * Inventory Management
 */
public class SEMInventoryProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMInventoryMenu.class, SEMInventoryMenu.ENABLED, true);
    }

}
