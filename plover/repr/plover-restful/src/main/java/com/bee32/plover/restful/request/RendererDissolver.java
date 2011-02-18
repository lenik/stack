package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;

public class RendererDissolver
        implements IExtensionDissolver {

    private static final Map<String, String> rendererByKeyword;

    static {
        rendererByKeyword = new HashMap<String, String>();
        rendererByKeyword.put("txt", "text");
        rendererByKeyword.put("text", "text");
        rendererByKeyword.put("htm", "html");
        rendererByKeyword.put("html", "html");
        rendererByKeyword.put("pdf", "pdf");
        rendererByKeyword.put("ps", "postscript");
        rendererByKeyword.put("png", "image");
        rendererByKeyword.put("jpg", "image");
    }

    @Override
    public boolean desolveExtension(String extension, RequestModel model) {
        if (extension == null)
            throw new NullPointerException("extension");

        String renderer = rendererByKeyword.get(extension);
        if (renderer == null)
            return false;

        return true;
    }

}
