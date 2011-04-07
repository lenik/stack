package com.bee32.plover.restful.util;

import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.restful.IRestfulResponse;

public abstract class TemplateController<T>
        extends RestfulController<T> {

    public static final int CONTENT = 0;
    public static final int CREATE = 1;
    public static final int EDIT = 2;

    public TemplateController(Class<T> clazz) {
        super(clazz);
    }

    protected abstract void template(int mode, IRestfulRequest req, IRestfulResponse resp)
            throws Exception;

    /**
     * READ-ONLY
     *
     * INIT-LABEL
     */
    public void content(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {
        template(CONTENT, req, resp);
    }

    /**
     * INIT-NONE
     */
    public void createForm(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {
        template(CREATE, req, resp);
    }

    /**
     * INIT-EDIT
     */
    public void editForm(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {
        template(EDIT, req, resp);
    }

}
