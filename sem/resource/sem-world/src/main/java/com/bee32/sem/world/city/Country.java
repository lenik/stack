package com.bee32.sem.world.city;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.NoSuchEnumException;

public class Country
        extends Region<Country> {

    private static final long serialVersionUID = 1L;

    static final Map<String, Country> nameMap = new HashMap<String, Country>();
    static final Map<String, Country> valueMap = new HashMap<String, Country>();

    public Country(String value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, Country> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<String, Country> getValueMap() {
        return valueMap;
    }

    public static Country forName(String altName) {
        Country country = nameMap.get(altName);
        if (country == null)
            throw new NoSuchEnumException(Country.class, altName);
        return country;
    }

    public static Collection<Country> values() {
        Collection<Country> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static Country valueOf(String value) {
        if (value == null)
            return null;

        Country country = valueMap.get(value);
        if (country == null)
            throw new NoSuchEnumException(Country.class, value);

        return country;
    }

}