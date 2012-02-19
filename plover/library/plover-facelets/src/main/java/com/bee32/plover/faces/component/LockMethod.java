package com.bee32.plover.faces.component;

public enum LockMethod {

    disable("disabled", true, false), //
    readonly("readonly", true, false), //
    editable("editable", false, true), //
    ;

    final String attributeName;
    final Object onValue;
    final Object offValue;

    private LockMethod(String attributeName, Object onValue, Object offValue) {
        this.attributeName = attributeName;
        this.onValue = onValue;
        this.offValue = offValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public Object getOnValue() {
        return onValue;
    }

    public Object getOffValue() {
        return offValue;
    }

}
