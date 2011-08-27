package com.bee32.plover.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.JavaioFile;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanServiceContext;
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
@ServiceTemplate
@Lazy
@Import(ScanServiceContext.class)
public abstract class ServiceCollector<T>
        extends WiredTestCase {

    protected final Class<T> prototype;

    public ServiceCollector() {
        prototype = ClassUtil.infer1(getClass(), ServiceCollector.class, 0);
    }

    protected void collect()
            throws IOException {
        ServiceCollector<T> wired = this;

        // Already wired, see WiredTestCase.

        // try {
        // wired = (ServiceCollector<T>) wire();
        // } catch (Exception e) {
        // throw new RuntimeException("Autowire failed", e);
        // }
        wired._collect();
    }

    private Map<File, StringBuilder> files;

    synchronized void _collect()
            throws IOException {
        files = new HashMap<File, StringBuilder>();
        scan();
        commit(files);
        files = null;
    }

    protected void scan() {
        for (Class<?> serviceType : getExtensions(prototype)) {
            publish(prototype, serviceType);
        }
    }

    protected void publish(Class<?> prototype, Class<?> serviceType) {
        System.out.println("    Service: " + serviceType);

        ServiceTemplate st = serviceType.getAnnotation(ServiceTemplate.class);
        assert st != null;
        boolean isPrototype = st.prototype();

        File resdir = MavenPath.getResourceDir(serviceType);
        File sfile;

        if (isPrototype)
            sfile = new File(resdir, "META-INF/prototypes/" + prototype.getName());
        else
            sfile = new File(resdir, "META-INF/services/" + prototype.getName());

        StringBuilder buf = files.get(sfile);
        if (buf == null) {
            buf = new StringBuilder();
            files.put(sfile, buf);
        }

        buf.append(serviceType.getName());
        buf.append("\n");
    }

    protected Collection<Class<?>> getExtensions(Class<?> base) {
        List<Class<?>> list = new ArrayList<Class<?>>();
        for (String beanName : application.getBeanNamesForType(base, true, false)) {
            Class<?> serviceType = application.getType(beanName);
            list.add(serviceType);
        }
        return list;
    }

    protected void commit(Map<File, StringBuilder> files)
            throws IOException {
        for (Entry<File, StringBuilder> entry : files.entrySet()) {
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
