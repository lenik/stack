package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

public class AliasUtil {

    static Map<String, Class<?>> aliases = new HashMap<String, Class<?>>();

    public static void register(Class<?> type, String alias) {
        Class<?> existing = aliases.get(alias);
        if (existing != null)
            if (existing != type)
                throw new IllegalUsageException("Ambiguous alias " + alias + ": " + type + " & existing");
            else
                return;

        aliases.put(alias, type);
    }

    public static <T> Class<T> getAliasedType(String alias) {
        return (Class<T>) aliases.get(alias);
    }

}
