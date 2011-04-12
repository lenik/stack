package com.bee32.plover.restful.util;

import com.bee32.plover.restful.IRESTfulRequest;
import com.bee32.plover.restful.IRESTfulResponse;

public abstract class TemplateController<T>
        extends RESTfulController<T> {

    public static final int CONTENT = 0;
    public static final int CREATE = 1;
    public static final int EDIT = 2;

    public TemplateController(Class<T> clazz) {
        super(clazz);
    }

    protected abstract void template(int mode, IRESTfulRequest req, IRESTfulResponse resp)
            throws Exception;

    /**
     * READ-ONLY
     *
     * INIT-LABEL
     */
    public void content(IRESTfulRequest req, IRESTfulResponse resp)
            throws Exception {
        template(CONTENT, req, resp);
    }

    /**
     * INIT-NONE
     */
    public void createForm(IRESTfulRequest req, IRESTfulResponse resp)
            throws Exception {
        template(CREATE, req, resp);
    }

    /**
     * INIT-EDIT
     */
    public void editForm(IRESTfulRequest req, IRESTfulResponse resp)
            throws Exception {
        template(EDIT, req, resp);
    }

}
