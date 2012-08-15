package com.bee32.sem.salary;

import com.bee32.plover.arch.AppProfile;

public class SEMSalaryProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMSalaryMenu.class, SEMSalaryMenu.ENABLED, true);
    }

}
