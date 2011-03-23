package com.bee32.plover.arch;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

class CompositeElementUtil {

    public static boolean isCompositeElement(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        CompositeElement compositeElement = annotationType.getAnnotation(CompositeElement.class);
        return compositeElement != null;
    }

    public static boolean isCompositeElement(AnnotatedElement element) {
        for (Annotation annotation : element.getAnnotations())
            if (isCompositeElement(annotation))
                return true;
        return false;
    }

}
