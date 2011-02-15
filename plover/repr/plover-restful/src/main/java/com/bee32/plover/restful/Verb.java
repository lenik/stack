package com.bee32.plover.restful;

import java.io.Serializable;

public class Verb
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public Verb(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (Verb.class.equals(obj.getClass()))
            return false;

        Verb o = (Verb) obj;
        if (!name.equals(o.name))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

}
