package com.bee32.plover.orm.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bee32.plover.orm.entity.IIdentity;

public class RefsDiff {

    List<IIdentity<?>> leftOnly = new ArrayList<IIdentity<?>>();
    List<IIdentity<?>> rightOnly = new ArrayList<IIdentity<?>>();

    @SuppressWarnings("unchecked")
    public <T extends IIdentity<?>> Collection<? extends T> leftOnly() {
        return (Collection<? extends T>) leftOnly;
    }

    @SuppressWarnings("unchecked")
    public <T extends IIdentity<?>> Collection<? extends T> rightOnly() {
        return (Collection<? extends T>) rightOnly;
    }

}