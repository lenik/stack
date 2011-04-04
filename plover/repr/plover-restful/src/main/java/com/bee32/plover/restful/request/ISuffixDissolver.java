package com.bee32.plover.restful.request;

import com.bee32.plover.restful.RestfulRequest;

public interface ISuffixDissolver {

    boolean dissolveSuffix(String name, RestfulRequest model);

}
