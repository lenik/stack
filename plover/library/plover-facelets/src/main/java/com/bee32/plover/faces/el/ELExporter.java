package com.bee32.plover.faces.el;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ELExporter {

    static Logger logger = LoggerFactory.getLogger(ELExporter.class);

    PrintStream out = System.out;

    public void export(Class<?> clazz) {
        out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        out.println("<!DOCTYPE facelet-taglib PUBLIC");
        out.println("    \"-//Sun Microsystems, Inc.//DTD Facelet Taglib 1.0//EN\"");
        out.println("    \"http://java.sun.com/dtd/facelet-taglib_1_0.dtd\">");
        out.println();
        out.println("<facelet-taglib>");
        out.println();

        String fqcn = clazz.getCanonicalName();
        int tld = fqcn.indexOf('.');
        int dot = fqcn.indexOf('.', tld + 1);
        String domain = fqcn.substring(tld + 1, dot) + "." + fqcn.substring(0, tld);

        String path = fqcn.substring(dot + 1).replace('.', '/');
        out.println("    <namespace>http://" + domain + "/" + path + "</namespace>");
        out.println();

        Set<Method> methods = new LinkedHashSet<Method>();
        Set<String> methodNames = new HashSet<String>();
        Set<String> overloaded = new HashSet<String>();
        List<String> skippedOverloads = new ArrayList<String>();

        for (Method method : clazz.getMethods()) {
            int modifiers = method.getModifiers();
            if (!Modifier.isStatic(modifiers))
                continue;
            if (method.getReturnType() == void.class)
                continue;
            if (method.getReturnType().isArray())
                continue;
            methods.add(method);
            if (!methodNames.add(method.getName()))
                overloaded.add(method.getName());
        }

        M: for (Method method : methods) {
            StringBuilder signature = new StringBuilder();
            signature.append(method.getReturnType().getCanonicalName());
            signature.append(" ");
            signature.append(method.getName());
            signature.append("(");
            Class<?>[] tv = method.getParameterTypes();
            for (int i = 0; i < tv.length; i++) {
                Class<?> t = tv[i];
                if (t.isArray())
                    continue M;
                if (i != 0)
                    signature.append(", ");
                signature.append(t.getCanonicalName());
            }
            signature.append(")");

            if (overloaded.contains(method.getName())) {
                skippedOverloads.add(signature.toString());
                continue;
            }

            out.println("    <function>");
            out.println("        <function-name>" + method.getName() + "</function-name>");
            out.println("        <function-class>" + clazz.getCanonicalName() + "</function-class>");
            out.println("        <function-signature>" + signature + "</function-signature>");
            out.println("    </function>");
            out.println();
        }

        out.println("</facelet-taglib>");

        for (String skip : skippedOverloads)
            logger.warn("Skipped overloaded method: " + skip);
    }

    public static void main(String[] args) {
        new ELExporter().export(StringUtils.class);
    }

}
