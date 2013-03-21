package com.bee32.plover.model.state;

import java.util.Map;
import java.util.Set;

public class PropertyStateInfo {

    Set<Integer> lockedState;
    Set<Integer> unlockedState;
    Set<Integer> visibleState;
    Set<Integer> invisibleState;

    Map<Integer, PropertyFlags> map;

}
