package com.bee32.sem.store.nlsprep;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.free.Strings;
import javax.free.SystemProperties;

import com.bee32.plover.orm.util.EntityFormatter;

public class NLSInitiator {

    public static File getSiblingResource(File dir) {
        String resdir = dir.getPath();

        // src/main/java => src/main/resources
        // target/classes => src/main/resources
        // target/test-classes => src/test/resources
        resdir = resdir.replaceFirst("/src/main/java", "/src/main/resources");
        resdir = resdir.replaceFirst("/target/classes", "/src/main/resources");
        resdir = resdir.replaceFirst("/target/test-classes", "/src/test/resources");
        return new File(resdir);
    }

    public static Map<String, ?> getNLSMap(Class<?> type) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("", "");

        Set<Class<?>> stopClasses = EntityFormatter.getStopClasses();

        try {
            for (PropertyDescriptor property : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
                map.put(property.getName(), "");
            }
        } catch (IntrospectionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        while (type != null && !stopClasses.contains(type)) {
            for (Method method : type.getDeclaredMethods()) {

                String name = method.getName();
                if (name.startsWith("_"))
                    continue;

                if (name.startsWith("get"))
                    continue;
                if (name.startsWith("set"))
                    continue;
                if (name.startsWith("is"))
                    continue;

                int modifiers = method.getModifiers();
                if (!Modifier.isPublic(modifiers))
                    continue;

                map.put(name, "");
            }

            for (Field field : type.getDeclaredFields()) {
                String name = field.getName();

                if (name.startsWith("_"))
                    continue;

                int modifiers = field.getModifiers();
                if (!Modifier.isPublic(modifiers))
                    continue;

                map.put(name, "");
            }

            type = type.getSuperclass();
        }

        return map;
    }

    public static void dumpNLS(Map<String, ?> map, File file)
            throws IOException {
        System.out.println("Create " + file);
        file.getParentFile().mkdirs();
        PrintStream out = new PrintStream(file, "utf-8");
        dumpNLS(map, out);
        out.close();
    }

    public static void dumpNLS(Map<String, ?> map, PrintStream out) {
        ArrayList<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        int maxlen = 0;
        for (String key : keys)
            if (key.length() > maxlen)
                maxlen = key.length();

        for (String key : keys) {
            String prefix = key;
            if (!prefix.isEmpty())
                prefix += ".";

            String PADDING = Strings.repeat(maxlen - (prefix.length() - 1), ' ');

            out.println(prefix + "label      " + PADDING + " = ");
            out.println(prefix + "description" + PADDING + " = ");
            out.println();
        }
    }

    public static void main(String[] args)
            throws Exception {
        for (File classdir : UCLDumper.getLocalClasspaths())
            if (classdir.isDirectory()) {
                File resdir = getSiblingResource(classdir);

                System.out.println("Scan " + classdir);

                List<String> fqcns = ClassFiles.findClasses(classdir);
                List<Class<?>> types = ClassFiles.forNames(fqcns);

                for (Class<?> type : types) {
                    Map<String, ?> map = getNLSMap(type);

                    String fileName = type.getName().replace(".", SystemProperties.getFileSeparator());

                    File file = new File(resdir, fileName + ".properties");
                    dumpNLS(map, file);

                    file = new File(resdir, fileName + "_zh_CN.properties");
                    dumpNLS(map, file);
                }
            }
    }

}
