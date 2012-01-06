package com.bee32.sem.world.city;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

public class City
        extends Region<City> {

    private static final long serialVersionUID = 1L;

    static final Map<String, City> nameMap = getNameMap(City.class);
    static final Map<String, City> valueMap = getValueMap(City.class);

    public City(String value, String name) {
        super(value, name);
    }

    public static City forName(String altName) {
        City city = nameMap.get(altName);
        if (city == null)
            throw new NoSuchEnumException(City.class, altName);
        return city;
    }

    public static Collection<City> values() {
        Collection<City> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static City valueOf(String value) {
        if (value == null)
            return null;

        City city = valueMap.get(value);
        if (city == null)
            throw new NoSuchEnumException(City.class, value);

        return city;
    }

}
