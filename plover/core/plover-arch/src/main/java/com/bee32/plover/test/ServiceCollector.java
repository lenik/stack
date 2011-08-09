package com.bee32.plover.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.JavaioFile;

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

    protected final void _collect()
            throws IOException {

        Map<File, StringBuilder> commit = new HashMap<File, StringBuilder>();

        for (T service : application.getBeansOfType(prototype).values()) {

            Class<? extends Object> serviceType = service.getClass();
            System.out.println("Service: " + service);

            File resdir = MavenPath.getResourceDir(serviceType);
            File sfile = new File(resdir, "META-INF/services/" + prototype.getName());

            StringBuilder buf = commit.get(sfile);
            if (buf == null) {
                buf = new StringBuilder();
                commit.put(sfile, buf);
            }

            buf.append(serviceType.getName());
            buf.append("\n");
        }

        for (Entry<File, StringBuilder> entry : commit.entrySet()) {
            File _file = entry.getKey();
            String content = entry.getValue().toString();

            JavaioFile file = new JavaioFile(_file);

            // TODO Not work.
            // file.setCreateParentsMode(true);
            _file.getParentFile().mkdirs();

            // The same?
            if (file.exists() == Boolean.TRUE) {
                String old = file.forRead().readTextContents();
                if (content.equals(old))
                    continue;
            }

            System.out.println("Update " + _file);
            file.forWrite().write(content);
        }
    }

}
