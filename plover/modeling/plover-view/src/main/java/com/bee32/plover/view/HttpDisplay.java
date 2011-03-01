package com.bee32.plover.view;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.servlet.util.ServletPrintOut;

public class HttpDisplay
        extends AbstractDisplay {

    private final HttpServletResponse response;
    private ServletPrintOut servletPrintOut;

    public HttpDisplay(HttpServletResponse response) {
        if (response == null)
            throw new NullPointerException("response");
        this.response = response;
    }

    @Override
    public HttpServletResponse getResponse() {
        return response;
    }

    @Override
    public ServletPrintOut getOut()
            throws IOException {
        if (servletPrintOut == null)
            synchronized (servletPrintOut) {
                if (servletPrintOut == null)
                    servletPrintOut = new ServletPrintOut(response);
            }
        return servletPrintOut;
    }

}
