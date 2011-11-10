package com.bee32.sem.asset;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class AccountSide
        extends EnumAlt<Character, AccountSide> {

    private static final long serialVersionUID = 1L;

    static final Map<String, AccountSide> nameMap = new HashMap<String, AccountSide>();
    static final Map<Character, AccountSide> valueMap = new HashMap<Character, AccountSide>();

    private AccountSide(char val, String name) {
        super(val, name);
    }

    @Override
    protected Map<String, AccountSide> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, AccountSide> getValueMap() {
        return valueMap;
    }

    public static AccountSide forName(String altName) {
        AccountSide gender = nameMap.get(altName);
        if (gender == null)
            throw new NoSuchEnumException(AccountSide.class, altName);
        return gender;
    }

    public static Collection<AccountSide> values() {
        Collection<AccountSide> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static AccountSide valueOf(Character value) {
        if (value == null)
            throw new NullPointerException("value");

        AccountSide gender = valueMap.get(value);
        if (gender == null)
            throw new NoSuchEnumException(AccountSide.class, value);

        return gender;
    }

    public static AccountSide valueOf(char value) {
        return valueOf(new Character(value));
    }

    public static final AccountSide DEBIT = new AccountSide('d', "debitSide");
    public static final AccountSide CREDIT = new AccountSide('c', "creditSide");

}
