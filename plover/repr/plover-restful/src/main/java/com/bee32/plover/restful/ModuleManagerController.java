package com.bee32.plover.restful;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.arch.IModuleLoader;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.pub.oid.OidVector;
import com.bee32.plover.restful.util.IRestfulController;

@Lazy
@Controller
public class ModuleManagerController
        implements IRestfulController {

    @Inject
    IModuleLoader moduleLoader;

    public void index(IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {

        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        out.println("<html>");
        out.println("    <head>");
        out.println("        <title>Available Modules</title>");
        out.println("    </head>");
        out.println("    <body>");

        out.println("        <h1>Available Modules</h1>");
        out.println("        <strong>Loaded from: " + moduleLoader + "</strong>");

        out.println("        <h2>Directory</h2>");
        out.println("        <hr />");
        out.println("");
        out.println("<ul>");

        List<IModule> sorted = new ArrayList<IModule>();
        for (IModule module : moduleLoader.getModules())
            sorted.add(module);

        // Sort by module name.
        Collections.sort(sorted, new Comparator<IModule>() {
            @Override
            public int compare(IModule o1, IModule o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (IModule module : sorted) {
            OidVector oid = OidUtil.getOid(module.getClass());

            String name = module.getName();

            String path = oid.toPath();
            out.println("<li>" + name + ":");
            out.println("  <a href=\"" + path + "\">about</a>");
            out.println("  <a href=\"" + path + "/\">index</a>");
        }

        out.println("</ul>");

        out.println("");
        out.println("        <hr />");
        out.println("(C) Copyright 2010-2011, BEE32.com");
    }

}
