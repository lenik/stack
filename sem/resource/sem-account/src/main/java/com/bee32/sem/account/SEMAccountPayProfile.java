package com.bee32.sem.account;

import com.bee32.plover.arch.AppProfile;

/**
 * 应付管理
 *
 * <p lang="en">
 * Account Pay
 */
public class SEMAccountPayProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMAccountPayMenu.class, SEMAccountPayMenu.ENABLED, true);
    }

}
