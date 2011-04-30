package com.bee32.plover.arch.util;

import java.io.Serializable;

public class Mask32
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final int maskBits;
    final int shifts;

    public Mask32(int maskBits) {
        this.maskBits = maskBits;

        int shifts = 0;

        while ((maskBits & 1) == 0) {
            shifts++;
            maskBits >>= 1;
        }
        this.shifts = shifts;

    }

    public final int extract(int composite) {
        return (composite & maskBits) >>> shifts;
    }

    public final int compose(int component) {
        return (component << shifts) & maskBits;
    }

    public final int compose(int composite, int component) {
        int add = (component << shifts) & maskBits;
        return (composite & ~maskBits) | add;
    }

}
