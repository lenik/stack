package com.bee32.plover.arch.util;

import java.io.Serializable;
import java.util.Arrays;

public class IdComposite
        implements Serializable {

    private static final long serialVersionUID = 1L;

    final Serializable[] elements;

    public IdComposite(Serializable... elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IdComposite))
            return false;

        IdComposite other = (IdComposite) obj;

        return Arrays.equals(elements, other.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(elements.length * 20);
        for (int i = 0; i < elements.length; i++) {
            if (i != 0)
                sb.append("::");
            sb.append(elements[i]);
        }
        return sb.toString();
    }

}
