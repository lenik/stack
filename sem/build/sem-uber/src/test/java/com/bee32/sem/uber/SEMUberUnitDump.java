package com.bee32.sem.uber;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.free.AbstractNonNullComparator;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.xutil.m2.MavenPath;

public class SEMUberUnitDump
        implements ITypeAbbrAware {

    public static void main(String[] args) {
        SEMUberUnit unit = new SEMUberUnit();

        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        Set<? extends ClassCatalog> deps = unit.getAllDependencies();
        for (ClassCatalog dep : deps)
            System.out.println(dep.getName() + ": " + dep.getAppearance().getDisplayName());

        List<Class<?>> classes = new ArrayList<Class<?>>(unit.getClasses());

        Collections.sort(classes, SimpleNameComparator.INSTANCE);

        for (Class<?> clazz : classes) {
            String abbr = ABBR.register(clazz);
            String simpleName = clazz.getSimpleName();
            if (!simpleName.equals(abbr))
                System.out.println("      Entity " + simpleName + " -> " + abbr);
        }

        System.out.println("\nJava Sources:");
        for (Class<?> clazz : classes) {
            File javaFile = MavenPath.getSourceFile(clazz);
            System.out.println(javaFile);
        }

        System.out.println("\nNLS Files:");
        for (Class<?> clazz : classes) {
            File javaFile = MavenPath.getSourceFile(clazz);
            File nlsFile = MavenPath.getSiblingResource(javaFile);
            String nlsPath = nlsFile.getPath().replace(".java", ".properties");
            System.out.println(nlsPath);
        }
    }

}

class SimpleNameComparator
        extends AbstractNonNullComparator<Class<?>> {

    @Override
    public int compareNonNull(Class<?> o1, Class<?> o2) {
        String n1 = o1.getSimpleName();
        String n2 = o2.getSimpleName();
        int cmp = n1.compareTo(n2);
        if (cmp != 0)
            return cmp;

        n1 = o1.getName();
        n2 = o2.getName();
        return n1.compareTo(n2);
    }

    public static final SimpleNameComparator INSTANCE = new SimpleNameComparator();

}