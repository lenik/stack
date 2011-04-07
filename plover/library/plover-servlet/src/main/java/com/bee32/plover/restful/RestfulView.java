package com.bee32.plover.restful;

import java.io.IOException;

import com.bee32.plover.arch.Component;

public abstract class RestfulView
        extends Component
        implements IRestfulView {

    public RestfulView() {
        super();
    }

    public RestfulView(String name) {
        super(name);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isFallback() {
        return false;
    }

    @Override
    public boolean render(Object obj, IRestfulRequest req, IRestfulResponse resp)
            throws IOException {
        if (obj == null)
            throw new NullPointerException("obj");

        Class<?> clazz = obj.getClass();

        return render(clazz, obj, req, resp);
    }

}
