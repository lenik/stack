package com.bee32.plover.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MixedHttpProcess
        extends HttpProcess
        implements HttpServletRequest {

    public MixedHttpProcess(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

}
