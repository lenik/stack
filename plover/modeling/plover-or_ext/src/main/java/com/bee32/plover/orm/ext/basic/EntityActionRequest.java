package com.bee32.plover.orm.ext.basic;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.mvc.ActionRequest;

public class EntityActionRequest
        extends ActionRequest {

    public EntityActionRequest(HttpServletRequest request) {
        super(request);
    }

}
