package com.bee32.plover.faces.tag;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class DisableHandler
        extends TagHandler {

    final TagAttribute _applied;

    public DisableHandler(TagConfig config) {
        super(config);
        _applied = getAttribute("applied");
    }

    @Override
    public void apply(FaceletContext ctx, UIComponent parent)
            throws IOException {
        nextHandler.apply(ctx, parent);
        boolean applied = _applied.getBoolean(ctx);
        if (applied) {

        }
    }

}
