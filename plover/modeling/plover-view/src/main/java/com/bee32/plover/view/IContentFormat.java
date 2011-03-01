package com.bee32.plover.view;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.util.Mime;

public interface IContentFormat
        extends IComponent {

    /**
     * Get the content type this format applied.
     *
     * @return Non-<code>null</code> mime literal.
     */
    Mime getContentType();

    /**
     * Find the appropriate renderer for the given object.
     *
     * @return The suitable renderer instance. If none exists, <code>null</code> is returned.
     */
    IContentRenderer getRenderer(Object obj);

}