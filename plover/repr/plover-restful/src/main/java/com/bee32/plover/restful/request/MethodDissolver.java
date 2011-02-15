package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.restful.CommonVerbs;
import com.bee32.plover.restful.Verb;

public class MethodDissolver
        implements IMethodDissolver {

    private static final Map<String, Verb> verbByMethod;

    static {
        verbByMethod = new HashMap<String, Verb>();
        verbByMethod.put("put", CommonVerbs.CREATE);
        verbByMethod.put("get", CommonVerbs.GET); // or, read in CRUD
        verbByMethod.put("post", CommonVerbs.UPDATE);
        verbByMethod.put("delete", CommonVerbs.DELETE);
        verbByMethod.put("head", CommonVerbs.HEAD);
    }

    @Override
    public void desolveMethod(String httpMethod, RequestModel model) {
        if (httpMethod == null)
            throw new NullPointerException("httpMethod");

        String methodName = httpMethod.toLowerCase();

        Verb verb = verbByMethod.get(methodName);
        if (verb == null)
            verb = new Verb(methodName);

        model.setVerb(verb);
    }

}
