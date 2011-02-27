package com.bee32.plover.restful.request;

import javax.servlet.ServletRequest;

import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.restful.Verb;

public interface IResourceRequest {

    /**
     * The original servlet request.
     *
     * @return The servlet request object, or <code>null</code> if non-applicable.
     */
    ServletRequest getServletRequest();

    /**
     * The verb on resource.
     *
     * @return Non-<code>null</code> verb literal on the resource.
     */
    Verb getVerb();

    /**
     * The resource path.
     *
     * @return Non-<code>null</code> resource path (without any context-uri).
     */
    String getPath();

    /**
     * The profile to be staged.
     *
     * @return Non-<code>null</code> profile literal.
     */
    Profile getProfile();

    /**
     * The output format.
     */
    // Format getFormat();

    /**
     * @see ServletRequest#getParameter(String)
     */
    String getParameter(String name);

    /**
     * @see ServletRequest#getParameterValues(String)
     */
    String[] getParameterValues(String name);

}
