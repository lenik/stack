package com.bee32.plover.restful.request;

public interface IRequestPreprocessor {

    int getPriority();

    void preprocess(RequestModel model);

}
