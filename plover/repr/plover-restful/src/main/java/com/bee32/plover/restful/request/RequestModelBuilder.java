package com.bee32.plover.restful.request;

import java.util.ServiceLoader;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

public class RequestModelBuilder {

    static TreeSet<IRequestPreprocessor> preprocessors;

    static void reloadServices() {
        ServiceLoader<IRequestPreprocessor> preprocessorServices = ServiceLoader.load(IRequestPreprocessor.class);
        for (IRequestPreprocessor preprocessor : preprocessorServices) {
            preprocessors.add(preprocessor);
        }
    }

    static {
        reloadServices();
    }

    public RequestModel preprocess(HttpServletRequest request) {
        RequestModel model = new RequestModel(request);

    }

}
