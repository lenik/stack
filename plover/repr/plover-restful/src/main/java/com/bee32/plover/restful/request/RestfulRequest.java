package com.bee32.plover.restful.request;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.disp.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.disp.util.TokenQueue;
import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.model.profile.StandardProfiles;
import com.bee32.plover.restful.Verb;
import com.bee32.plover.servlet.util.ModifiableHttpServletRequest;
import com.bee32.plover.util.Mime;
import com.bee32.plover.util.Mimes;

public class RestfulRequest
        extends ModifiableHttpServletRequest
        implements IRestfulRequest, Serializable {

    private static final long serialVersionUID = 1L;

    private Verb verb;

    private String path;
    private ITokenQueue tokenQueue;
    private IArrival arrival;

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

    public void setDispatchPath(String path) {
        this.path = path;
        if (path == null)
            tokenQueue = null;
        else
            tokenQueue = new TokenQueue(path);
    }

    @Override
    public ITokenQueue getTokenQueue() {
        return tokenQueue;
    }

    @Override
    public IArrival getArrival() {
        return arrival;
    }

    public void setArrival(IArrival arrival) {
        this.arrival = arrival;
    }

    @Override
    public <T> T getTarget() {
        if (arrival == null)
            return null;
        return (T) arrival.getTarget();
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
        StringBuilder buf = new StringBuilder();

        buf.append(path);

        if (profile != StandardProfiles.CONTENT) {
            buf.append('~');
            buf.append(profile);
        }

        if (verb != null) {
            // int level = verb.getLevel();
            // while (level-- > 0)
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
