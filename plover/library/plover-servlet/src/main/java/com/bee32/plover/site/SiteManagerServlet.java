package com.bee32.plover.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.util.PrettyPrintStream;

public class SiteManagerServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI(); // no params
        String cmdname;
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash != -1)
            cmdname = uri;
        else
            cmdname = uri.substring(lastSlash + 1);

        Map<String, ?> _map = req.getParameterMap();
        TextMap args = new TextMap(_map);

        PrintWriter out = resp.getWriter();
        out.println();
    }

    static Map<String, Class<? extends SiteTemplate>> tmap;
    static {
        tmap.put("create", Create.class);
    }

    class Create
            extends SiteTemplate {

        public Create(TextMap args) {
            super(args);
        }

    }

    public void about() {
        //
    }

    public void about(SiteInstance site, TextMap map, PrettyPrintStream out) {
        String label = map.getString("label");
        if (label != null) {
            label = label.trim();
            site.setProperty("label", label);
        }

        simpleForm(out, "label", "Label", label);
    }

    protected void simpleForm(PrettyPrintStream out, Object... entries) {

    }

}
