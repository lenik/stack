package com.bee32.sem.account;

import com.bee32.plover.arch.AppProfile;

/**
 * 应收管理
 *
 * <p lang="en">
 * Account Receivable Management
 */
public class SEMAccountReceProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMAccountReceMenu.class, SEMAccountReceMenu.ENABLED, true);
    }

}
