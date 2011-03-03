package com.bee32.plover.restful.request;

import com.bee32.plover.restful.Verb;

public class VerbPreprocessor
        extends AbstractRequestPreprocessor {

    @Override
    public void preprocess(RestfulRequest request) {
        String x = request.getParameter("X");
        if (x != null) {
            Verb verb = new Verb(x);
            request.setVerb(verb);
        }
    }

}
