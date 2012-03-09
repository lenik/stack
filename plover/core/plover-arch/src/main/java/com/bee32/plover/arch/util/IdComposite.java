package com.bee32.plover.arch.util;

import java.io.Serializable;

public class IdComposite
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String NID_SEPARATOR = "->";

    final Serializable[] elements;

    public IdComposite(Serializable... elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IdComposite))
            return false;

        IdComposite other = (IdComposite) obj;

        if (elements.length != other.elements.length)
            return false;

        for (int i = 0; i < elements.length; i++) {
            Serializable o1 = elements[i];
            Serializable o2 = other.elements[i];

            if (o1 == null && o2 == null)
                return true;

            if (!o1.equals(o2))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < elements.length; i++) {
            Serializable o = elements[i];
            if (o == null)
                hash += System.identityHashCode(this); // meaningless..?
            else
                hash += o.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(elements.length * 20);
        for (int i = 0; i < elements.length; i++) {
            if (i != 0)
                sb.append(NID_SEPARATOR);

            Serializable o = elements[i];

            if (o == null)
                sb.append("(null)");
            else
                sb.append(o);
        }
        return sb.toString();
    }

}
