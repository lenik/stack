package com.bee32.plover.orm.ext.meta;

import java.util.LinkedHashMap;

public class CandidateMap<T>
        extends LinkedHashMap<T, String> {

    private static final long serialVersionUID = 1L;

    public CandidateMap() {
    }

    @Override
    public String put(T value, String description) {
        return put(value, description);
    }

}
