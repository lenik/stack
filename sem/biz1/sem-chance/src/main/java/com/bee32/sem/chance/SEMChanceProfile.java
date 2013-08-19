package com.bee32.sem.chance;

import com.bee32.plover.arch.AppProfile;

/**
 * 客户关系管理
 *
 * <p lang="en">
 * CRM
 */
public class SEMChanceProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMChanceMenu.class, SEMChanceMenu.ENABLED, true);
    }

}
