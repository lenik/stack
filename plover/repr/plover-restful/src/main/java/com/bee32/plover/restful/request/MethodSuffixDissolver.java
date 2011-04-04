package com.bee32.plover.restful.request;

import com.bee32.plover.restful.MethodNames;
import com.bee32.plover.restful.RestfulRequest;

public class MethodSuffixDissolver
        implements ISuffixDissolver {

    @Override
    public boolean dissolveSuffix(String name, RestfulRequest request) {
        if (!MethodNames.isDefined(name))
            return false;

        request.setMethod(name);
        return true;
    }

    public static final MethodSuffixDissolver INSTANCE = new MethodSuffixDissolver();

}
