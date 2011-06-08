package com.bee32.plover.orm.ext.basic;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.mvc.ResultView;

public class CreateHandler
        extends CreateOrEditHandler {

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        view.put("method", "create");
        view.put("METHOD", view.V.get("create"));

        super.handleRequest(req, view);

        resp.sendRedirect("index.htm");
        return null;
    }

}
