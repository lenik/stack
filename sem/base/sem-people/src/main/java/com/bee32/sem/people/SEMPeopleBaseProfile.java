package com.bee32.sem.people;

import com.bee32.plover.arch.AppProfile;

public abstract class SEMPeopleBaseProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMPeopleMenu.class, SEMPeopleMenu.ENABLED, true);
    }

}
