package com.bee32.plover.restful.request;

import com.bee32.plover.restful.Verb;

public class VerbDissolver
        implements ISuffixDissolver {

    @Override
    public boolean dissolveSuffix(String name, RestfulRequest model) {
        Verb verb = new Verb(name);
        model.setVerb(verb);
        return true;
    }

}
