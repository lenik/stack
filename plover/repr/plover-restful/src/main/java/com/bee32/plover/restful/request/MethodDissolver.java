package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.restful.Verb;
import com.bee32.plover.restful.Verbs;

public class MethodDissolver
        implements IMethodDissolver {

    static final Map<String, Verb> verbByMethod;

    static {
        verbByMethod = new HashMap<String, Verb>();
        verbByMethod.put("get", Verbs.GET); // or, read in CRUD
        verbByMethod.put("put", Verbs.CREATE);
        verbByMethod.put("post", Verbs.UPDATE);
        verbByMethod.put("delete", Verbs.DELETE);
        verbByMethod.put("head", Verbs.ESTATE);
    }

    @Override
    public void desolveMethod(String httpMethod, RestfulRequest model) {
        if (httpMethod == null)
            throw new NullPointerException("httpMethod");

        String methodName = httpMethod.toLowerCase();

        Verb verb = verbByMethod.get(methodName);
        if (verb == null)
            verb = new Verb(methodName);

        model.setVerb(verb);
    }

}
