package com.bee32.plover.restful.request;

import com.bee32.plover.restful.RESTfulRequest;
import com.bee32.plover.restful.util.MethodNameUtil;

public class MethodSuffixDissolver
        implements ISuffixDissolver {

    @Override
    public boolean dissolveSuffix(String name, RESTfulRequest request) {
        if (!MethodNameUtil.isDefined(name))
            return false;

        request.setMethod(name);
        return true;
    }

    public static final MethodSuffixDissolver INSTANCE = new MethodSuffixDissolver();

}
