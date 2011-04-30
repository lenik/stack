package com.bee32.plover.orm.ext.meta;

import javax.free.ParseException;

public class BackedCandidateMap1
        extends BackedCandidateMap<String> {

    private static final long serialVersionUID = 1L;

    public BackedCandidateMap1(CandidateMap<String> form) {
        super(form);
    }

    public BackedCandidateMap1(String def) {
        super(def);
    }

    @Override
    protected String valToForm(String def)
            throws ParseException {
        return def;
    }

    @Override
    protected String valToDef(String form) {
        return form;
    }

}
