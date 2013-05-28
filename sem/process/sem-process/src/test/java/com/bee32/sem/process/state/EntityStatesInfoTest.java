package com.bee32.sem.process.state;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.process.state.util.EntityStateInfoMap;
import com.bee32.sem.process.state.util.StateIntInfo;

import user.war.entity.AttackMission;

public class EntityStatesInfoTest
        extends Assert {

    @Test
    public void testAppearance() {
        EntityStateInfoMap states = EntityStateInfoMap.getInstance(AttackMission.class);
        for (Integer stateInt : states.getStateIntSet()) {
            StateIntInfo info = states.getInfo(stateInt);
            System.out.println("State " + stateInt + ": " + info.getLabel());
            System.out.println("    " + info.getDescription());
        }
    }

}
