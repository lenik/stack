package com.bee32.plover.restful.request;

import com.bee32.plover.restful.RESTfulRequest;

public interface ISuffixDissolver {

    boolean dissolveSuffix(String name, RESTfulRequest model);

}
