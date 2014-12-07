package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;

import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.site.IZebraSiteLayout;

public class TopicVbo
        extends AbstractHtmlViewBuilder<Topic>
        implements IZebraSiteAnchors, IZebraSiteLayout {

    public TopicVbo() {
        super(Topic.class);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<Topic> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return ctx;
    }

}
