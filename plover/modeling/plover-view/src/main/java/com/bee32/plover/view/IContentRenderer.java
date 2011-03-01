package com.bee32.plover.view;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.util.Mime;

/**
 * The object to be renderred is passed to the {@link #render(Object, IDisplay)} method.
 *
 * For other information gathered from the request (like {@link HttpServletRequest} and other
 * resource-request objects), you should process those information and pass down to the concrete
 * classes' constructors. Or initialize the instance with the appropriate request informations.
 */
public interface IContentRenderer {

    /**
     * The content type this renderer preferred.
     *
     * @return Non-<code>null</code> mime literal.
     */
    Collection<Mime> getSupportedContentType();

    int getPriority();

    boolean isRenderable(Object obj);

    /**
     * The render implementation.
     */
    void render(Object obj, IDisplay display)
            throws RenderException;

}
