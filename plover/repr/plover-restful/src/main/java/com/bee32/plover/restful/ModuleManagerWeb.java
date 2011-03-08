package com.bee32.plover.restful;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.ServiceModuleLoader;
import com.bee32.plover.inject.ContextException;
import com.bee32.plover.inject.IContainer;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;
import com.bee32.plover.restful.annotation.WebCase;

public class ModuleManagerWeb
        implements WebCase {

    public void index(IContainer container)
            throws IOException, ContextException {
        final HttpServletRequest req = container.require(HttpServletRequest.class);
        final HttpServletResponse resp = container.require(HttpServletResponse.class);

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<ul>");

        for (IModule module : ServiceModuleLoader.getModules()) {
            OidVector oid = OidUtil.getOid(module.getClass());

            String name = module.getName();

            String path = oid.toPath();
            out.println("<li>" + name + ":");
            out.println("  <a href=\"" + path + "\">about</a>");
            out.println("  <a href=\"" + path + "/\">index</a>");
        }

        out.println("</ul>");
    }

}
