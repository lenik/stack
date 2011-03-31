package com.bee32.plover.arch.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.bee32.plover.arch.Component;

public abstract class ClassCatalog
        extends Component
        implements Iterable<Class<?>> {

    private final Set<Class<?>> classes = new HashSet<Class<?>>();

    private boolean assembled;

    public ClassCatalog(ClassCatalog... imports) {
        super();
        for (ClassCatalog imp : imports)
            classes.addAll(imp.getClasses());
    }

    public ClassCatalog(String name, ClassCatalog... imports) {
        super(name);
        for (ClassCatalog imp : imports)
            classes.addAll(imp.getClasses());
    }

    @Override
    public final Iterator<Class<?>> iterator() {
        return getClasses().iterator();
    }

    public Set<Class<?>> getClasses() {
        assemble();
        return classes;
    }

    public void add(Class<?> clazz) {
        classes.add(clazz);
    }

    public void remove(Class<?> clazz) {
        getClasses().remove(clazz);
    }

    public synchronized void assemble() {
        if (assembled)
            return;

        preamble();

        assembled = true;
    }

    protected abstract void preamble();

}
