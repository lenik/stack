package com.bee32.plover.faces;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.myfaces.view.facelets.tag.AbstractTagLibrary;

import com.bee32.plover.faces.el.CoreConstants;
import com.bee32.plover.faces.el.StringUtil;
import com.bee32.plover.internet.spam.EmailUtil;
import com.bee32.plover.util.zh.ZhUtil;

/**
 * Suggested ns prefix: "pc".
 */
public class CoreLibrary
        extends AbstractTagLibrary {

    public static String NAMESPACE = "http://bee32.com/plover/core";

    public CoreLibrary() {
        super(NAMESPACE);

        importFunctions(CoreConstants.class);
        importFunctions(StringUtil.class);
        importFunctions(EmailUtil.class);
        importFunctions(ZhUtil.class);
    }

    void importFunctions(Class<?> clazz) {
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
