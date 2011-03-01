package com.bee32.plover.view;

import java.util.Arrays;
import java.util.Collection;

import com.bee32.plover.util.Mime;

public abstract class ContentRenderer
        implements IContentRenderer {

    private final Mime[] supportedContentTypes;

    public ContentRenderer(Mime... supportedContentTypes) {
        if (supportedContentTypes == null)
            throw new NullPointerException("supportedContentTypes");
        this.supportedContentTypes = supportedContentTypes;
    }

    @Override
    public Collection<Mime> getSupportedContentType() {
        return Arrays.asList(supportedContentTypes);
    }

    @Override
    public int getPriority() {
        return 0;
    }

}
