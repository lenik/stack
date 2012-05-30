package com.bee32.sem.people;

public class SEMPeopleProfile
        extends SEMPeopleBaseProfile {

    @Override
    protected void preamble() {
        super.preamble();
        setParameter(SEMPeopleMenu.class, SEMPeopleMenu.X_RECORDS, true);
    }

}
