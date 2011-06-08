package com.bee32.plover.orm.ext.basic;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.mvc.ResultView;

public class CreateFormHandler
        extends CreateOrEditHandler {

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        view.setViewName(normalizeView("form"));
        view.put("method", "create");
        view.put("METHOD", view.V.get("create"));

        return super.handleRequest(req, view);
    }

}
