package com.bee32.plover.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.xutil.m2.MavenPath;

/**
 * Usage:
 * <ol>
 * <li>Put {@link ComponentTemplate} annotation to something like AbstractFooService.
 * <li>Create FooCollector > ServiceCollector
 * <li>Run FooCollector test case.
 * <li>Remove {@link ComponentTemplate}.
 * </ol>
 */
public class ServiceCollector<T>
        extends WiredTestCase {

    final Class<T> prototype;

    public ServiceCollector() {
        prototype = ClassUtil.infer1(getClass(), ServiceCollector.class, 0);
    }

    protected void collect()
            throws IOException {
        ServiceCollector<T> wired;
        try {
            wired = (ServiceCollector<T>) wire();
        } catch (Exception e) {
            throw new RuntimeException("Autowire failed", e);
        }
        wired._collect();
    }

    Set<File> created = new HashSet<File>();

    protected final void _collect()
            throws IOException {
        for (T service : application.getBeansOfType(prototype).values()) {

            Class<? extends Object> serviceType = service.getClass();
            System.out.println("Service: " + service);

            File resdir = MavenPath.getResourceDir(serviceType);
            File sfile = new File(resdir, "META-INF/services/" + prototype.getName());

            boolean newFile = created.add(sfile);
            FileWriter writer = new FileWriter(sfile, !newFile);
            PrintWriter out = new PrintWriter(writer);

            out.println(serviceType.getName());

            out.close();
        }
    }

}
