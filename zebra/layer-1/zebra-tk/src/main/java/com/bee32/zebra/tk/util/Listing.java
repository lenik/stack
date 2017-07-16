package com.bee32.zebra.tk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.bodz.bas.i18n.dom.iString;
import net.bodz.bas.t.pojo.Pair;
import net.bodz.bas.t.predef.Predef;
import net.bodz.lily.model.base.CoObject;

public class Listing {

    @SafeVarargs
    public static <T> List<Pair<T, String>> pairsValString(T... array) {
        List<Pair<T, String>> pairs = new ArrayList<>(array.length);
        for (T o : array)
            pairs.add(Pair.of(o, o.toString()));
        return pairs;
    }

    @SafeVarargs
    public static <E extends Predef<?, ?>> List<Pair<E, String>> pairsValLabel(E... array) {
        return pairsValLabel(Arrays.asList(array));
    }

    public static <E extends Predef<?, ?>> List<Pair<E, String>> pairsValLabel(Iterable<E> items) {
        List<Pair<E, String>> pairs = new ArrayList<>();
        for (E o : items) {
            iString label = o.getLabel();
            pairs.add(Pair.of(o, label == null ? null : label.toString()));
        }
        return pairs;
    }

    public static String joinLabels(String delim, Iterable<? extends CoObject> entities) {
        StringBuilder sb = new StringBuilder();
        for (CoObject o : entities) {
            if (sb.length() != 0)
                sb.append(delim);
            sb.append(o.getLabel());
        }
        return sb.toString();
    }

}
