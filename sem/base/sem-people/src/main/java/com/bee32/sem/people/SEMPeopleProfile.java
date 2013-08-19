package com.bee32.sem.people;

/**
 * 公司和人员管理
 *
 * <p lang="en">
 * People Manggement
 */
public class SEMPeopleProfile
        extends SEMPeopleBaseProfile {

    @Override
    protected void preamble() {
        super.preamble();
        setParameter(SEMPeopleMenu.class, SEMPeopleMenu.X_RECORDS, true);
    }

}
