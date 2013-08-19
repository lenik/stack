package com.bee32.sem.world.city;

import java.util.Collection;

/**
 * 国家
 *
 * <p lang="en">
 * Country
 */
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

    public static Country forValue(String value) {
        return forValue(Country.class, value);
    }

}