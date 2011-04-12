package com.bee32.plover.restful.request;

import com.bee32.plover.restful.RESTfulRequest;
import com.bee32.plover.util.Mime;

public class ContentTypeDissolver
        implements ISuffixDissolver {

    @Override
    public boolean dissolveSuffix(String extension, RESTfulRequest model) {
        if (extension == null)
            throw new NullPointerException("extension");

        Mime contentType = Mime.getInstanceByExtension(extension);
        if (contentType == null)
            return false;

        model.setTargetContentType(contentType);
        return true;
    }

    public static final ContentTypeDissolver INSTANCE = new ContentTypeDissolver();

}
