package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeSet;

import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.restful.RestfulRequest;
import com.bee32.plover.util.Mime;

public class RestfulRequestBuilder {

    private static Map<Character, ISuffixDissolver> suffixDissolvers;

    private static MethodSuffixDissolver methodSuffixDissolver = MethodSuffixDissolver.INSTANCE;
    private static ContentTypeDissolver contentTypeDissolver = ContentTypeDissolver.INSTANCE;

    static {
        suffixDissolvers = new HashMap<Character, ISuffixDissolver>();
        suffixDissolvers.put('*', methodSuffixDissolver);
        suffixDissolvers.put('.', contentTypeDissolver);
    }

    static TreeSet<IRequestPreprocessor> preprocessors;

    static void reloadServices() {
        ServiceLoader<IRequestPreprocessor> preprocessorServices = ServiceLoader.load(IRequestPreprocessor.class);

        preprocessors = new TreeSet<IRequestPreprocessor>(RequestPreprocessorComparator.getInstance());

        for (IRequestPreprocessor preprocessor : preprocessorServices) {
            preprocessors.add(preprocessor);
        }
        if (preprocessors.isEmpty())
            preprocessors = null;
    }

    static {
        reloadServices();
    }

    public RestfulRequest build(HttpServletRequest _request) {

        RestfulRequest request = new RestfulRequest(_request);

        // Translate http method
        String method = _request.getParameter(".method");
        if (method == null) {
            method = _request.getParameter("method");
            if (method == null) {
                method = _request.getParameter("X");
                if (method == null)
                    method = HttpMethodNames.getMethodName(_request.getMethod());
            }
        }
        request.setMethod(method);

        String view = _request.getParameter(".view");
        if (view == null) {
            view = _request.getParameter("view");
            if (view == null)
                view = _request.getParameter("Y");
        }
        request.setView(view);

        // Content-type by browser.
        String acceptContentType = _request.getHeader("Accept-Content-Type");
        if (acceptContentType != null) {
            Mime contentType = Mime.getInstance(acceptContentType);
            if (contentType != null)
                request.setTargetContentType(contentType);
        }

        String path = _request.getPathInfo();
        //if (path == null)
            path = _request.getRequestURI();

        // boolean endsWithSlash = path.endsWith("/");
        // if (endsWithSlash) profile = INDEX;

        int lastSlash = path.lastIndexOf('/');
        assert lastSlash != -1;
        String dirName = path.substring(0, lastSlash);
        String baseName = path.substring(lastSlash + 1);

        SuffixTokenizer baseTokens = new SuffixTokenizer(baseName, true);

        // Remove the first token as the base stem
        String baseStem = baseTokens.next();

        String pathStem = dirName;
        if (baseStem.isEmpty())
            request.setPreferredView("index");
        else
            pathStem += "/" + baseStem;
        request.setDispatchPath(pathStem);

        // Process the rest tokens
        while (baseTokens.hasNext()) {
            String suffix = baseTokens.next();

            char delim = suffix.charAt(0);
            ISuffixDissolver dissolver = suffixDissolvers.get(delim);
            if (dissolver == null)
                throw new IllegalUsageException("Bad suffix: " + suffix);

            String suffixName = suffix.substring(1);
            dissolver.dissolveSuffix(suffixName, request);
        }

        // other preprocessors.
        if (preprocessors != null) {
            for (IRequestPreprocessor preprocessor : preprocessors)
                preprocessor.preprocess(request);
        }

        return request;
    }

    private static final RestfulRequestBuilder instance = new RestfulRequestBuilder();

    public static RestfulRequestBuilder getInstance() {
        return instance;
    }

}
