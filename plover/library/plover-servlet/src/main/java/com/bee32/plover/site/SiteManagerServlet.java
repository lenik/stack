package com.bee32.plover.site;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

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

        Map<String, ?> map = req.getParameterMap();
        TextMap textMap = new TextMap(map);
        PrintWriter out = resp.getWriter();

        PrettyPrintStream buf = new PrettyPrintStream();
        template(cmdname, textMap, buf); // ignore retval.

        out.println(buf.toString());
    }

    Object template(String cmdname, TextMap args, PrettyPrintStream out)
            throws ServletException {
        // header..
        try {
            Method method = getClass().getMethod(cmdname, SiteInstance.class, TextMap.class, PrettyPrintStream.class);
            Object retval = method.invoke(this, args, out);
            return retval;
        } catch (ReflectiveOperationException e) {
            throw new ServletException(e.getMessage(), e);
        }
        // footer...
    }

    public void create(SiteInstance site, TextMap map, PrettyPrintStream out) {

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
        out.println("<form>");
        out.enter();
        {
            out.println("<table>");
            out.enter();
            {
                for (int i = 0; i < entries.length; i += 3) {
                    String name = (String) entries[i];
                    String label = (String) entries[i + 1];
                    Object value = entries[i + 2];
                    out.println("<tr>");
                    out.println("<th>" + label + "</th>");
                    out.println("<td>");
                    out.enter();
                    {
                        if (value instanceof String) {
                            String strVal = (String) value;
                            out.print("<input type='text' name='" + name + "' value='");
                            out.print(HtmlUtils.htmlEscape(strVal));
                            out.println("' />");
                        }
                    }
                    out.leave();
                    out.println("</td>");
                }
            }
            out.println("</table>");
        }
        out.leave();
        out.println("</form>");
    }

}
