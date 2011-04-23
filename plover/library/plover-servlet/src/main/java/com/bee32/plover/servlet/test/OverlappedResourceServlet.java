package com.bee32.plover.servlet.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.free.FilePath;
import javax.free.OutputStreamTarget;
import javax.free.URLResource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.util.Mime;

/**
 * This servlet should be served as the default servlet, which means to replace the default one.
 */
public class OverlappedResourceServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(OverlappedResourceServlet.class);

    public OverlappedResourceServlet() {
        super();
    }

    static final boolean listIndex;
    static {
        listIndex = false;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getServletPath();

        logger.debug("Get overlapped resource: " + path);

        if (path.startsWith("/"))
            path = path.substring(1);

        URL resourceUrl = OverlappedBases.searchResource(path);

        if (resourceUrl == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        URLResource resource = new URLResource(resourceUrl);

        if (resourceUrl.getPath().endsWith("/")) {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");

            String dirName = req.getRequestURI();

            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<head><title>" + "Index of " + dirName + "</title></head>");
            out.println("<body>");
            out.println("<h1>" + "Index of " + dirName + "</h1>");
            out.println("<hr />");

            for (String baseName : resource.forRead().listLines()) {
                baseName = baseName.trim();
                if (baseName.isEmpty())
                    continue;

                out.print("<div>");
                out.print("<a href='" + baseName + "'>" + baseName + "</a>");
                out.println("</div>");
            }

            out.println("</body>");
            out.println("</html>");
            return;
        }

        String extension = FilePath.getExtension(path);
        Mime mime = Mime.getInstanceByExtension(extension);

        if (mime != null) {
            String contentType = mime.getName();
            resp.setContentType(contentType);
        }

        // Also guess the encoding?

        ServletOutputStream out = resp.getOutputStream();
        new OutputStreamTarget(out).forWrite().writeBytes(resource);
    }

    static URL searchClassInherited(Class<?> clazz, String path) {
        while (clazz != null) {

            URL resource = clazz.getResource(path);
            if (resource != null)
                return resource;

            clazz = clazz.getSuperclass();
        }

        return null;
    }

}
