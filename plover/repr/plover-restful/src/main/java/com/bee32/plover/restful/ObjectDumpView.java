package com.bee32.plover.restful;

import java.io.IOException;
import java.io.PrintWriter;

import javax.free.ObjectInfo;

import org.springframework.web.util.HtmlUtils;

public class ObjectDumpView
        extends RestfulView {

    /**
     * Returns the lowest priority.
     */
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean render(Class<?> clazz, Object obj, IRestfulRequest req, IRestfulResponse resp)
            throws IOException {
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Object Dump</title></head>");
        out.println("<body><h1>Object Dump: " + ObjectInfo.getObjectId(obj) + "</h1>");
        out.println("<hr/>");

        String string = String.valueOf(obj);
        String html = HtmlUtils.htmlEscape(string);
        out.println(html);

        return true;
    }

}
