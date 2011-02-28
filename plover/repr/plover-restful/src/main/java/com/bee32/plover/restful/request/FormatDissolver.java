package com.bee32.plover.restful.request;

import java.util.HashMap;
import java.util.Map;

public class FormatDissolver
        implements IExtensionDissolver {

    private static final Map<String, String> canonicalFormatNames;

    static {
        canonicalFormatNames = new HashMap<String, String>();
        canonicalFormatNames.put("txt", "text");
        canonicalFormatNames.put("text", "text");
        canonicalFormatNames.put("htm", "html");
        canonicalFormatNames.put("html", "html");
        canonicalFormatNames.put("wml", "wml");
        canonicalFormatNames.put("pdf", "pdf");
        canonicalFormatNames.put("ps", "postscript");
        canonicalFormatNames.put("png", "image");
        canonicalFormatNames.put("jpg", "image");
    }

    @Override
    public boolean desolveExtension(String extension, ResourceRequest model) {
        if (extension == null)
            throw new NullPointerException("extension");

        String format = canonicalFormatNames.get(extension);
        if (format == null)
            return false;

        model.setFormat(format);
        return true;
    }

}
