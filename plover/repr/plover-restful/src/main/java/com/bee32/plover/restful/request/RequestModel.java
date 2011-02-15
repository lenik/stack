package com.bee32.plover.restful.request;

import java.io.Serializable;

import javax.free.StringPart;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.Model;
import com.bee32.plover.restful.Verb;

public class RequestModel
        extends Model
        implements Serializable {

    private static final long serialVersionUID = 1L;

    static final String defaultView = "view";
    static final String defaultRenderer = "html";

    private final HttpServletRequest request;

    private String dispatchPath;

    private Verb verb;
    private String view;
    private String renderer;

    public RequestModel(HttpServletRequest request) {
        this.request = request;

        String uri = request.getRequestURI();
        String baseName = StringPart.before(uri, '/');

        int end = baseName.length();
        int dot;
        while ((dot = baseName.lastIndexOf('.', end - 1)) != -1) {
            String keyword = baseName.substring(dot + 1, end);
            // parseKeyword();
            end = dot;
        }

        int stripped = baseName.length() - end;
        dispatchPath = uri.substring(0, uri.length() - stripped);
    }

}
