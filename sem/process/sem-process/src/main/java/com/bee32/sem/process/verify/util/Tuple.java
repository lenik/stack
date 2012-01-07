package com.bee32.sem.process.verify.util;

import java.io.Serializable;
import java.util.Arrays;

public final class Tuple
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Object[] factors;
    public final int length;

    public Tuple(Object... factors) {
        if (factors == null)
            throw new NullPointerException("factors");
        this.factors = factors;
        this.length = factors.length;
    }

    public <T> T getFactor(int index) {
        @SuppressWarnings("unchecked")
        T factor = (T) factors[index];
        return factor;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tuple))
            return false;
        Tuple o = (Tuple) obj;
        return Arrays.equals(factors, o.factors);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(factors);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(factors.length * 10);
        buf.append('[');
        for (int i = 0; i < factors.length; i++) {
            if (i != 0)
                buf.append(", ");
            buf.append(factors[i]);
        }
        buf.append(']');
        return buf.toString();
    }

}
