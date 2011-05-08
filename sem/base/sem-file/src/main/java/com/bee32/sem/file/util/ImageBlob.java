package com.bee32.sem.file.util;

import java.awt.Image;
import java.awt.Toolkit;



public class ImageBlob
        extends MimeBlob {

    private static final long serialVersionUID = 1L;

    public ImageBlob(ImageType imageType, byte[] data) {
        super(imageType.getContentType(), data);
    }

    public Image toImage() {
        Image image = Toolkit.getDefaultToolkit().createImage(data);
        return image;
    }

}
