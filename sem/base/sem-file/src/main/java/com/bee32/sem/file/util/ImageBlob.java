package com.bee32.sem.file.util;

import java.awt.Image;

public class ImageBlob {

    private static final long serialVersionUID = 1L;

    final String format;
    final byte[] data;

    public ImageBlob(String format, byte[] data) {
        if (format == null)
            throw new NullPointerException("format");
        if (data == null)
            throw new NullPointerException("data");
        this.format = format;
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public byte[] getData() {
        return data;
    }

    public Image toImage() {
        // java.awt.image.BufferedImage
    }

}
