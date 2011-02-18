package com.bee32.plover.restful.request;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.free.UnexpectedException;

import com.bee32.plover.model.view.StandardViews;
import com.bee32.plover.model.view.View;

public class ViewDissolver
        implements IExtensionDissolver {

    private static Map<String, View> viewByKeyword;

    static {
        viewByKeyword = new HashMap<String, View>();

        for (Field field : StandardViews.class.getFields()) {
            if (View.class.isAssignableFrom(field.getType())) {
                View view;
                try {
                    view = (View) field.get(null);
                } catch (IllegalAccessException e) {
                    throw new UnexpectedException(e.getMessage(), e);
                }
                String viewName = view.getName();
                viewByKeyword.put(viewName, view);
            }
        }

        if (viewByKeyword.isEmpty())
            viewByKeyword = null;
    }

    @Override
    public boolean desolveExtension(String extension, RequestModel model) {
        if (extension == null)
            throw new NullPointerException("extension");

        if (viewByKeyword == null)
            return false;

        View view = viewByKeyword.get(extension);
        if (view == null)
            return false;

        model.setView(view);
        return true;
    }

}
