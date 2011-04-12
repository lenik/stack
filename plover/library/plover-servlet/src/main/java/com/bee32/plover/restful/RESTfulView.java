package com.bee32.plover.restful;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.Component;

public abstract class RESTfulView
        extends Component
        implements IRESTfulView {

    public RESTfulView() {
        super();
    }

    public RESTfulView(String name) {
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
    public boolean render(Object obj, IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {
        if (obj == null)
            throw new NullPointerException("obj");

        Class<?> clazz = obj.getClass();

        return render(clazz, obj, req, resp);
    }

    @Transactional
    @Override
    public boolean renderTx(Class<?> clazz, Object obj, IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {
        return render(clazz, obj, req, resp);
    }

    @Transactional
    @Override
    public boolean renderTx(Object obj, IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {
        return render(obj, req, resp);
    }

}
