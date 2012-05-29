package com.bee32.sem.people;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

public class Gender
        extends EnumAlt<Character, Gender> {

    private static final long serialVersionUID = 1L;

    private Gender(char val, String name) {
        super(val, name);
    }

    public static Gender forName(String name) {
        return forName(Gender.class, name);
    }

    public static Collection<Gender> values() {
        return values(Gender.class);
    }

    public static Gender forValue(Character value) {
        return forValue(Gender.class, value);
    }

    public static Gender forValue(char value) {
        return forValue(new Character(value));
    }

    public static final Gender MALE = new Gender('m', "male");
    public static final Gender FEMALE = new Gender('f', "female");
    public static final Gender OTHER = new Gender('x', "other");

}
