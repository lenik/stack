package com.bee32.plover.servlet.rabbits;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jetty.util.ConcurrentHashSet;

public class OverlappedBases {

    static final List<IResourceBase> searchBases = new ArrayList<IResourceBase>();

    public static int size() {
        return searchBases.size();
    }

    public static boolean contains(Object o) {
        return searchBases.contains(o);
    }

    public static int indexOf(Object o) {
        return searchBases.indexOf(o);
    }

    public static int lastIndexOf(Object o) {
        return searchBases.lastIndexOf(o);
    }

    public static IResourceBase get(int index) {
        return searchBases.get(index);
    }

    public static boolean add(IResourceBase e) {
        return searchBases.add(e);
    }

    public static boolean add(Class<?> baseClass) {
        return searchBases.add(new ClassResourceBase(baseClass));
    }

    public static boolean add(String prefix) {
        return searchBases.add(new PrefixResourceBase(prefix));
    }

    public static void add(int index, IResourceBase element) {
        searchBases.add(index, element);
    }

    public static IResourceBase remove(int index) {
        return searchBases.remove(index);
    }

    public static boolean remove(Object o) {
        return searchBases.remove(o);
    }

    public static void clear() {
        searchBases.clear();
    }

    public static Iterator<IResourceBase> iterator() {
        return searchBases.iterator();
    }

    static Set<String> notFound = new ConcurrentHashSet<String>();

    /**
     * Search resource.
     *
     * @param path
     *            The leading / is ignored.
     * @return The resolved resource, <code>null</code> if resource denoted by the given
     *         <code>path</code> doesn't exist.
     */
    public static URL searchResource(String path) {
        // Always remove the leading /.
        if (path.startsWith("/"))
            path = path.substring(1);

        if (notFound.contains(path))
            return null;

        for (IResourceBase base : searchBases) {
            URL resource = base.getResource(path);
            if (resource != null)
                return resource;
        }

        notFound.add(path);
        return null;
    }

}
