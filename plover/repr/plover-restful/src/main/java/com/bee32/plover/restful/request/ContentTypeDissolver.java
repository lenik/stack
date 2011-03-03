package com.bee32.plover.restful.request;

import com.bee32.plover.util.Mime;

public class ContentTypeDissolver
        implements ISuffixDissolver {

    @Override
    public boolean desolveSuffix(String name, ResourceRequest model) {
        if (name == null)
            throw new NullPointerException("extension");

        Mime contentType = Mime.getInstanceByExtension(name);
        if (contentType == null)
            return false;

        model.setContentType(contentType);
        return true;
    }

}
