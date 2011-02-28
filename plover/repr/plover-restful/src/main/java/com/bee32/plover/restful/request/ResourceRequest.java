package com.bee32.plover.restful.request;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.Model;
import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.restful.Verb;

public class ResourceRequest
        extends Model
        implements IResourceRequest, Serializable {

    private static final long serialVersionUID = 1L;

    static final String defaultView = "view";
    static final String defaultRenderer = "html";

    private final HttpServletRequest rawRequest;

    private Verb verb;
    private String path;
    private Profile profile;
    private String format;

    public ResourceRequest(HttpServletRequest request) {
        this.rawRequest = request;
    }

    @Override
    public HttpServletRequest getServletRequest() {
        return rawRequest;
    }

    @Override
    public Verb getVerb() {
        return verb;
    }

    public void setVerb(Verb verb) {
        this.verb = verb;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String getParameter(String name) {
        return rawRequest.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return rawRequest.getParameterValues(name);
    }

    @Override
    public String toString() {
        return verb + " " + path + ":" + profile;
    }

}
