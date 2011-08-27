package com.bee32.plover.ox1.userMode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class UserDataType
        extends EnumAlt<Character, UserDataType> {

    private static final long serialVersionUID = 1L;

    static final Map<String, UserDataType> nameMap = new HashMap<String, UserDataType>();
    static final Map<Character, UserDataType> valueMap = new HashMap<Character, UserDataType>();

    public UserDataType(char value, String name) {
        super(value, name);
    }

    @Override
    protected Map<String, UserDataType> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, UserDataType> getValueMap() {
        return valueMap;
    }

    public static Collection<UserDataType> values() {
        Collection<UserDataType> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static UserDataType forName(String altName) {
        UserDataType dataType = nameMap.get(altName);
        if (dataType == null)
            throw new NoSuchEnumException(UserDataType.class, altName);
        return dataType;
    }

    public static UserDataType valueOf(char value) {
        UserDataType dataType = valueMap.get(value);
        if (dataType == null)
            throw new NoSuchEnumException(UserDataType.class, value);

        return dataType;
    }

    public static final char T_INT = 'I';
    public static final char T_DOUBLE = 'D';
    public static final char T_TEXT = 'T';

    public static final UserDataType INTEGER = new UserDataType(T_INT, "integer");
    public static final UserDataType DOUBLE = new UserDataType(T_DOUBLE, "double");
    public static final UserDataType TEXT = new UserDataType(T_TEXT, "text");

}