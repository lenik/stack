package com.bee32.plover.arch.ui.impl;

import java.awt.IllegalComponentStateException;
import java.util.Locale;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.accessibility.AccessibleStateSet;

public class AccessibleContextImpl
        extends AccessibleContext {

    @Override
    public Locale getLocale()
            throws IllegalComponentStateException {
        return null;
    }

    @Override
    public int getAccessibleChildrenCount() {
        return 0;
    }

    @Override
    public Accessible getAccessibleChild(int i) {
        return null;
    }

    @Override
    public int getAccessibleIndexInParent() {
        return 0;
    }

    @Override
    public AccessibleRole getAccessibleRole() {
        return null;
    }

    @Override
    public AccessibleStateSet getAccessibleStateSet() {
        return null;
    }

}
