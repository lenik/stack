package com.bee32.sem.frame;

import java.io.Serializable;

public final class FrameSite
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public FrameSite(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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

        FrameSite other = (FrameSite) obj;

        if (name.equals(other.name))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public static final FrameSite MAIN = new FrameSite("main-frame");
    public static final FrameSite USER = new FrameSite("user-frame");
    public static final FrameSite GROUP = new FrameSite("group-frame");
    public static final FrameSite ROLE = new FrameSite("role-frame");

}
