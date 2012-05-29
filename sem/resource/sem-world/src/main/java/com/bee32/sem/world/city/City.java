package com.bee32.sem.world.city;

import java.util.Collection;

public class City
        extends Region<City> {

    private static final long serialVersionUID = 1L;

    public City(String value, String name) {
        super(value, name);
    }

    public static City forName(String name) {
        return forName(City.class, name);
    }

    public static Collection<City> values() {
        return values(City.class);
    }

    public static City forValue(String value) {
        return forValue(City.class, value);
    }

}
