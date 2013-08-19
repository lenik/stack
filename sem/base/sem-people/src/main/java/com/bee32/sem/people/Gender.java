package com.bee32.sem.people;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 *
 *
 * <p lang="en">
 */
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

    /**
     * 男
     *
     * <p lang="en">
     * Male
     */
    public static final Gender MALE = new Gender('m', "male");

    /**
     * 女
     *
     * <p lang="en">
     * Female
     */
    public static final Gender FEMALE = new Gender('f', "female");

    /**
     * 其它
     *
     * <p lang="en">
     * Other
     */
    public static final Gender OTHER = new Gender('x', "other");

}
