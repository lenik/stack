package com.bee32.sem.frame.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.servlet.mvc.ActionHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.plover.servlet.mvc.CompositeController;

@ComponentTemplate
// @Controller
/* @PerSite */@Lazy
public abstract class IndexRichCompatController
        extends CompositeController {

    public static final String INDEX_RICH = "index-rich.jsf";
    public static final String MODE_PARAM = "MODE";

    @Override
    protected void initController()
            throws BeansException {
        addHandler("index", /*      */new CompatRedirectHandler(INDEX_RICH));
        addHandler("content", /*    */new CompatRedirectHandler(INDEX_RICH, MODE_PARAM + "=content"));
        addHandler("createForm", /* */new CompatRedirectHandler(INDEX_RICH, MODE_PARAM + "=createForm"));
        addHandler("editForm", /*   */new CompatRedirectHandler(INDEX_RICH, MODE_PARAM + "=editForm"));
        addHandler("create", /*     */new CompatRedirectHandler(INDEX_RICH, MODE_PARAM + "=create"));
        addHandler("edit", /*       */new CompatRedirectHandler(INDEX_RICH, MODE_PARAM + "=edit"));
        addHandler("delete", /*     */new CompatRedirectHandler(INDEX_RICH, MODE_PARAM + "=delete"));
    }

}

class CompatRedirectHandler
        extends ActionHandler {

    final String base;
    final Map<String, String[]> overridedParameters = new LinkedHashMap<String, String[]>();

    public CompatRedirectHandler(String base) {
        this.base = base;
    }

    public CompatRedirectHandler(String base, String... overrideNameValues) {
        this(base);
        for (String overrideNameValue : overrideNameValues) {
            int eq = overrideNameValue.indexOf('=');
            if (eq == -1)
                throw new IllegalArgumentException("Bad name=value format: " + overrideNameValue);
            String name = overrideNameValue.substring(0, eq);
            String value = overrideNameValue.substring(eq + 1);
            overridedParameters.put(name, new String[] { value });
        }
    }

    @Override
    public ActionResult handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        StringBuilder buf = new StringBuilder();

        Map<String, String[]> reqMap = req.getParameterMap();
        Map<String, String[]> all;
        if (reqMap.isEmpty())
            all = overridedParameters;
        else if (overridedParameters.isEmpty())
            all = reqMap;
        else {
            all = new LinkedHashMap<String, String[]>(overridedParameters);
            for (Entry<String, String[]> param : reqMap.entrySet()) {
                String paramName = param.getKey();
                if (overridedParameters.containsKey(paramName))
                    continue;
                all.put(paramName, param.getValue());
            }
        }

        String uri = req.getRequestURI();
        int lastSlash = uri.lastIndexOf('/');
        if (lastSlash == -1)
            uri = base;
        else
            uri = uri.substring(0, lastSlash) + "/" + base;
        buf.append(uri);

        int n = 0;
        for (Entry<String, String[]> param : all.entrySet()) {
            String paramName = param.getKey();
            String[] paramValues = param.getValue();
            for (String paramValue : paramValues) {
                if (n == 0)
                    buf.append("?");
                else
                    buf.append("&");
                buf.append(paramName);
                buf.append("=");
                buf.append(encodeURI(paramValue));
            }
        }

        result.sendRedirect(buf.toString());
        return result;
    }

    static String encodeURI(Object value) {
        String s = String.valueOf(value);
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
