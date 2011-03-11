package com.bee32.plover.pub.oid;

import java.io.Serializable;
import java.util.Arrays;

public class OidVector
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final int[] vector;

    public OidVector(int... vector) {
        if (vector == null)
            throw new NullPointerException("vector");
        this.vector = vector;
    }

    /**
     * Format the given OID to a string.
     *
     * @param delim
     *            The separator used to delimit oid numbers.
     * @return The formatted string.
     */
    public String format(char delim) {
        StringBuilder buf = new StringBuilder(vector.length * 15);
        for (int i = 0; i < vector.length; i++) {
            if (i != 0)
                buf.append(delim);
            buf.append(vector[i]);
        }
        return buf.toString();
    }

    public String toPath() {
        return format('/');
    }

    @Override
    public String toString() {
        return format('.');
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(vector);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OidVector other = (OidVector) obj;
        if (!Arrays.equals(vector, other.vector))
            return false;
        return true;
    }

}
