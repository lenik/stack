package com.bee32.icsf.access;

import javax.free.UnexpectedException;

import com.bee32.plover.arch.util.ILabelledEntry;

public enum SinglePermissionTristate
        implements ILabelledEntry {

    inherited(null),

    allowed(true),

    denied(false),

    ;

    final Boolean bool;

    private SinglePermissionTristate(Boolean bool) {
        this.bool = bool;
    }

    public Boolean getBoolean() {
        return bool;
    }

    @Override
    public String getEntryLabel() {
        switch (this) {
        case inherited:
            return "默认（继承）";
        case allowed:
            return "允许";
        case denied:
            return "拒绝";
        }
        throw new UnexpectedException();
    }

    @Override
    public String toString() {
        return getEntryLabel();
    }

}
