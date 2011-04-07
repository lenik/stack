package com.bee32.plover.restful;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.disp.util.TokenQueue;
import com.bee32.plover.util.Mime;
import com.bee32.plover.util.Mimes;

public class RestfulRequest
        extends HttpServletRequestWrapper
        implements IRestfulRequest, Serializable {

    private static final long serialVersionUID = 1L;

    private String method;
    private String path;
    private ITokenQueue tokenQueue;
    private IArrival arrival;

    private Mime contentType = Mimes.text_html;

    public RestfulRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public Mime getTargetContentType() {
        return contentType;
    }

    public void setTargetContentType(Mime contentType) {
        this.contentType = contentType;
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
    public String getRestPath() {
        if (arrival == null)
            return null;
        return arrival.getRestPath();
    }

    public String getRestfulPath() {
        StringBuilder buf = new StringBuilder();

        buf.append(path);

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
