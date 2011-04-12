package com.bee32.plover.restful.request;

import com.bee32.plover.restful.RESTfulRequest;

public interface IRequestPreprocessor {

    int getPriority();

    void preprocess(RESTfulRequest request);

}
