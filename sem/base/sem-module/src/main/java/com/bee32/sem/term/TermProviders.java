package com.bee32.sem.term;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

import javax.free.Strings;

public class TermProviders {

    static final Map<String, ITermProvider> map;

    static {
        map = new TreeMap<String, ITermProvider>();
        for (ITermProvider termProvider : ServiceLoader.load(ITermProvider.class)) {
            String simpleName = termProvider.getClass().getSimpleName();

            String lcfirst = Strings.lcfirst(simpleName);
            map.put(lcfirst, termProvider);

            if (simpleName.startsWith("SEM") && simpleName.endsWith("Terms")) {
                String stem = simpleName.substring(3, simpleName.length() - 5);
                lcfirst = Strings.lcfirst(stem);
                map.put(lcfirst, termProvider);
            }
        }
    }

    public static ITermProvider getTermProvider(String providerName) {
        return map.get(providerName);
    }

}
