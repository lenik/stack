package com.bee32.plover.scheduler.stat;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.free.FilePath;
import javax.free.UnexpectedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.bee32.plover.scheduler.quartz.QuartzConfig;

public class QuartzMonitorServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri = req.getRequestURI();
        String base = FilePath.getBaseName(uri);

        resp.setBufferSize(4096);
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        PrintWriter out = resp.getWriter();

        try {
            Scheduler sched = QuartzConfig.getScheduler(req);

            if ("stat".equals(base))
                stat(sched, out);

            else
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No such stat function.");

        } catch (SchedulerException e) {
            throw new ServletException("Scheduler exception: " + e.getMessage(), e);
        }
    }

    protected void stat(Scheduler sched, PrintWriter out)
            throws ServletException, IOException {

        BeanInfo schedulerInfo;
        try {
            schedulerInfo = Introspector.getBeanInfo(sched.getClass(), Object.class);
        } catch (IntrospectionException e) {
            throw new UnexpectedException(e.getMessage(), e);
        }

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Quartz Statistics</title>");
        out.println("</head>");

        out.println("<body>");

        out.println("<h1>Scheduler Info</h1>");
        out.println("<table>");
        out.println("<thead>");
        out.println("<th>Property Name</th>");
        out.println("<th>Property Value</th>");
        out.println("</thead>");
        out.println("<tbody>");
        for (PropertyDescriptor property : schedulerInfo.getPropertyDescriptors()) {
            Method getter = property.getReadMethod();
            if (getter == null)
                continue;

            String name = property.getName();
            String valueHtml;
            try {
                Object val = getter.invoke(sched);
                valueHtml = String.valueOf(val);
                valueHtml = valueHtml.replace("&", "&amp;");
                valueHtml = valueHtml.replace("<", "&lt;");
            } catch (Exception e) {
                valueHtml = "<span style='color: red'>Exception: " + e + "</span>";
            }

            out.println("<tr>");
            out.println("<td>" + name + "</td>");
            out.println("<td>" + valueHtml + "</td>");
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");

        out.println("</body>");
        out.println("</html>");
    }

}
