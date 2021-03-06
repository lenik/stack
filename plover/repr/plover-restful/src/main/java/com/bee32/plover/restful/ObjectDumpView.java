package com.bee32.plover.restful;

import java.io.IOException;
import java.io.PrintWriter;

import javax.free.ObjectInfo;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Lazy
public class ObjectDumpView
        extends RESTfulView {

    /**
     * Returns the lowest priority.
     */
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isFallback() {
        return true;
    }

    @Override
    public boolean render(Class<?> clazz, Object obj, IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Object Dump</title></head>");
        out.println("<body><h1>Object Dump: " + ObjectInfo.getObjectId(obj) + "</h1>");
        out.println("<hr/>");

        String string = String.valueOf(obj);
        String html = HtmlUtils.htmlEscape(string);
        out.println("<pre>");
        out.println(html);
        out.println("</pre>");

        out.println("<hr/>");
        out.println("<h2>Request Info</h2>");
        out.println("<pre>");

        out.println("Context-Path: " + req.getContextPath());
        out.println("Dispatch-Path: " + req.getDispatchPath());
        out.println("Arrival: " + req.getArrival());
        out.println("Rest-Path: " + req.getRestPath());
        out.println("Method: " + req.getMethod());
        out.println(req);

        out.println("</pre>");
        return true;
    }

}
