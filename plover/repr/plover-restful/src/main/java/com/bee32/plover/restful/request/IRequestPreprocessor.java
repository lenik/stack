package com.bee32.plover.restful.request;

import com.bee32.plover.restful.RestfulRequest;

public interface IRequestPreprocessor {

    int getPriority();

    void preprocess(RestfulRequest request);

}
