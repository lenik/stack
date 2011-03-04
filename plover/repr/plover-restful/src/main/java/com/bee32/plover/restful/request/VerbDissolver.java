package com.bee32.plover.restful.request;

import com.bee32.plover.restful.Verb;

public class VerbDissolver
        implements ISuffixDissolver {

    @Override
    public boolean desolveSuffix(String name, RestfulRequest model) {
        Verb verb = new Verb(name);
        model.setVerb(verb);
        return true;
    }

}
