package com.bee32.plover.restful;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Verb
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final boolean managed;

    private static Map<String, Verb> verbs = new HashMap<String, Verb>();

    public Verb(String name) {
        this(name, false);
    }

    public Verb(String name, boolean managed) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
        this.managed = managed;

        if (verbs.containsKey(name))
            throw new IllegalStateException("Verb " + name + " is already registered");

        verbs.put(name, this);
    }

    public String getName() {
        return name;
    }

    public boolean isManaged() {
        return managed;
    }

    @Override
    public boolean equals(Object obj) {
        if (Verb.class.equals(obj.getClass()))
            return false;

        Verb o = (Verb) obj;
        if (managed != o.managed)
            return false;
        if (!name.equals(o.name))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0x878cd12;
        if (managed)
            hash ^= 0x189245ca;
        hash ^= name.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Verb getInstance(String name) {
        return verbs.get(name);
    }

}
