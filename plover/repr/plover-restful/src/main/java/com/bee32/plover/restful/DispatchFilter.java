package com.bee32.plover.restful;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.disp.Arrival;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.restful.context.SimpleApplicationContextUtil;
import com.bee32.plover.restful.request.RestfulRequestBuilder;

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
@ContextConfiguration
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

    protected boolean processOrNot(ServletRequest _request, ServletResponse _response)
            throws IOException, ServletException, RestfulException {

        if (!(_request instanceof HttpServletRequest))
            return false;
        if (!(_response instanceof HttpServletResponse))
            return false;

        // 1, Build the restful-request
        HttpServletRequest request = (HttpServletRequest) _request;
        HttpServletResponse response = (HttpServletResponse) _response;

        RestfulRequestBuilder requestBuilder = RestfulRequestBuilder.getInstance();
        RestfulRequest req = requestBuilder.build(request);
        RestfulResponse resp = new RestfulResponse(response);

        // 2, Path-dispatch
        ITokenQueue tq = req.getTokenQueue();
        IArrival arrival = dispatch(tq);
        if (arrival == null)
            return false;

        // No verb, do GET/READ.

        Object origin = arrival.getTarget();
        // Date expires = resultContext.getExpires();

        if (origin == null) {
            // ERROR 404??
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return true;
        }

        // 3, Render the specific Profile, or default if none.
        if (origin instanceof Servlet) {
            Servlet resultServlet = (Servlet) origin;
            resultServlet.service(_request, _response);
            return true;
        }

        req.setArrival(arrival);

        // origin<method> -> target
        //  -> null: terminate
        // -> object -> object.view ()

        Class<? extends Object> originClass = origin.getClass();
        Object target = null;

        Method method = getControllerMethod(originClass, req.getMethod());
        if (method != null) {

            Class<?> controllerClass = method.getDeclaringClass();
            if (controllerClass != null) {
                Object controller;
                try {
                    // webImpl = StatelessUtil.createOrReuse(webClass);
                    controller = applicationContext.getBean(controllerClass);
                } catch (Exception e) {
                    throw new ServletException(e.getMessage(), e);
                }

                try {
                    target = method.invoke(controller, req, resp);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ServletException(e.getMessage(), e);
                }
            }
        }

        if (target == null)
            target = resp.getTarget();

        throw new NotImplementedException();
    } // processOrNot

    private IArrival dispatch(ITokenQueue tq)
            throws ServletException {
        Object rootObject = root == null ? moduleManager : root;
        if (rootObject == null)
            // throw new IllegalStateException("Root object isn't set");
            rootObject = ModuleManager.getInstance();

        Dispatcher dispatcher = Dispatcher.getInstance();
        IArrival arrival;
        try {
            arrival = dispatcher.dispatch(new Arrival(rootObject), tq);
        } catch (DispatchException e) {
            // resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            throw new ServletException(e.getMessage(), e);
        }
        return arrival;
    }

    static MethodPattern controllerPattern = new MethodPattern(ServletRequest.class, ServletResponse.class);
    static Map<Class<?>, Map<String, Method>> classControllerMethods = new HashMap<Class<?>, Map<String, Method>>();

    static Map<String, Method> getControllerMethods(Class<?> resourceClass) {
        if (resourceClass == null)
            throw new NullPointerException("resourceClass");

        Map<String, Method> methods = classControllerMethods.get(resourceClass);
        if (methods == null) {
            methods = controllerPattern.searchOverlayMethods(resourceClass, "controller");
            classControllerMethods.put(resourceClass, methods);
        }
        return methods;
    }

    static Method getControllerMethod(Class<?> resourceClass, String method) {
        Map<String, Method> map = getControllerMethods(resourceClass);
        return map.get(method);
    }

}
