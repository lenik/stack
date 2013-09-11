package com.bee32.plover.servlet.central;

import java.io.IOException;

import javax.free.Strings;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class PloverServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String name;

    public PloverServlet() {
        Class<?> clazz = ClassUtil.skipProxies(getClass());
        String simpleName = clazz.getSimpleName();
        if (simpleName.endsWith("PloverServlet"))
            simpleName = simpleName.substring(0, simpleName.length() - 13);
        else if (simpleName.endsWith("Servlet"))
            simpleName = simpleName.substring(0, simpleName.length() - 7);

        this.name = Strings.hyphenatize(simpleName);
    }

    public String getName() {
        return name;
    }

    @Override
    public final void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {
        super.service(req, res);
    }

}
