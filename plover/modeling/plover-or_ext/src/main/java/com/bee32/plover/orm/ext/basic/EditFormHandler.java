package com.bee32.plover.orm.ext.basic;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.mvc.ResultView;

public class EditFormHandler
        extends CreateOrEditHandler {

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        view.setViewName(normalizeView("form"));
        view.put("method", "edit");
        view.put("METHOD", view.V.get("edit"));

        return super.handleRequest(req, view);
    }

}
