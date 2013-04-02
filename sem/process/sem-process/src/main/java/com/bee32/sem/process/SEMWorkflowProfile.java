package com.bee32.sem.process;

import com.bee32.plover.arch.AppProfile;

public class SEMWorkflowProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMWorkflowMenu.class, SEMWorkflowMenu.ENABLED, true);
    }

}
