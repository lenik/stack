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

    public final void init(Flags32 flags32) {
        this.bits = flags32.bits;
    }

    public final void init(int bits) {
        this.bits = bits;
    }

    public final void set(int mask, boolean on) {
        if (on)
            this.bits |= mask;
        else
            this.bits &= ~mask;
    }

    public final void set(int mask) {
        this.bits |= mask;
    }

    public final void clear(int mask) {
        this.bits &= ~mask;
    }

    public final void toggle(int mask) {
        this.bits ^= mask;
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
        return checkAndClear(mask);
    }

    public final boolean checkAndSet(int mask) {
        if ((bits & mask) == mask)
            return false;
        bits |= mask;
        return true;
    }

    public final boolean checkAndClear(int mask) {
        if ((bits & mask) == 0)
            return false;
        bits &= ~mask;
        return true;
    }

    public final int translate(int... testAndSet) {
        int bits = 0;
        int i = 0;
        while (i < testAndSet.length) {
            int test = testAndSet[i++];
            int set = testAndSet[i++];
            if (contains(test))
                bits |= set;
        }
        return bits;
    }

    public String getBitsString() {
        if (bits == 0)
            return "0";

        // char[32]: needs to fast check the MSB index.
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
