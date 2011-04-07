package com.bee32.plover.restful;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.util.Mime;

public interface IRestfulRequest
        extends HttpServletRequest {

    /**
     * The resource path.
     *
     * @return Non-<code>null</code> resource path (without any context-uri).
     */
    String getDispatchPath();

    /**
     * The verb on resource.
     *
     * @return Non-<code>null</code> verb literal on the resource.
     */
    String getMethod();

    /**
     * The MIME type for the desired output.
     *
     * @return Non-<code>null</code> MIME literal.
     */
    Mime getTargetContentType();

    // Stateful extension

    /**
     * The token queue being processed.
     *
     * When the dispatch started, the token queue contains tokens to be dispatched, and after
     * dispatch is completed, all processed tokens are consumed, rest only the unprocessed tokens.
     *
     * @return The token queue object.
     */
    ITokenQueue getTokenQueue();

    /**
     * Get the arrival information from dispatch process.
     */
    IArrival getArrival();

    /**
     * The same as arrival.target.
     */
    <T> T getTarget();

    /**
     * The same as arrival.restPath.
     */
    String getRestPath();

}
