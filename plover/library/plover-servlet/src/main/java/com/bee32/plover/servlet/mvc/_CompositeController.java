package com.bee32.plover.servlet.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bee32.plover.arch.util.ClassUtil;

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
     *            E.g., "/1/2/3" or "/foo/bar" without the trailing slash.
     *            <p>
     *            Specify <code>null</code> to get from static field PREFIX.
     */
    protected _CompositeController(String prefix) {
        Class<?> clazz = ClassUtil.skipProxies(getClass());
        if (prefix == null)
            try {
                Field prefixField = clazz.getDeclaredField("PREFIX");

                int modifiers = prefixField.getModifiers();
                if (!Modifier.isStatic(modifiers))
                    throw new Error(prefixField + " must be static");

                prefix = (String) prefixField.get(null);
            } catch (Exception e) {
                throw new Error("PREFIX isn't defined in " + clazz);
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

    @Override
    protected final void initApplicationContext()
            throws BeansException {
        initController();
    }

    protected abstract void initController()
            throws BeansException;

}
