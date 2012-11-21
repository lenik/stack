package com.bee32.xem.zjhf;


import com.bee32.plover.arch.AppProfile;

public class SEMZjhfProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMZjhfMenu.class, SEMZjhfMenu.ENABLED, true);
    }

}
