package com.bee32.ape.html.apex.beans;

import java.util.ArrayList;
import java.util.List;

import org.activiti.explorer.ui.form.BooleanFormPropertyRenderer;
import org.activiti.explorer.ui.form.DateFormPropertyRenderer;
import org.activiti.explorer.ui.form.EnumFormPropertyRenderer;
import org.activiti.explorer.ui.form.FormPropertyRenderer;
import org.activiti.explorer.ui.form.FormPropertyRendererManager;
import org.activiti.explorer.ui.form.LongFormPropertyRenderer;
import org.activiti.explorer.ui.form.StringFormPropertyRenderer;
import org.activiti.explorer.ui.form.UserFormPropertyRenderer;

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
