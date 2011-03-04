package com.bee32.plover.restful.request;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.restful.Verb;
import com.bee32.plover.util.Mime;

public interface IRestfulRequest
        extends HttpServletRequest {

    /**
     * The resource path.
     *
     * @return Non-<code>null</code> resource path (without any context-uri).
     */
    String getDispatchPath();

    // IDispatchContext getDispatchContext();

    /**
     * The verb on resource.
     *
     * @return Non-<code>null</code> verb literal on the resource.
     */
    Verb getVerb();

    /**
     * The profile to be staged.
     *
     * @return Non-<code>null</code> profile literal.
     */
    Profile getProfile();

    /**
     * The output format.
     */
    Mime getTargetContentType();

}
