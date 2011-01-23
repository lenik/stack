package com.bee32.plover.arch.typeextension;

import java.lang.annotation.Annotation;

import javax.free.Traits;

/**
 * Plover 类型扩展
 * <p>
 * 提供类型扩展的辅助方法。
 * <p>
 * 在 Plover 体系中，一个类可以通过下面的方法来增加额外的信息：
 * <ul>
 * <li>标注：标注的扩展信息。
 * <li>Traits 类：通过静态方法 query() 返回的扩展信息。
 * </ul>
 */
public class PloverExtension {

    public static <T> T getTypeExtension(Class<?> clazz, Class<T> extension) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (extension == null)
            throw new NullPointerException("extension");

        if (extension.isAnnotation()) {
            @SuppressWarnings("unchecked")
            Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) extension;

            @SuppressWarnings("unchecked")
            T annotation = (T) clazz.getAnnotation(annotationClass);

            return annotation;
        }

        return Traits.getTraits(clazz, extension);
    }

}
