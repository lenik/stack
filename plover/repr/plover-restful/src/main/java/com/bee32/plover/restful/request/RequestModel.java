package com.bee32.plover.restful.request;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.Model;
import com.bee32.plover.model.view.View;
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
    private View view;
    private String renderer;

    public RequestModel(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public String getDispatchPath() {
        return dispatchPath;
    }

    public void setDispatchPath(String dispatchPath) {
        this.dispatchPath = dispatchPath;
    }

    public Verb getVerb() {
        return verb;
    }

    public void setVerb(Verb verb) {
        this.verb = verb;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

}
