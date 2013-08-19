package com.bee32.sem.mail;

import com.bee32.plover.arch.AppProfile;

/**
 * 邮件交换
 *
 * <p lang="en">
 * Mail Exchange
 */
public class SEMMailProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMMailMenu.class, SEMMailMenu.ENABLED, true);
    }

}
