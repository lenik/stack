package com.bee32.plover.restful;

import java.io.IOException;
import java.io.PrintWriter;

import javax.free.NotImplementedException;
import javax.inject.Inject;
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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import overlay.OverlayUtil;

import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.OperationFusion;
import com.bee32.plover.disp.DispatchContext;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.IDispatchContext;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.model.IModel;
import com.bee32.plover.model.profile.Profile;
import com.bee32.plover.model.stage.ModelStage;
import com.bee32.plover.model.stage.ModelStageException;
import com.bee32.plover.restful.context.SimpleApplicationContextUtil;
import com.bee32.plover.restful.request.RestfulRequest;
import com.bee32.plover.restful.request.RestfulRequestBuilder;
import com.bee32.plover.servlet.context.ServletContainer;
import com.bee32.plover.velocity.Velocity;

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
        implements Filter, InitializingBean {

    private static final long serialVersionUID = 1L;

    private ServletContext servletContext;
    protected String contextPath; // Not used.

    private transient Object root;

    public DispatchFilter() {
    }

    @Inject
    ApplicationContext applicationContext;

    @Inject
    private transient ModuleManager moduleManager;

    @Override
    public void afterPropertiesSet()
            throws Exception {
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

        ApplicationContext context = SimpleApplicationContextUtil.getApplicationContext(servletContext);
        if (context == null)
            throw new ServletException("Application context isn't set up");

        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }

    @Override
    public void destroy() {
        servletContext = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            if (processOrNot(request, response))
                return;
        } catch (RestfulException e) {
            throw new ServletException(e.getMessage(), e);
        }

        chain.doFilter(request, response);
    }

    protected boolean processOrNot(ServletRequest request, ServletResponse response)
            throws IOException, ServletException, RestfulException {

        if (!(request instanceof HttpServletRequest))
            return false;

        // 1, Build the restful-request
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        RestfulRequestBuilder requestBuilder = RestfulRequestBuilder.getInstance();
        RestfulRequest rreq = requestBuilder.build(req);

        // 2, Path-dispatch
        ITokenQueue tq = rreq.getTokenQueue();

        Object rootObject = root == null ? moduleManager : root;
        if (rootObject == null)
            // throw new IllegalStateException("Root object isn't set");
            rootObject = ModuleManager.getInstance();

        IDispatchContext rootContext = new DispatchContext(rootObject);
        Dispatcher dispatcher = Dispatcher.getInstance();
        IDispatchContext resultContext;
        try {
            resultContext = dispatcher.dispatch(rootContext, tq);
        } catch (DispatchException e) {
            // resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            throw new ServletException(e.getMessage(), e);
        }

        if (resultContext == null)
            return false;

        HttpRequestContext requestContext = new HttpRequestContext(req, resp);

        Verb verb = rreq.getVerb();

        if (verb != null) {
            Object verbImpl;
            Object redirectObject;

            try {
                verbImpl = verb.operate(resultContext, requestContext);
            } catch (UnsupportedVerbException e) {
                throw e;
            } catch (Exception e) {
                throw new RestfulException(e.getMessage(), e);
            }

            redirectObject = requestContext.getReturnValue();

            if (redirectObject == null)
                redirectObject = resultContext.getReachedObject(); // verbImpl?

            String message = "Operation done";

            if (redirectObject != null) {
                if (redirectObject instanceof String)
                    message = (String) redirectObject;

                // redirect...
                String relativeLocation = ReverseLookupRegistry.getInstance().getLocation(redirectObject);
                if (relativeLocation != null) {
                    String location = req.getContextPath() + "/" + relativeLocation;
                    resp.sendRedirect(location);
                    return true;
                }
            }

            resp.getWriter().println(message);
            resp.flushBuffer();
            return true;
        }

        // No verb, do GET/READ.

        Object result = resultContext.getObject();
        // Date expires = resultContext.getExpires();

        if (result == null) {
            // ERROR 404??
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return true;
        }

        rreq.setDispatchContext(resultContext);

        // 3, Render the specific Profile, or default if none.
        if (resp.isCommitted())
            return true;

        String profileName = "content";
        Profile profile = rreq.getProfile();
        if (profile != null)
            profileName = profile.getName();

        Class<? extends Object> resultClass = result.getClass();
        Class<?> webClass = OverlayUtil.getOverlay(resultClass, "web");
        if (webClass != null) {
            Object webImpl;
            try {
                // webImpl = StatelessUtil.createOrReuse(webClass);
                webImpl = applicationContext.getAutowireCapableBeanFactory().createBean(webClass);
            } catch (Exception e) {
                throw new ServletException(e.getMessage(), e);
            }

            OperationFusion fusion = OperationFusion.getInstance();
            IOperation profileOperation = fusion.getOperation(webImpl, profileName);

            if (profileOperation != null) {
                try {
                    // XXX - Return value is ignored here.
                    profileOperation.execute(webImpl, requestContext);
                } catch (ServletException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ServletException(e.getMessage(), e);
                }
                return true;
            }
        }

        Template template = Velocity.getTemplate(resultClass, profileName);
        if (template != null) {
            VelocityContext context = new VelocityContext();
            context.put("it", result);
            PrintWriter writer = resp.getWriter();
            template.merge(context, writer);
            writer.flush();
            return true;
        }

        if (!skipped) {
            if (result instanceof Servlet) {
                Servlet resultServlet = (Servlet) result;
                resultServlet.service(request, response);
                return true;
            }

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

            throw new NotImplementedException();
        } // skipped

        return false;
    } // processOrNot

    static boolean skipped = true;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            if (!processOrNot(req, resp)) {
                // Dispatched to nothing.
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (RestfulException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

}
