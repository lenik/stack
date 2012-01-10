package com.bee32.plover.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.JavaioFile;

import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanServiceContext;
import com.bee32.plover.xutil.ClassScanner;
import com.bee32.plover.xutil.m2.MavenPath;
import com.bee32.plover.xutil.m2.TestClassLoader;

/**
 * Usage:
 * <ol>
 * <li>Put {@link ServiceTemplate} annotation to something like AbstractFooService.
 * <li>Create FooCollector > ServiceCollector
 * <li>Run FooCollector test case.
 * <li>Remove {@link ServiceTemplate}.
 * </ol>
 */
@ServiceTemplate
@Lazy
@Import(value = ScanServiceContext.class, inherits = false)
public abstract class ServiceCollector<T> {

    protected final Class<T> prototype;
    protected ClassScanner scanner = new ClassScanner();

    public ServiceCollector() {
        URLClassLoader scl = (URLClassLoader) ClassLoader.getSystemClassLoader();
        scanner.setClassLoader(TestClassLoader.createMavenTestClassLoader(scl));

        prototype = ClassUtil.infer1(getClass(), ServiceCollector.class, 0);
        try {
            scanner.scan("com.bee32");
            scanner.scan("user");
        } catch (IOException e) {
            throw new Error(e.getMessage(), e);
        }
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

    private Map<File, List<String>> files;

    synchronized void _collect()
            throws IOException {
        files = new HashMap<File, List<String>>();
        scan();
        commit(files);
        files = null;
    }

    protected void scan() {
        List<Class<?>> serviceList = new ArrayList<Class<?>>(getExtensions(prototype));
        // Collections.sort(serviceList, ClassNameComparator.getInstance());
        for (Class<?> serviceType : serviceList) {
            publish(prototype, serviceType);
        }
    }

    protected void publish(Class<?> prototype, Class<?> serviceType) {
        System.out.println("    Service: " + serviceType);

        ServiceTemplate st = serviceType.getAnnotation(ServiceTemplate.class);
        if (st == null) {
            System.out.println("        (No service-template annotation, skipped)");
            return;
        }

        boolean isPrototype = st.prototype();

        File resdir = MavenPath.getResourceDir(serviceType);
        File sfile;

        if (isPrototype)
            sfile = new File(resdir, "META-INF/prototypes/" + prototype.getName());
        else
            sfile = new File(resdir, "META-INF/services/" + prototype.getName());

        List<String> lines = files.get(sfile);
        if (lines == null) {
            lines = new ArrayList<String>();
            files.put(sfile, lines);
        }

        lines.add(serviceType.getName());
    }

    protected Collection<Class<?>> getExtensions(Class<?> base) {
        List<Class<?>> list = new ArrayList<Class<?>>();
        // ApplicationContext appctx;
        // for (String beanName : appctx.getBeanNamesForType(base, true, false)) {
        // Class<?> serviceType = appctx.getType(beanName);
        // list.add(serviceType);
        // }
        for (Class<?> clazz : scanner.getClosure(base)) {
            int mod = clazz.getModifiers();
            if (Modifier.isAbstract(mod))
                continue;
            if (!Modifier.isPublic(mod))
                continue;
            // defined in code-block, or inner class with-in enclosing instance.
            if (clazz.isAnonymousClass() || clazz.isLocalClass() || clazz.isMemberClass())
                continue;
            NotAComponent _nac = clazz.getAnnotation(NotAComponent.class);
            if (_nac != null)
                continue;
            list.add(clazz);
        }
        return list;
    }

    protected void commit(Map<File, List<String>> files)
            throws IOException {
        for (Entry<File, List<String>> entry : files.entrySet()) {
            File _file = entry.getKey();

            List<String> lines = entry.getValue();
            Collections.sort(lines);
            StringBuilder buf = new StringBuilder(lines.size() * 100);
            for (String line : lines) {
                buf.append(line);
                buf.append('\n');
            }
            String content = buf.toString();

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
            file.forWrite().write(content.toString());
        }
    }

}
