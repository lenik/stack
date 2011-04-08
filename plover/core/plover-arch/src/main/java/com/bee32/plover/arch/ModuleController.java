package com.bee32.plover.arch;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModuleController {

    public void index(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        PrintWriter out = resp.getWriter();

        Module module = (Module) req.getAttribute("target");

        out.println("<html>");
        out.println("    <head>");
        out.println("        <title>Module " + module.getName() + "</title>");
        out.println("    </head>");
        out.println("    <body>");
        out.println("        <h1>Module " + module.getName() + "</h1>");
        out.println("        <h2>Entries</h2>");
        out.println("        <hr />");
        out.println("");

        for (String childLoc : module.getChildNames()) {
            Object child = module.getChild(childLoc);
            out.println("        <div>");
            out.println("            " + childLoc + " - " + child);
            out.println("            <a href=\"" + childLoc + "\">contents</a>");
            out.println("            <a href=\"" + childLoc + "/\">index</a>");
            out.println("        </div>");
        }

        out.println("        <hr />");
        out.println("        <div>");
        out.println("        <a href=\"/\">INDEX</a>");
        out.println("        </div>");
        out.println("        <a href=\"credit\">CREDIT</a>");
        out.println("    </body>");
        out.println("</html>");
    }

}
