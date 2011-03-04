package com.bee32.plover.restful.request;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.model.profile.StandardProfiles;
import com.bee32.plover.restful.Verb;
import com.bee32.plover.restful.Verbs;
import com.bee32.plover.servlet.util.ModifiableHttpServletRequest;
import com.bee32.plover.util.Mime;
import com.bee32.plover.util.Mimes;

public class RestfulRequest
        extends ModifiableHttpServletRequest
        implements IRestfulRequest, Serializable {

    private static final long serialVersionUID = 1L;

    private Verb verb = Verbs.GET;
    private String path;
    private Profile profile = StandardProfiles.CONTENT;
    private Mime contentType = Mimes.text_html;

    public RestfulRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Verb getVerb() {
        return verb;
    }

    public void setVerb(Verb verb) {
        this.verb = verb;
    }

    @Override
    public String getDispatchPath() {
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
    public Mime getTargetContentType() {
        return contentType;
    }

    public void setTargetContentType(Mime contentType) {
        this.contentType = contentType;
    }

    public String getRestfulPath() {
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
        return getRestfulPath();
    }

}
