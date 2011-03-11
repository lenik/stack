package com.bee32.plover.arch.util;

public class LoadFlags32
        extends LoadFlags {

    private int flags;

    public LoadFlags32() {
    }

    public LoadFlags32(int flags) {
        this.flags = flags;
    }

    public boolean checkAndLoad(int bits) {
        if ((flags & bits) == bits)
            return false;
        flags |= bits;
        return true;
    }

    public boolean checkAndClean(int bits) {
        if ((flags & bits) == 0)
            return false;
        flags &= ~bits;
        return true;
    }

    @Override
    public String getBitsString() {
        if (flags == 0)
            return "0";

        // char[32]: XXX - needs to fast check the MSB index.
        StringBuilder buffer = new StringBuilder();
        int x = flags;
        while (x != 0) {
            int lsb = x & 1;
            x >>= 1;
            buffer.append((char) ('0' + lsb));
        }
        return buffer.reverse().toString();
    }

}
