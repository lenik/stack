package com.bee32.plover.arch.ui.annotated;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

public class ImageMapUtil {

    public static Image[] getImages(AnnotatedElement annotatedElement) {
        Images _images = annotatedElement.getAnnotation(Images.class);
        if (_images != null) {
            Image[] images = _images.value();
            return images;
        }

        Image _image = annotatedElement.getAnnotation(Image.class);
        if (_image != null)
            return new Image[] { _image };

        return null;
    }

    public static <AnnotatedMember extends AnnotatedElement & Member> //
    Image[] getMemberImages(AnnotatedMember member) {
        Image[] images = getImages(member);
        return images;
    }

}
