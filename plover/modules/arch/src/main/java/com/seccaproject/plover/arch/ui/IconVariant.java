package com.seccaproject.plover.arch.ui;

public enum IconVariant {

    BIG("big", 1), //
    DISABLED("disabled", 2), //
    HOVER("hover", 4), //
    ;

    private final String name;
    private final int bits;

    private IconVariant(String name, int bits) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.bits = bits;
    }

    public int getVariants() {
        return bits;
    }

    public boolean isBig() {
        return (bits & BIG.bits) != 0;
    }

    public boolean isDisabled() {
        return (bits & DISABLED.bits) != 0;
    }

    public boolean isHover() {
        return (bits & HOVER.bits) != 0;
    }

    public String getName() {
        return name;
    }

    public String getPreferredSuffix() {
        StringBuffer buf = new StringBuffer(16);
        for (IconVariant var : IconVariant.values()) {
            if ((bits & var.bits) == var.bits) {
                buf.append('_');
                buf.append(var.name);
            }
        }
        return buf.toString();
    }

    @Override
    public String toString() {
        return getName();
    }

}
