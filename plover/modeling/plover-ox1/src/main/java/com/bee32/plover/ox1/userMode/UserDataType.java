package com.bee32.plover.ox1.userMode;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

public class UserDataType
        extends EnumAlt<Character, UserDataType> {

    private static final long serialVersionUID = 1L;

    public UserDataType(char value, String name) {
        super(value, name);
    }

    public static UserDataType forName(String name) {
        return forName(UserDataType.class, name);
    }

    public static Collection<UserDataType> values() {
        return values(UserDataType.class);
    }

    public static UserDataType forValue(Character value) {
        return forValue(UserDataType.class, value);
    }

    public static UserDataType forValue(char value) {
        return forValue(new Character(value));
    }

    public static final char T_INT = 'I';
    public static final char T_DOUBLE = 'D';
    public static final char T_TEXT = 'T';

    public static final UserDataType INTEGER = new UserDataType(T_INT, "integer");
    public static final UserDataType DOUBLE = new UserDataType(T_DOUBLE, "double");
    public static final UserDataType TEXT = new UserDataType(T_TEXT, "text");

}