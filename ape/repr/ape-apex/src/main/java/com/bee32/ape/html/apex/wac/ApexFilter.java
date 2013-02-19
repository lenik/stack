package com.bee32.ape.html.apex.wac;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.bee32.ape.graph.ApeGraphModule;
import com.bee32.ape.html.apex.ApexModule;

public class ApexFilter
        implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        // HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());
        String rewrite = path;

        if (path.startsWith(ApexModule.PREFIX_)) {
            String apexSpec = path.substring(ApexModule.PREFIX_.length());

            int slash = apexSpec.indexOf('/');
            String head;
            String tail;
            if (slash == -1) {
                head = apexSpec;
                tail = "";
            } else {
                head = apexSpec.substring(0, slash);
                tail = apexSpec.substring(slash);
            }

            switch (head) {
            case "service":
                rewrite = ApeGraphModule.PREFIX + tail;
                // System.out.println("REQ: " + path + " => " + rewrite);
                // resp.sendRedirect(rewrite);
                break;
            case "api":
            case "editor":
            case "explorer":
            case "libs":
                rewrite = "/" + apexSpec;
                break;
            }
        }

        // else {
        // String apexParentPrefix_ = "/3/30/3/";
        // if (path.startsWith(apexParentPrefix_)) {
        // if (!path.startsWith(ApeGraphModule.PREFIX)) {
        // String appSpec = path.substring(apexParentPrefix_.length());
        // rewrite = "/" + appSpec;
        // }
        // }
        // }

        if (rewrite == path) {
            // System.out.println("REQ: " + path);
            chain.doFilter(request, response);
        } else {
            // System.out.println("REQ: " + path + " -> " + rewrite);
            request.getRequestDispatcher(rewrite).forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
