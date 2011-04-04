package com.bee32.plover.restful.request;

import com.bee32.plover.restful.RestfulRequest;

public interface IMethodDissolver {

    void desolveMethod(String httpMethod, RestfulRequest model);

}
