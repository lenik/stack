package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeSet;

import javax.free.IllegalUsageException;
import javax.free.StringPart;
import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.model.profile.StandardProfiles;
import com.bee32.plover.util.Mime;

public class RestfulRequestBuilder {

    private static IMethodDissolver methodDissolver;
    private static Map<Character, ISuffixDissolver> suffixDissolvers;

    static {
        methodDissolver = new MethodDissolver();

        suffixDissolvers = new HashMap<Character, ISuffixDissolver>();
        suffixDissolvers.put('~', new ProfileDissolver());
        suffixDissolvers.put('*', new VerbDissolver());
        suffixDissolvers.put('.', new ContentTypeDissolver());
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

    public RestfulRequest build(HttpServletRequest request) {
        RestfulRequest model = new RestfulRequest(request);

        // Translate http method
        String httpMethod = request.getMethod();
        methodDissolver.desolveMethod(httpMethod, model);

        String acceptContentType = request.getHeader("Accept-Content-Type");
        if (acceptContentType != null) {
            Mime contentType = Mime.getInstance(acceptContentType);
            if (contentType != null)
                model.setTargetContentType(contentType);
        }

        // Prepare resource path
        // dir/base*~.suffix
        // dir/*~.suffix => base=""
        String rawPath = request.getPathInfo();
        String dirName = StringPart.beforeLast(rawPath, '/');
        String baseName = StringPart.afterLast(rawPath, '/');

        // Prepare to parse suffixes
        SuffixTokenizer tokens = new SuffixTokenizer(baseName, true);
        baseName = tokens.next();

        String path;
        if (baseName.isEmpty()) { // index?
            path = dirName;
            model.setProfile(StandardProfiles.INDEX);
        } else {
            path = dirName + '/' + baseName;
        }
        model.setPath(path);

        while (tokens.hasNext()) {
            String suffix = tokens.next();

            char delim = suffix.charAt(0);
            ISuffixDissolver dissolver = suffixDissolvers.get(delim);
            if (dissolver == null)
                throw new IllegalUsageException("Bad suffix: " + suffix);

            String suffixName = suffix.substring(1);

            dissolver.desolveSuffix(suffixName, model);
        }

        // other preprocessors.
        if (preprocessors != null) {
            for (IRequestPreprocessor preprocessor : preprocessors)
                preprocessor.preprocess(model);
        }

        return model;
    }

    private static final RestfulRequestBuilder instance = new RestfulRequestBuilder();

    public static RestfulRequestBuilder getInstance() {
        return instance;
    }

}
