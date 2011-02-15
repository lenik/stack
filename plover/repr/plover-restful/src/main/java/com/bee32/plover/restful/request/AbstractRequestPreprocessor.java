package com.bee32.plover.restful.request;

public abstract class AbstractRequestPreprocessor
        implements IRequestPreprocessor {

    @Override
    public int getPriority() {
        return 0;
    }

}
