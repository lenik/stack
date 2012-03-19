package com.bee32.plover.faces.primefaces;

import org.primefaces.component.push.Push;

/**
 * This plover:push is a modified version of PrimeFaces p:push, which overrides the renderer to
 * utilize dynamic push-server address.
 */
public class PloverPush
        extends Push {

    public static final String COMPONENT_TYPE = "plover.faces.Push";
    public static final String DEFAULT_RENDERER_TYPE = "plover.faces.PushRenderer";

    public PloverPush() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

}
