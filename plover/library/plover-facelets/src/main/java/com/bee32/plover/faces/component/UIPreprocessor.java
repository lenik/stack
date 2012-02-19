package com.bee32.plover.faces.component;

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.render.RenderKit;

public abstract class UIPreprocessor
        extends HtmlPanelGroup {

    public static final String COMPONENT_FAMILY = "plover.faces.preprocessor";
    public static final String DEFAULT_RENDERER_TYPE = "javax.faces.Group";

    /**
     * The actual renderer = rendererKit.getRenderer(component-family, renderer-type).
     *
     * @see RenderKit#getRenderer(String, String)
     */
    public UIPreprocessor() {
        setRendererType(DEFAULT_RENDERER_TYPE); // The predefined renderer type.
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

}
