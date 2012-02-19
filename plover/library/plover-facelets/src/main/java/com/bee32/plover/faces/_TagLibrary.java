package com.bee32.plover.faces;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.faces.view.facelets.TagHandler;

import org.apache.myfaces.view.facelets.tag.AbstractTagLibrary;

public class _TagLibrary
        extends AbstractTagLibrary {

    public _TagLibrary(String namespace) {
        super(namespace);
    }

    protected final void addComponent(String name, Class<?> componentType, Class<?> rendererType) {
        String renderer = rendererType == null ? null : rendererType.getCanonicalName();
        super.addComponent(name, componentType.getCanonicalName(), renderer);
    }

    protected final void addComponent(String name, Class<?> componentType, Class<?> rendererType,
            Class<? extends TagHandler> handlerType) {
        String renderer = rendererType == null ? null : rendererType.getCanonicalName();
        super.addComponent(name, componentType.getCanonicalName(), renderer, handlerType);
    }

    protected void importFunctions(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            int modifiers = method.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers))
                continue;
            Class<?> returnType = method.getReturnType();
            if (void.class == returnType)
                continue;
            String name = method.getName();
            addFunction(name, method);
        }
    }

}
