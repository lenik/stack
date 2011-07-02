package com.bee32.sem.store.nlsprep;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

    static Set<String> skippedProps = new HashSet<String>();
    static {
        skippedProps.add("class");
        skippedProps.add("appearance");
        skippedProps.add("properties");
        skippedProps.add("exceptionSupport");

        // These should go with general english.
        skippedProps.add("id");
        skippedProps.add("name");
        skippedProps.add("label");
        skippedProps.add("description");
    }

    static boolean beanProperties = false;

    public static Map<String, String> getNLSMap(Class<?> type) {
        Set<String> keys = new HashSet<String>();
        keys.add("");

        if (beanProperties)
            try {
                for (PropertyDescriptor property : Introspector.getBeanInfo(type).getPropertyDescriptors()) {

                    String name = property.getName();
                    if (name.startsWith("_"))
                        continue;
                    if (skippedProps.contains(name))
                        continue;

                    keys.add(name);
                }
            } catch (IntrospectionException e) {
                throw new RuntimeException(e.getMessage(), e);
            }

        Set<Class<?>> stopClasses = EntityFormatter.getStopClasses();

        while (type != null && !stopClasses.contains(type)) {
            for (Method method : type.getDeclaredMethods()) {

                int modifiers = method.getModifiers();
                if (!Modifier.isPublic(modifiers))
                    continue;

                String name = method.getName();
                if (name.startsWith("_"))
                    continue;

                if (name.startsWith("set"))
                    continue;

                if (name.startsWith("get"))
                    name = Strings.lcfirst(name.substring(3));
                else if (name.startsWith("is"))
                    name = Strings.lcfirst(name.substring(2));

                keys.add(name);
            }

            for (Field field : type.getDeclaredFields()) {

                int modifiers = field.getModifiers();
                if (!Modifier.isPublic(modifiers))
                    continue;

                String name = field.getName();

                if (name.startsWith("_"))
                    continue;

                keys.add(name);
            }

            // Only local declarations.
            // type = type.getSuperclass();
            break;
        }

        Map<String, String> map = new HashMap<String, String>();
        for (String key : keys) {
            if (!key.isEmpty())
                key += ".";
            map.put(key + "label", "");
            map.put(key + "description", "");
        }
        return map;
    }

    public static void dumpNLS(Map<String, String> map, File file)
            throws IOException {

        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            Reader reader = new InputStreamReader(in, "utf-8");
            Properties existing = new Properties();
            existing.load(reader);
            for (Object _key : existing.keySet()) {
                String key = (String) _key;
                String val = existing.getProperty(key);
                // overwrite using existings.
                map.put(key, val);
            }
        }

        System.out.println("Create " + file);
        file.getParentFile().mkdirs();

        OutputStream _out = new FileOutputStream(file, true);
        PrintStream out = new PrintStream(_out, true, "utf-8");
        dumpNLS(map, out);
        out.close();
    }

    public static void dumpNLS(Map<String, ?> map, PrintStream out) {
        ArrayList<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys, PropertyKeyComparator.INSTANCE);

        int maxlen = 40; // Default width.
        for (String key : keys)
            if (key.length() > maxlen)
                maxlen = key.length();

        for (String key : keys) {
            String PADDING = Strings.repeat(maxlen - key.length(), ' ');
            out.println(key + PADDING + " = ");
        }
    }

    public static void main(String[] args)
            throws Exception {
        for (File classdir : UCLDumper.getLocalClasspaths())
            if (classdir.isDirectory()) {
                File resdir = getSiblingResource(classdir);

                System.out.println("Scan " + classdir);

                List<String> fqcns = ClassFiles.findClasses(classdir);
                List<Class<?>> types = ClassFiles.forNames(fqcns, true);

                for (Class<?> type : types) {
                    if (type.isInterface())
                        continue;

                    int modifiers = type.getModifiers();
                    if (Modifier.isAbstract(modifiers))
                        continue;

                    Map<String, String> map = getNLSMap(type);

                    String fileName = type.getName().replace(".", SystemProperties.getFileSeparator());

                    File file = new File(resdir, fileName + ".properties");
                    dumpNLS(map, file);

                    file = new File(resdir, fileName + "_zh_CN.properties");
                    dumpNLS(map, file);
                }
            }
    }

}
