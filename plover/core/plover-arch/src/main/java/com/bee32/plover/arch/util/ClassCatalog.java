package com.bee32.plover.arch.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.arch.Component;

public abstract class ClassCatalog
        extends Component
        implements Iterable<Class<?>> {

    private final Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
    private final Set<Class<?>> localClasses = new LinkedHashSet<Class<?>>();
    private final Set<ClassCatalog> dependencies = new LinkedHashSet<ClassCatalog>();
    private final Map<Class<?>, ClassCatalog> invMap = new HashMap<Class<?>, ClassCatalog>();

    private boolean assembled;

    public ClassCatalog(ClassCatalog... catalogs) {
        super();
        _import(catalogs);
    }

    public ClassCatalog(String name, ClassCatalog... catalogs) {
        super(name);
        _import(catalogs);
    }

    protected void _import(ClassCatalog... catalogs) {
        for (ClassCatalog catalog : catalogs) {
            classes.addAll(catalog.getClasses());
            dependencies.add(catalog);
            invMap.putAll(catalog.getInvMap());
        }
    }

    @Override
    public final Iterator<Class<?>> iterator() {
        return getClasses().iterator();
    }

    public Set<Class<?>> getClasses() {
        assemble();
        return classes;
    }

    public Set<Class<?>> getLocalClasses() {
        assemble();
        return localClasses;
    }

    public Set<? extends ClassCatalog> getDependencies() {
        assemble();
        return dependencies;
    }

    public Set<? extends ClassCatalog> getAllDependencies() {
        Set<ClassCatalog> dependencies = new LinkedHashSet<ClassCatalog>();
        findAllDependencies(dependencies);
        return dependencies;
    }

    void findAllDependencies(Set<ClassCatalog> dependencies) {
        for (ClassCatalog dependency : getDependencies()) {
            dependency.findAllDependencies(dependencies);
        }
        dependencies.add(this);
    }

    public Map<Class<?>, ClassCatalog> getInvMap() {
        assemble();
        return invMap;
    }

    public ClassCatalog which(Class<?> clazz) {
        return getInvMap().get(clazz);
    }

    public void add(Class<?> clazz) {
        classes.add(clazz);
        localClasses.add(clazz);
        invMap.put(clazz, this);
    }

    public final void add(Class<?>... classes) {
        for (Class<?> c : classes)
            add(c);
    }

    public final void addAll(Collection<? extends Class<?>> classes) {
        for (Class<?> c : classes)
            add(c);
    }

    /**
     * Do assemble before remove.
     */
    @Deprecated
    public void remove(Class<?> clazz) {
        getClasses().remove(clazz);
        localClasses.remove(clazz);
    }

    public synchronized void assemble() {
        if (assembled)
            return;

        internalAssemble();
        preamble();

        assembled = true;
    }

    protected abstract void internalAssemble();

    protected abstract void preamble();

}
