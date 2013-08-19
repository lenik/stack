package com.bee32.sem.process;

import com.bee32.plover.arch.AppProfile;

/**
 * APEX 工作流
 *
 * <p lang="en">
 */
public class SEMWorkflowProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMWorkflowMenu.class, SEMWorkflowMenu.ENABLED, true);
    }

}
