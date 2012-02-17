package com.bee32.plover.orm.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.free.URLResource;

import com.bee32.plover.arch.DefaultClassLoader;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.xutil.ResourceScanner;
import com.bee32.plover.xutil.m2.MavenPath;

public class EntityUsageCollector {

    Map<String, EntityUsage> usageMap;
    List<IEntityUsageProcessor> processors;

    public EntityUsageCollector() {
        usageMap = new TreeMap<String, EntityUsage>();
        processors = new ArrayList<IEntityUsageProcessor>();
    }

    public void addProcessor(IEntityUsageProcessor processor) {
        processors.add(processor);
    }

    public void removeProcessor(IEntityUsageProcessor processor) {
        processors.remove(processor);
    }

    public Set<String> getUsageIds() {
        return usageMap.keySet();
    }

    public EntityUsage getUsage(String usageId) {
        EntityUsage usage = usageMap.get(usageId);
        if (usage == null) {
            usage = new EntityUsage(usageId);
            usageMap.put(usageId, usage);
        }
        return usage;
    }

    public void collect(PersistenceUnit unit) {
        for (Class<?> clazz : unit.getClasses()) {
            for (IEntityUsageProcessor processor : processors) {
                try {
                    processor.process(clazz, this);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void loadFromResources()
            throws ClassNotFoundException, IOException {
        ClassLoader loader = DefaultClassLoader.getInstance();
        loadFromResources(loader);
    }

    static final String entityUsageDir = "META-INF/entity-usage/";

    public void loadFromResources(ClassLoader loader)
            throws ClassNotFoundException, IOException {

        ResourceScanner scanner = new ResourceScanner(null);
        scanner.setClassLoader(loader);
        for (Entry<String, List<URL>> entry : scanner.scanResources(entityUsageDir).entrySet()) {
            String resourceName = entry.getKey();
            List<URL> resources = entry.getValue();
            if (resourceName.endsWith("/"))
                continue;

            String usageId = resourceName.substring(entityUsageDir.length());
            EntityUsage usage = getUsage(usageId);

            for (URL resource : resources)
                for (String line : new URLResource(resource).forRead().listLines()) {
                    int colon = line.indexOf(':');
                    String entityTypeName = line.substring(0, colon).trim();
                    Class<?> entityType = Class.forName(entityTypeName, false, loader);
                    String description = line.substring(colon + 1).trim();
                    usage.add(entityType, description);
                }
        }
    }

    /**
     * <ul>
     * <li>Module: The corresponding module for the specific entity class.
     * <li>Dir: module/src/resources/
     * <li>File: META-INF/entity-usage/usage-id
     * <li>Content: entity-type: description
     * </ul>
     */
    public void writeToMavenResources()
            throws IOException {
        Map<File, StringBuilder> fileContentMap = new HashMap<File, StringBuilder>();

        for (String usageId : getUsageIds()) {
            EntityUsage usage = getUsage(usageId);
            for (Entry<Class<?>, String> entry : usage.getEntityMap().entrySet()) {
                Class<?> entityType = entry.getKey();
                String description = entry.getValue();

                File resourceDir = MavenPath.getResourceDir(entityType);
                File file = new File(resourceDir, entityUsageDir + usageId);
                StringBuilder content = fileContentMap.get(file);
                if (content == null) {
                    content = new StringBuilder();
                    fileContentMap.put(file, content);
                }

                content.append(entityType.getCanonicalName());
                content.append(": ");
                content.append(description);
                content.append("\n");
            }
        }

        for (Entry<File, StringBuilder> entry : fileContentMap.entrySet()) {
            File file = entry.getKey();
            file.getParentFile().mkdirs();
            String content = entry.getValue().toString();
            System.out.println(file + ": " + content.length() + " chars");
            try (PrintStream out = new PrintStream(file, "utf-8")) {
                out.append(content);
            }
        }
    }

}
