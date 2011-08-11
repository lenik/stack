package com.bee32.plover.orm.context;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.free.FilePath;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bee32.plover.servlet.mvc.MVCConfig;

public class OSIVFilter
        extends OpenSessionInViewFilter {

    boolean enabled = true;

    static Set<String> includeExtensions = new HashSet<String>();
    static Set<String> includePatterns = new HashSet<String>();
    static Set<String> excludePatterns = new HashSet<String>();

    static {
        String mvcExt = MVCConfig.SUFFIX;
        if (mvcExt.startsWith("."))
            mvcExt = mvcExt.substring(1);

        includeExtensions.add(mvcExt);
        includeExtensions.add("jsf"); // See FaceletsConfig.extension

        excludePatterns.add("/javax.faces.resource/"); // myfaces etc. static resources.
    }

    public OSIVFilter() {
        super();
    }

    @Override
    protected SessionFactory lookupSessionFactory() {
        // logger.debug("Lookup OSIV session factory");

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        Map<String, SessionFactory> sessionFactories = wac.getBeansOfType(SessionFactory.class);
        if (sessionFactories.isEmpty()) {
            enabled = false;
            return null;
        }

        return wac.getBean(SessionFactory.class);
    }

    @Override
    protected final boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {

        boolean included = filter(request);
        if (included) {
            logger.debug("OSIV-Request: " + request.getRequestURI());
        }
        return !included;
    }

    protected boolean filter(HttpServletRequest request) {
        if (!enabled)
            return false;

        // Should enable for .jsf, .do.

        String uri = request.getRequestURI();
        String extension = FilePath.getExtension(uri);

        for (String include : includePatterns)
            if (uri.contains(include))
                return true;

        for (String exclude : excludePatterns)
            if (uri.contains(exclude))
                return false;

        if (includeExtensions.contains(extension))
            return true;

        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        super.doFilterInternal(request, response, filterChain);
    }

}