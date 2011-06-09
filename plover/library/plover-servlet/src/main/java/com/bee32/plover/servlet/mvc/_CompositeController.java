package com.bee32.plover.servlet.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.mvc.AbstractController;

//@org.springframework.stereotype.Controller
@Lazy
public abstract class _CompositeController
        extends AbstractController {

    protected final String _prefix;

    /**
     * Get the prefix string from static field PREFIX.
     */
    protected _CompositeController() {
        this(null);
    }

    /**
     * @param prefix
     *            <code>null</code> to get from static field PREFIX.
     */
    protected _CompositeController(String prefix) {
        if (prefix == null)
            try {
                Field prefixField = getClass().getDeclaredField("PREFIX");

                int modifiers = prefixField.getModifiers();
                if (!Modifier.isStatic(modifiers))
                    throw new Error(prefixField + " must be static");

                prefix = (String) prefixField.get(null);
            } catch (Exception e) {
                throw new Error("PREFIX isn't defined in " + getClass());
            }
        this._prefix = prefix;
    }

    static String getRequestPath(HttpServletRequest req) {
        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();
        assert requestURI.startsWith(contextPath);
        String contextLocalPath = requestURI.substring(contextPath.length());
        return contextLocalPath;
    }

}
