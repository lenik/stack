package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.restful.CommonVerbs;
import com.bee32.plover.restful.Verb;

public class VerbPreprocessor
        extends AbstractRequestPreprocessor {

    static final Map<String, Verb> definedVerbs;

    static {
        definedVerbs = new HashMap<String, Verb>();
        definedVerbs.put("create", CommonVerbs.CREATE);
        definedVerbs.put("get", CommonVerbs.GET); // or, read in CRUD
        definedVerbs.put("update", CommonVerbs.UPDATE);
        definedVerbs.put("delete", CommonVerbs.DELETE);
        definedVerbs.put("head", CommonVerbs.HEAD);
    }

    @Override
    public void preprocess(ResourceRequest request) {
        String x = request.getParameter("X");
        if (x != null) {
            Verb verb = definedVerbs.get(x);
            if (verb != null)
                request.setVerb(verb);
        }
    }

}
