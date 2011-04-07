package com.bee32.plover.restful;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.IModuleLoader;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;
import com.bee32.plover.restful.util.IRestfulController;

@Controller
@Lazy
public class ModuleManagerController
        implements IRestfulController {

    @Inject
    IModuleLoader moduleLoader;

    public void index(IRestfulRequest req, IRestfulResponse resp)
            throws IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<ul>");

        for (IModule module : moduleLoader.getModules()) {
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
