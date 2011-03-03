package com.bee32.plover.restful.request;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.model.profile.StandardProfiles;
import com.bee32.plover.restful.Verb;
import com.bee32.plover.restful.Verbs;
import com.bee32.plover.util.Mime;
import com.bee32.plover.util.Mimes;

public class RestfulRequest
        implements IRestfulRequest, Serializable {

    private static final long serialVersionUID = 1L;

    private final HttpServletRequest servletRequest;

    private Verb verb = Verbs.GET;
    private String path;
    private Profile profile = StandardProfiles.CONTENT;
    private Mime contentType = Mimes.text_html;

    public RestfulRequest(HttpServletRequest request) {
        this.servletRequest = request;
    }

    @Override
    public HttpServletRequest getServletRequest() {
        return servletRequest;
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
    public Mime getContentType() {
        return contentType;
    }

    public void setContentType(Mime contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getParameter(String name) {
        return servletRequest.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return servletRequest.getParameterValues(name);
    }

    public String toComplexPath() {
        StringBuffer buf = new StringBuffer();

        buf.append(path);

        if (profile != StandardProfiles.CONTENT) {
            buf.append('~');
            buf.append(profile);
        }

        if (verb != null && verb != Verbs.GET) {
            buf.append('*');
            buf.append(verb);
        }

        if (contentType != Mimes.text_html) {
            buf.append('.');
            buf.append(contentType.getPreferredExtension());
        }

        String complexPath = buf.toString();
        return complexPath;
    }

    @Override
    public String toString() {
        return toComplexPath();
    }

}
