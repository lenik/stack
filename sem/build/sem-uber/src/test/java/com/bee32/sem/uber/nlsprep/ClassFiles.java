package com.bee32.sem.uber.nlsprep;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.free.FilePath;

import com.bee32.plover.arch.DefaultClassLoader;
import com.bee32.plover.xutil.m2.UCLDumper;

public class ClassFiles {

    public static List<String> findClasses(File start) {
        List<String> list = new ArrayList<String>();
        findClasses(start, "", list);
        return list;
    }

    static void findClasses(File start, String prefix, List<String> list) {
        for (String base : start.list()) {
            String ext = FilePath.getExtension(base);
            String stem = FilePath.stripExtension(base);

            if ("class".equals(ext)) {
                String fqcn = prefix + stem;
                list.add(fqcn);
                continue;
            }

            File file = new File(start, base);
            if (file.isDirectory()) {
                String subPrefix = prefix + stem + ".";
                findClasses(file, subPrefix, list);
            }
        }
    }

    public static List<Class<?>> forNames(List<String> fqcns, boolean skipLocals)
            throws ClassNotFoundException {
        return forNames(fqcns, skipLocals, DefaultClassLoader.getInstance());
    }

    public static List<Class<?>> forNames(List<String> fqcns, boolean skipLocals, ClassLoader loader)
            throws ClassNotFoundException {

        List<Class<?>> types = new ArrayList<Class<?>>(fqcns.size());

        for (String fqcn : fqcns) {
            if (skipLocals && fqcn.contains("$"))
                continue;

            Class<?> type = Class.forName(fqcn, false, loader);

            types.add(type);
        }
        return types;
    }

    public static void main(String[] args)
            throws ClassNotFoundException {

        for (File start : UCLDumper.getLocalClasspaths())
            if (start.isDirectory()) {
                System.out.println("Scan " + start);
                List<String> fqcns = findClasses(start);
                List<Class<?>> types = forNames(fqcns, true);
                for (Class<?> type : types)
                    System.out.println("    " + type);
            }
    }

}
