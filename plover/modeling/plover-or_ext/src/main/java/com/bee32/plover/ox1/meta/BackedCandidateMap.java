package com.bee32.plover.ox1.meta;

import java.util.StringTokenizer;

import javax.free.ParseException;

import com.bee32.plover.util.StringBacked;

public abstract class BackedCandidateMap<T>
        extends StringBacked<CandidateMap<T>> {

    private static final long serialVersionUID = 1L;

    public BackedCandidateMap(CandidateMap<T> form) {
        super(form);
        if (form == null)
            throw new NullPointerException("form");
    }

    public BackedCandidateMap(String def) {
        super(def);
        if (def == null)
            throw new NullPointerException("def");
    }

    @Override
    protected CandidateMap<T> toForm(String def)
            throws ParseException {
        StringTokenizer tok = new StringTokenizer(def, "\n");
        boolean emptyContained = false;

        CandidateMap<T> candidateMap = new CandidateMap<T>();

        while (tok.hasMoreTokens()) {
            String line = tok.nextToken().trim();

            String valDef;
            String description;

            int sharp = line.indexOf("#");
            if (sharp != -1) {
                valDef = line.substring(0, sharp).trim();
                description = line.substring(sharp + 1).trim();
            } else {
                valDef = line.trim();
                description = valDef;
            }

            if (valDef.isEmpty()) {
                emptyContained = true;
                continue;
            }

            T value = valToForm(valDef);

            candidateMap.put(value, description);
        }

        if (emptyContained) {
            // candidateMap.put(null, "(n/a)");
        }
        return candidateMap;
    }

    @Override
    protected String toDef(CandidateMap<T> form) {
        return null;
    }

    protected abstract T valToForm(String def)
            throws ParseException;

    protected abstract String valToDef(T form);

}
