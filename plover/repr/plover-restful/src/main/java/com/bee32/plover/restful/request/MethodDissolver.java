package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.restful.Verb;
import com.bee32.plover.restful.VerbNames;

public class MethodDissolver
        implements IMethodDissolver {

    static final Map<String, Verb> verbByMethod;

    static {
        verbByMethod = new HashMap<String, Verb>();
        verbByMethod.put("put", new Verb(VerbNames.CREATE));
        verbByMethod.put("post", new Verb(VerbNames.UPDATE));
        verbByMethod.put("delete", new Verb(VerbNames.DELETE));
        verbByMethod.put("head", new Verb(VerbNames.ESTATE));
    }

    @Override
    public void desolveMethod(String httpMethod, RestfulRequest model) {
        if (httpMethod == null)
            throw new NullPointerException("httpMethod");

        String methodName = httpMethod.toLowerCase();

        Verb verb = verbByMethod.get(methodName);
        if (verb != null)
            model.setVerb(verb);
    }

}
