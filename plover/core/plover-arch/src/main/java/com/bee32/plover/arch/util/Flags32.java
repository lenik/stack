package com.bee32.plover.arch.util;

import java.io.Serializable;

public class Flags32
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public int bits;

    public Flags32() {
    }

    public Flags32(int flags) {
        this.bits = flags;
    }

    public final void set(Flags32 flags32) {
        this.bits = flags32.bits;
    }

    public final void set(int flags) {
        this.bits = flags;
    }

    /**
     * Check any bit in the mask is set.
     */
    public final boolean test(int mask) {
        return containsAny(mask);
    }

    public final boolean contains(int mask) {
        return (bits & mask) == mask;
    }

    public final boolean containsAny(int mask) {
        return (bits & mask) != 0;
    }

    public final boolean checkAndLoad(int mask) {
        return checkAndSet(mask);
    }

    public final boolean checkAndUnload(int mask) {
        return checkAndClean(mask);
    }

    public final boolean checkAndSet(int mask) {
        if ((bits & mask) == mask)
            return false;
        bits |= mask;
        return true;
    }

    public final boolean checkAndClean(int mask) {
        if ((bits & mask) == 0)
            return false;
        bits &= ~mask;
        return true;
    }

    public String getBitsString() {
        if (bits == 0)
            return "0";

        // char[32]: XXX - needs to fast check the MSB index.
        StringBuilder buffer = new StringBuilder();
        int x = bits;
        while (x != 0) {
            int lsb = x & 1;
            x >>= 1;
            buffer.append((char) ('0' + lsb));
        }
        return buffer.reverse().toString();
    }

    @Override
    public String toString() {
        return getBitsString();
    }

}
