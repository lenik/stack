package com.bee32.plover.restful.request;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.TreeSet;

import javax.free.StringPart;
import javax.servlet.http.HttpServletRequest;

public class RequestModelBuilder {

    private static IMethodDissolver methodDissolver;
    private static List<IExtensionDissolver> extensionDissolvers;

    static {
        methodDissolver = new MethodDissolver();
        extensionDissolvers = new ArrayList<IExtensionDissolver>();
        extensionDissolvers.add(new RendererDissolver());
    }

    static TreeSet<IRequestPreprocessor> preprocessors;

    static void reloadServices() {
        ServiceLoader<IRequestPreprocessor> preprocessorServices = ServiceLoader.load(IRequestPreprocessor.class);
        for (IRequestPreprocessor preprocessor : preprocessorServices) {
            preprocessors.add(preprocessor);
        }
        if (preprocessors.isEmpty())
            preprocessors = null;
    }

    static {
        reloadServices();
    }

    public RequestModel build(HttpServletRequest request) {
        RequestModel model = new RequestModel(request);

        String httpMethod = request.getMethod();
        methodDissolver.desolveMethod(httpMethod, model);

        String uri = request.getRequestURI();
        String baseName = StringPart.afterLast(uri, '/');

        int end = baseName.length();
        int dot;
        while ((dot = baseName.lastIndexOf('.', end - 1)) != -1) {
            String extension = baseName.substring(dot + 1, end);
            boolean consumed = false;
            for (IExtensionDissolver ed : extensionDissolvers) {
                if (ed.desolveExtension(extension, model))
                    consumed = true;
            }
            if (!consumed)
                break;
            end = dot;
        }

        int stripped = baseName.length() - end;
        String dispatchPath = uri.substring(0, uri.length() - stripped);
        model.setDispatchPath(dispatchPath);

        if (preprocessors != null) {
            for (IRequestPreprocessor preprocessor : preprocessors)
                preprocessor.preprocess(model);
        }

        return model;
    }

}
