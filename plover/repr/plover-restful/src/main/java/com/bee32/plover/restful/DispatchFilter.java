package com.bee32.plover.restful;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.disp.DispatchContext;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.IDispatchContext;
import com.bee32.plover.disp.util.TokenQueue;
import com.bee32.plover.model.IModel;
import com.bee32.plover.model.stage.ModelStage;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.plover.restful.request.ResourceRequest;
import com.bee32.plover.restful.request.ResourceRequestBuilder;
import com.bee32.plover.servlet.container.ServletContainer;

/**
 * The overall modules dispatcher.
 * <p>
 * The dispatch filter can be used in both servlet-mapping and filter-mapping form.
 * <p>
 * In the servlet-mapping form, only path starts with-in a specific namespace will be served by the
 * dispatch filter.
 * <p>
 * In the filter-mapping form, the dispatched namespace is mess up with the ordinary servlets and
 * jsp urls.
 */
public class DispatchFilter
        extends HttpServlet
        implements Filter {

    private static final long serialVersionUID = 1L;

    private ServletContext servletContext;
    protected String contextPath; // Not used.

    private Object root;

    public DispatchFilter() {
        root = ModuleManager.getInstance();
    }

    public Object getRoot() {
        return root;
    }

    public void setRoot(Object root) {
        if (root == null)
            throw new NullPointerException("root");
        this.root = root;
    }

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        servletContext = filterConfig.getServletContext();
        contextPath = servletContext.getContextPath();
    }

    @Override
    public void destroy() {
        servletContext = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            ResourceRequestBuilder requestBuilder = ResourceRequestBuilder.getInstance();
            ResourceRequest rreq = requestBuilder.build(req);

            String pathInfo = rreq.getPath();
            TokenQueue tq = new TokenQueue(pathInfo);

            IDispatchContext rootContext = new DispatchContext(root);
            Dispatcher dispatcher = Dispatcher.getInstance();
            IDispatchContext resultContext;
            try {
                resultContext = dispatcher.dispatch(rootContext, tq);
            } catch (DispatchException e) {
                // resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
                throw new ServletException(e.getMessage(), e);
            }

            if (resultContext == null) {
                chain.doFilter(request, response);
                return;
            }

            Object result = resultContext.getObject();
            // Date expires = resultContext.getExpires();

            if (result == null) {
                // ERROR 404??
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (result != null) {
                //
                result.getClass();

                if (result instanceof IModel) {
                    IModel targetModel = (IModel) result;

                    ServletContainer container = new ServletContainer(servletContext, request, response);
                    ModelStage stage = new ModelStage(container);
                    // ... stage.setView(tq.rest()); ...
                    try {
                        targetModel.stage(stage);
                    } catch (ModelStageException e) {
                        throw new ServletException(e.getMessage(), e);
                    }

                    // stage.getElements() -> Tree-Convert...
                    // display...
                }

                else if (result instanceof Servlet) {
                    Servlet targetServlet = (Servlet) result;
                    targetServlet.service(request, response);
                }

                // else if target.getClass()#doVIEW(req, resp) ...

                // else if side-file target.getClass().getResource(simpleName + ".jelly") ...

                return;
            }
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req);
    }

}
