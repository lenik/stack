package com.bee32.plover.velocity;

import java.io.StringWriter;
import java.io.Writer;

import javax.free.Strings;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class Velocity {

    static VelocityEngine velocityEngine;

    static {
        velocityEngine = new VelocityEngine();

        velocityEngine.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.setProperty("runtime.references.strict", "true");

        velocityEngine.init();
    }

    public static Template getTemplate(Class<?> clazz, String extension)
            throws VelocityException {
        String classPath = clazz.getName().replace('.', '/');

        String templateName;
        if (extension == null)
            templateName = classPath;
        else {
            String Extension = Strings.ucfirst(extension);
            Extension += ".v";
            templateName = classPath + Extension;
        }

        try {
            return velocityEngine.getTemplate(templateName);
        } catch (ResourceNotFoundException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass == null)
                return null;
            return getTemplate(superclass, extension);
        }
    }

    public static boolean merge(Class<?> clazz, String extension, VelocityContext context, Writer writer)
            throws VelocityException {
        Template template = getTemplate(clazz, extension);
        if (template == null)
            return false;

        template.merge(context, writer);
        return true;
    }

    public static boolean merge(Class<?> clazz, String extension, Object it, Writer writer)
            throws VelocityException {
        Template template = getTemplate(clazz, extension);
        if (template == null)
            return false;

        VelocityContext context = new VelocityContext();
        context.put("it", it);

        template.merge(context, writer);
        return true;
    }

    public static String merge(Class<?> clazz, String extension, VelocityContext it)
            throws VelocityException {
        StringWriter buf = new StringWriter();
        merge(clazz, extension, it, buf);
        return buf.toString();
    }

    public static String merge(Class<?> clazz, String extension, Object it)
            throws VelocityException {
        StringWriter buf = new StringWriter();
        merge(clazz, extension, it, buf);
        return buf.toString();
    }

    // Auto typing..

    public static boolean merge(String extension, Object it, Writer writer)
            throws VelocityException {
        return merge(it.getClass(), extension, it, writer);
    }

    public static String merge(String extension, Object it) {
        return merge(it.getClass(), extension, it);
    }

}
