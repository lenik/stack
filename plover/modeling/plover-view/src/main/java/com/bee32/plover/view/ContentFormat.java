package com.bee32.plover.view;

import java.util.TreeSet;

import com.bee32.plover.arch.Component;
import com.bee32.plover.util.Mime;

public class ContentFormat
        extends Component
        implements IContentFormat {

    private final Mime contentType;
    private final TreeSet<IContentRenderer> renderers;

    public ContentFormat(Mime contentType) {
        this(contentType.getName(), contentType);
    }

    public ContentFormat(String name, Mime contentType) {
        super(name);

        if (contentType == null)
            throw new NullPointerException("contentType");

        this.contentType = contentType;
        this.renderers = new TreeSet<IContentRenderer>(RendererComparator.getInstance());
    }

    @Override
    public Mime getContentType() {
        return contentType;
    }

    public void addRenderer(IContentRenderer renderer) {
        renderers.add(renderer);
    }

    public void removeRenderer(IContentRenderer renderer) {
        renderers.remove(renderer);
    }

    @Override
    public IContentRenderer getRenderer(Object obj) {
        for (IContentRenderer renderer : renderers) {
            if (renderer.isRenderable(obj))
                return renderer;
        }
        return null;
    }

}
