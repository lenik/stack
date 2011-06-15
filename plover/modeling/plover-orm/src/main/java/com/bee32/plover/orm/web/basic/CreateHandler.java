package com.bee32.plover.orm.web.basic;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.web.IEntityForming;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public class CreateHandler<E extends Entity<K>, K extends Serializable>
        extends CreateOrEditHandler<E, K> {

    public CreateHandler(IEntityForming<E, K> forming) {
        super(forming);
    }

    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        result.put("method", "create");
        result.put("METHOD", result.V.get("create"));

        super._handleRequest(req, result);

        return result.sendRedirect("index.do");
    }

}
