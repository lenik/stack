package com.bee32.sem.world.city;

import java.util.Collection;

public class Country
        extends Region<Country> {

    private static final long serialVersionUID = 1L;

    public Country(String value, String name) {
        super(value, name);
    }

    public static Country forName(String name) {
        return forName(Country.class, name);
    }

    public static Collection<Country> values() {
        return values(Country.class);
    }

    public static Country valueOf(String value) {
        return valueOf(Country.class, value);
    }

}