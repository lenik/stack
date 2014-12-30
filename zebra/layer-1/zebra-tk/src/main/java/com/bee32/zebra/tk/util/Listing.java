package com.bee32.zebra.tk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.bodz.bas.t.pojo.Pair;
import net.bodz.bas.t.predef.Predef;

import com.tinylily.model.base.CoEntity;

public class Listing {

    @SafeVarargs
    public static <T> List<Pair<T, String>> pairsValString(T... array) {
        List<Pair<T, String>> pairs = new ArrayList<>(array.length);
        for (T o : array)
            pairs.add(Pair.of(o, o.toString()));
        return pairs;
    }

    @SafeVarargs
    public static <T extends Predef<?, ?>> List<Pair<T, String>> pairsValLabel(T... array) {
        return pairsValLabel(Arrays.asList(array));
    }

    public static <T extends Predef<?, ?>> List<Pair<T, String>> pairsValLabel(Iterable<T> items) {
        List<Pair<T, String>> pairs = new ArrayList<>();
        for (T o : items)
            pairs.add(Pair.of(o, o.getLabel().toString()));
        return pairs;
    }

    public static String joinLabels(String delim, Iterable<? extends CoEntity> entities) {
        StringBuilder sb = new StringBuilder();
        for (CoEntity o : entities) {
            if (sb.length() != 0)
                sb.append(delim);
            sb.append(o.getLabel());
        }
        return sb.toString();
    }

}
