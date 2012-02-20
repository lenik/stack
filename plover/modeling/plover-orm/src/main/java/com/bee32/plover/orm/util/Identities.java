package com.bee32.plover.orm.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bee32.plover.orm.entity.IIdentity;

public class Identities {

    /**
     * For duplicated identities, the result is unknown.
     */
    public static RefsDiff compare(Collection<? extends IIdentity<?>> left, Collection<? extends IIdentity<?>> right) {
        RefsDiff diff = new RefsDiff();
        Map<Object, IIdentity<?>> leftIndex = new LinkedHashMap<>();
        for (IIdentity<?> le : left) {
            Object leId = le.getId();
            leftIndex.put(leId, le);
        }
        for (IIdentity<?> ri : right) {
            Object riId = ri.getId();
            IIdentity<?> le = leftIndex.remove(riId);
            if (le == null) {
                // right only
                diff.rightOnly.add(ri);
            } else {
                // both
            }
        }
        for (IIdentity<?> le : leftIndex.values()) {
            // left only
            diff.leftOnly.add(le);
        }
        return diff;
    }

}
