package com.bee32.plover.xutil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.free.Pred1;

public class ResourceScanner {

    private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private IFileOrEntryFilter filter;

    public ResourceScanner(IFileOrEntryFilter filter) {
        setFilter(filter);
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        if (classLoader == null)
            throw new NullPointerException("classLoader");
        this.classLoader = classLoader;
    }

    public IFileOrEntryFilter getFilter() {
        return filter;
    }

    public void setFilter(IFileOrEntryFilter filter) {
        if (filter == null)
            filter = IFileOrEntryFilter.TRUE;
        this.filter = filter;
    }

    public Map<String, List<URL>> scanResources(String rootResourceName)
            throws IOException {
        BufferedFileOrEntryProcessor buffer = new BufferedFileOrEntryProcessor();
        scanResources(rootResourceName, buffer);
        return buffer.getResourceMap();
    }

    public void scanResources(String rootResourceName, IFileOrEntryProcessor processor)
            throws IOException {
        Enumeration<URL> urls = classLoader.getResources(rootResourceName);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            // System.out.println(url);
            if ("jar".equals(url.getProtocol())) {
                String path = url.getPath(); // "file:....!/..."
                int exclm = path.lastIndexOf('!');
                String jarPath = path.substring(5, exclm); // remove "file:"
                JarFile jarFile = new JarFile(jarPath);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().startsWith(rootResourceName))
                        continue;
                    if (filter.accept(entry)) {
                        processor.process(jarFile, entry);
                    }
                }
            } else if ("file".equals(url.getProtocol())) {
                String path = url.getPath();
                File file = new File(path);
                scanFileResources(file, rootResourceName, processor);
            }
        }
    }

    void scanFileResources(File file, String resourceName, IFileOrEntryProcessor processor) {
        if (!resourceName.isEmpty() && !resourceName.endsWith("/"))
            resourceName += "/";
        for (File childFile : file.listFiles(filter)) {
            String childResourceName = resourceName + childFile.getName();
            if (childFile.isDirectory()) {
                scanFileResources(childFile, childResourceName, processor);
            } else {
                processor.process(childFile, childResourceName);
            }
        }
    }

    public static void scanTypenames(String packageName, final Pred1<String> typeNameCallback)
            throws IOException {
        ResourceScanner scanner = new ResourceScanner(//
                ClassOrDirFileFilter.INSTANCE);
        String packageDir = packageName.replace('.', '/');
        for (String resourceName : scanner.scanResources(packageDir).keySet()) {
            assert resourceName.endsWith(".class");
            String rawName = resourceName.substring(0, resourceName.length() - 6);
            String fqcn = rawName.replace('/', '.');
            // fqcn = fqcn.replace('$', '.');
            typeNameCallback.eval(fqcn);
        }
    }

    static class TypeResolver
            extends Pred1<String> {

        final Pred1<Class<?>> target;

        public TypeResolver(Pred1<Class<?>> target) {
            if (target == null)
                throw new NullPointerException("target");
            this.target = target;
        }

        @Override
        public boolean test(String fqcn) {
            Class<?> clazz;
            try {
                clazz = Class.forName(fqcn);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
            return target.test(clazz);
        }

    }

    public static void scanTypes(String packageName, final Pred1<Class<?>> typeCallback)
            throws IOException {
        TypeResolver typeResolver = new TypeResolver(typeCallback);
        scanTypenames(packageName, typeResolver);
    }

}
