package com.bee32.plover.servlet.rabbits;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.free.FilePath;
import javax.free.OutputStreamTarget;
import javax.free.URLResource;
import javax.free.UnexpectedException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.DefaultServlet_WF;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

import com.bee32.plover.util.Mime;

/**
 * This servlet should be served as the default servlet, which means to replace the default one.
 */
public class OverlappedResourceServlet
        extends DefaultServlet_WF {

    private static final long serialVersionUID = 1L;

    static Logger logger = Log.getLogger(OverlappedResourceServlet.class);

    public OverlappedResourceServlet() {
        super();
    }

    static final boolean listIndex;
    static {
        listIndex = false;
    }

    /**
     * @see OverlappedContextHandler#getResource(String)
     * @see OverlappedBases#searchResource(String)
     */
    @Override
    public Resource getResource(String pathInContext) {
        logger.debug("Get overlapped resource: " + pathInContext);

        URL resourceUrl = OverlappedBases.searchResource(pathInContext);

        // Not in search-bases, fallback to the default one (which is resource-base based).
        if (resourceUrl == null) {
            // .jsf => .xhtml
            if (pathInContext.endsWith(".jsf")) {
                String xhtmlPath = pathInContext.substring(0, pathInContext.length() - 4) + ".xhtml";
                URL xhtmlUrl = OverlappedBases.searchResource(xhtmlPath);
                if (xhtmlUrl != null) {
                    String _url = xhtmlUrl.toString();
                    _url = _url.substring(0, _url.length() - 6) + ".jsf";
                    try {
                        resourceUrl = new URL(_url);
                    } catch (MalformedURLException e) {
                        throw new UnexpectedException("URL subst should work for: " + _url, e);
                    }
                }
            }

            if (resourceUrl == null)
                return null;
            else {
                logger.debug("Resolved as servlet path: " + resourceUrl);
            }
        }

        Resource resource;
        try {
            resource = Resource.newResource(resourceUrl);
            // logger.debug("    => " + resource);
        } catch (IOException e) {
            logger.ignore(e);
            return null;
        }

        return resource;
    }

    /**
     * Local HTTP-Get Implementation. (Not used)
     */
    // @Override
    protected void _doGet(HttpServletRequest req, HttpServletResponse resp)
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
            String contentType = mime.getContentType();
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
