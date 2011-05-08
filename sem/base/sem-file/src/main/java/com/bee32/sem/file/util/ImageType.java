package com.bee32.sem.file.util;

public enum ImageType {

    JPEG("image/jpeg"),

    PNG("image/png"),

    GIF("image/gif"),

    ;

    final String contentType;

    ImageType(String contentType) {
        if (contentType == null)
            throw new NullPointerException("contentType");
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

}
