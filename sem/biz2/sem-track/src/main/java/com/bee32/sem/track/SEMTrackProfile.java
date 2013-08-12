package com.bee32.sem.track;

import com.bee32.plover.arch.AppProfile;

/**
 * 事件跟踪系统
 *
 * <p lang="en">
 * Issue Tracking System
 */
public class SEMTrackProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(SEMTrackMenu.class, SEMTrackMenu.ENABLED, true);
    }

}
