package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.restful.MethodNames;
import com.bee32.plover.restful.RestfulRequest;
import com.bee32.plover.restful.Verb;

public class MethodDissolver
        implements IMethodDissolver {

    static final Map<String, Verb> verbByMethod;

    static {
        verbByMethod = new HashMap<String, Verb>();
        verbByMethod.put("get", new Verb(MethodNames.READ));
        verbByMethod.put("put", new Verb(MethodNames.CREATE));
        verbByMethod.put("post", new Verb(MethodNames.UPDATE));
        verbByMethod.put("delete", new Verb(MethodNames.DELETE));
        verbByMethod.put("head", new Verb(MethodNames.ESTATE));
    }

    @Override
    public void desolveMethod(String httpMethod, RestfulRequest request) {
        if (httpMethod == null)
            throw new NullPointerException("httpMethod");

        String methodName = httpMethod.toLowerCase();

        Verb verb = verbByMethod.get(methodName);
        if (verb != null)
            request.setMethod(verb.getName());
    }

}
