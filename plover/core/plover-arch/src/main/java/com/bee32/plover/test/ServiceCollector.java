package com.bee32.plover.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.JavaioFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;

import com.bee32.plover.arch.DefaultClassLoader;
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

    static Logger logger = LoggerFactory.getLogger(ServiceCollector.class);

    protected final Class<T> serviceClass;
    protected ClassScanner scanner = new ClassScanner();

    boolean showPaths;
    List<Class<?>> serviceImpls;
    Map<File, List<String>> commitMap;

    public ServiceCollector() {
        URLClassLoader scl = (URLClassLoader) DefaultClassLoader.getInstance();
        URLClassLoader tcl = TestClassLoader.createMavenTestClassLoader(scl);
        scanner.setClassLoader(tcl);

        serviceClass = ClassUtil.infer1(getClass(), ServiceCollector.class, 0);
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

    synchronized void _collect()
            throws IOException {
        commitMap = new HashMap<File, List<String>>();
        serviceImpls = new ArrayList<>();
        scan();
        commit(commitMap);
        if (showPaths)
            for (Class<?> serviceImpl : serviceImpls) {
                File sourceFile = MavenPath.getSourceFile(serviceImpl);
                System.out.println(sourceFile);
            }
        commitMap = null;
        serviceImpls = null;
    }

    protected void scan() {
        List<Class<?>> serviceList = new ArrayList<Class<?>>(getExtensions(serviceClass, true));
        // Collections.sort(serviceList, ClassNameComparator.getInstance());
        for (Class<?> serviceImpl : serviceList) {
            publish(serviceClass, serviceImpl);
        }
    }

    protected void publish(Class<?> serviceClass, Class<?> serviceImpl) {
        logger.info("    Service: " + serviceImpl);

        ServiceTemplate _serviceTemplate = serviceImpl.getAnnotation(ServiceTemplate.class);
        if (_serviceTemplate == null) {
            logger.debug("        (No service-template annotation, skipped)");
            return;
        }

        serviceImpls.add(serviceImpl);

        int mod = serviceImpl.getModifiers();
        if (Modifier.isAbstract(mod)) {
            if (!_serviceTemplate.prototype())
                return;
            logger.debug("    (Abstract class included for prototype)");
        }

        File resdir = MavenPath.getResourceDir(serviceImpl);
        File sfile;

        if (_serviceTemplate.prototype())
            sfile = new File(resdir, "META-INF/prototypes/" + serviceClass.getName());
        else
            sfile = new File(resdir, "META-INF/services/" + serviceClass.getName());

        List<String> lines = commitMap.get(sfile);
        if (lines == null) {
            lines = new ArrayList<String>();
            commitMap.put(sfile, lines);
        }

        lines.add(serviceImpl.getName());
    }

    protected Collection<Class<?>> getExtensions(Class<?> base, boolean includeAbstract) {
        List<Class<?>> list = new ArrayList<Class<?>>();
        // ApplicationContext appctx;
        // for (String beanName : appctx.getBeanNamesForType(base, true, false)) {
        // Class<?> serviceType = appctx.getType(beanName);
        // list.add(serviceType);
        // }
        for (Class<?> clazz : scanner.getClosure(base)) {
            int mod = clazz.getModifiers();
            if (Modifier.isAbstract(mod))
                if (!includeAbstract)
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

    public boolean isShowPaths() {
        return showPaths;
    }

    public void setShowPaths(boolean showPaths) {
        this.showPaths = showPaths;
    }

    protected static void commit(Map<File, List<String>> files)
            throws IOException {
        for (Entry<File, List<String>> entry : files.entrySet()) {
            File _file = entry.getKey();

            List<String> lines = entry.getValue();
            Collections.sort(lines);

            StringBuilder buf = new StringBuilder(lines.size() * 100);
            for (String line : new LinkedHashSet<String>(lines)) {
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

            logger.info("Update " + _file);
            file.forWrite().write(content.toString());
        }
    }

}
