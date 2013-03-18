package com.bee32.ape.html.apex.beans;

import java.util.ArrayList;
import java.util.List;

import org.activiti.explorer.ui.form.*;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite(reason = "stated: renderers is modifiable.")
public class ApexFormPropertyRendererManager
        extends FormPropertyRendererManager {

    private static final long serialVersionUID = 1L;

    public ApexFormPropertyRendererManager() {
        FormPropertyRenderer noTypePropertyRenderer = new StringFormPropertyRenderer();
        setNoTypePropertyRenderer(noTypePropertyRenderer);

        List<FormPropertyRenderer> renderers = new ArrayList<>();

        renderers.add(new StringFormPropertyRenderer());
        renderers.add(new EnumFormPropertyRenderer());
        renderers.add(new LongFormPropertyRenderer());
        renderers.add(new DateFormPropertyRenderer());
        renderers.add(new UserFormPropertyRenderer());
        renderers.add(new BooleanFormPropertyRenderer());

        setPropertyRenderers(renderers);
    }

}
