package com.bee32.plover.restful;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.free.IllegalUsageException;
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

import com.bee32.plover.arch.naming.ReverseLookupRegistry;
import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;
import com.bee32.plover.disp.util.Arrival;
import com.bee32.plover.disp.util.ArrivalBacktraceCallback;
import com.bee32.plover.disp.util.IArrival;
import com.bee32.plover.disp.util.ITokenQueue;
import com.bee32.plover.disp.util.MethodLazyInjector;
import com.bee32.plover.disp.util.MethodPattern;
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
        final HttpServletRequest request = (HttpServletRequest) _request;
        final HttpServletResponse response = (HttpServletResponse) _response;

        RestfulRequestBuilder requestBuilder = RestfulRequestBuilder.getInstance();
        final RestfulRequest req = requestBuilder.build(request);
        final RestfulResponse resp = new RestfulResponse(response);

        // 2, Path-dispatch
        ITokenQueue tq = req.getTokenQueue();
        IArrival arrival = dispatch(tq);
        if (arrival == null)
            return false;
        req.setArrival(arrival);

        final Object origin = arrival.getTarget();

        // 3, origin is a servlet delegate?
        if (origin instanceof Servlet) {
            Servlet resultServlet = (Servlet) origin;
            resultServlet.service(_request, _response);
            return true;
        }

        // 3.1, otherwise execute origin..method-> target

        Object target = null;
        // Date expires = arrival.getExpires();

        class Callback
                implements ArrivalBacktraceCallback<ServletException> {

            @Override
            public boolean arriveBack(IArrival arrival)
                    throws ServletException {

                Object obj = arrival.getTarget();
                Class<?> objClass = obj.getClass();

                req.setArrival(arrival);

                if (doControllerMethod(objClass, req, resp))
                    return true;

                if (doInplaceMethod(objClass, req, resp))
                    return true;

                return false;
            }
        }
        Callback callback = new Callback();

        while (true) {

            if (arrival.backtrace(callback)) {
                String respMethod = resp.getMethod();
                resp.setMethod(null);
                if (respMethod == null)
                    break;

                target = resp.getTarget();
                break; /* Currently recursive method isn't supported */
            }

            // Do the render if no controller method is defined.
            req.setArrival(arrival);

            String view = req.getView();
            if (view == null) {
                view = req.getMethod();
                if (view == null)
                    view = "index";
                req.setView(view);
            }
            doRender(origin, req, resp);
            return true;
        }

        // 4, show the target
        // NOTICE: user should not write to response, if any target is returned.

        // Treat if the response have already been done.
        if (target == null) {
            // assert resp.isCommitted();
            return true;
        }

        if (target instanceof String) {
            String location = (String) target;
            resp.sendRedirect(location);
            return true;
        }

        String location = ReverseLookupRegistry.getInstance().getLocation(target);
        if (location != null) {
            resp.sendRedirect(location);
            return true;
        }

        req.setArrival(arrival);
        doRender(target, req, resp);
        return true;
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

    private boolean doControllerMethod(Class<?> originClass, RestfulRequest req, RestfulResponse resp)
            throws ServletException {
        Method method = getControllerMethod(originClass, req.getMethod());
        if (method == null)
            return false;

        // Get the controller instance.
        Class<?> controllerClass = method.getDeclaringClass();
        assert controllerClass != null;

        Object controller;
        try {
            // webImpl = StatelessUtil.createOrReuse(webClass);
            controller = applicationContext.getBean(controllerClass);
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e);
        }

        Object target;
        try {
            target = method.invoke(controller, req, resp);
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e);
        }

        if (target != null)
            resp.setTarget(target);
        return true;
    }

    private boolean doInplaceMethod(Object origin, final RestfulRequest req, final RestfulResponse resp)
            throws ServletException {
        Class<?> originClass = origin.getClass();
        final Method singleMethod = getSingleMethod(originClass, req.getMethod());
        if (singleMethod == null)
            return false;

        MethodLazyInjector injector = new MethodLazyInjector() {
            @Override
            protected Object require(Class<?> declType) {
                if (ServletRequest.class.isAssignableFrom(declType))
                    return req;

                if (ServletResponse.class.isAssignableFrom(declType))
                    return resp;

                throw new IllegalUsageException("Don't know how to inject parameter " + declType + " in "
                        + singleMethod);
            }
        };

        Object target;
        try {
            target = injector.invoke(origin, singleMethod);
        } catch (Exception e) {
            throw new ServletException(e.getMessage(), e);
        }

        if (target != null)
            resp.setTarget(target);
        return true;
    }

    /**
     * @throws IOException
     * @throws RestfulException
     */
    private void doRender(Object object, IRestfulRequest req, IRestfulResponse resp)
            throws IOException, RestfulException {
        Class<?> clazz = object.getClass();
        boolean handled = false;

        for (IRestfulView view : RestfulViewFactory.getViews()) {
            if (view.render(clazz, object, req, resp)) {
                handled = true;
                break;
            }
        }

        if (!handled)
            throw new RestfulException("No available view for " + clazz);
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

    static Map<Class<?>, Map<String, Method>> classSingleMethods = new HashMap<Class<?>, Map<String, Method>>();

    static Method getSingleMethod(Class<?> clazz, String name) {
        Map<String, Method> singleMethods = classSingleMethods.get(clazz);
        if (singleMethods == null) {
            synchronized (classSingleMethods) {
                singleMethods = classSingleMethods.get(clazz);
                if (singleMethods == null) {
                    singleMethods = new HashMap<String, Method>();

                    Set<String> nameset = new HashSet<String>();
                    for (Method method : clazz.getMethods()) {
                        String _name = method.getName();
                        if (nameset.add(_name))
                            singleMethods.put(_name, method);
                        else
                            singleMethods.remove(method);
                    }

                    classSingleMethods.put(clazz, singleMethods);
                }
            }
        }

        return singleMethods.get(name);
    }

}
