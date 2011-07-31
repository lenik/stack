package com.bee32.sem.people;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class Gender
        extends EnumAlt<Character, Gender> {

    private static final long serialVersionUID = 1L;

    static final Map<String, Gender> nameMap = new HashMap<String, Gender>();
    static final Map<Character, Gender> valueMap = new HashMap<Character, Gender>();

    private Gender(char val, String name) {
        super(val, name);
    }

    @Override
    protected Map<String, Gender> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, Gender> getValueMap() {
        return valueMap;
    }

    public static Gender forName(String altName) {
        Gender gender = nameMap.get(altName);
        if (gender == null)
            throw new NoSuchEnumException(Gender.class, altName);
        return gender;
    }

    public static Collection<Gender> values() {
        Collection<Gender> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static Gender valueOf(Character value) {
        if (value == null)
            throw new NullPointerException("value");

        Gender gender = valueMap.get(value);
        if (gender == null)
            throw new NoSuchEnumException(Gender.class, value);

        return gender;
    }

    public static Gender valueOf(char value) {
        return valueOf(new Character(value));
    }

    public static final Gender MALE = new Gender('m', "male");
    public static final Gender FEMALE = new Gender('f', "female");
    public static final Gender OTHER = new Gender('x', "other");

}
