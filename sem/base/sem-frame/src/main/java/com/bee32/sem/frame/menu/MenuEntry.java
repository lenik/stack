package com.bee32.sem.frame.menu;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.frame.action.IAction;

public class MenuEntry
        extends MenuItem
        implements IMenuEntry {

    private Map<String, IMenuEntry> children = new LinkedHashMap<String, IMenuEntry>();

    public MenuEntry(String name) {
        super(name);
    }

    public MenuEntry(String name, IAction action) {
        super(name, action);
    }

    @Override
    public int size() {
        return children.size();
    }

    @Override
    public Iterator<IMenuEntry> iterator() {
        return children.values().iterator();
    }

    @Override
    public boolean isEmpty() {
        return children.isEmpty();
    }

    @Override
    public boolean contains(String childName) {
        return children.containsKey(childName);
    }

    @Override
    public IMenuEntry get(String childName) {
        return children.get(childName);
    }

    @Override
    public synchronized IMenuEntry create(String childName) {
        if (children.containsKey(childName))
            return null;

        MenuEntry newChildEntry = new MenuEntry(childName);

        add(newChildEntry);

        return newChildEntry;
    }

    @Override
    public synchronized IMenuEntry getOrCreate(String childName) {
        IMenuEntry childEntry = get(childName);

        if (childEntry == null)
            childEntry = create(childName);

        return childEntry;
    }

    @Override
    public synchronized boolean add(IMenuEntry childEntry) {
        if (childEntry == null)
            throw new NullPointerException("childEntry");

        String childName = childEntry.getName();
        if (children.containsKey(childName))
            return false;

        children.put(childName, childEntry);
        return true;
    }

    @Override
    public boolean remove(String childName) {
        return children.remove(childName) != null;
    }

    @Override
    public void clear() {
        children.clear();
    }

    @Override
    public MenuEntry populate(IMenuItem menuItem) {
        super.populate(menuItem);
        return this;
    }

    @Override
    public MenuEntry populate(IMenuEntry menuEntry) {
        populate((IMenuItem) menuEntry);

        for (IMenuEntry childEntry : menuEntry) {
            String childName = childEntry.getName();
            getOrCreate(childName).populate(childEntry);
        }

        return this;
    }

    @Override
    public IMenuEntry resolve(String path) {
        return resolve(path, false);
    }

    @Override
    public IMenuEntry resolve(String path, boolean createIfNotExisted) {
        int slash = path.indexOf('/');
        String head;
        if (slash == -1) {
            head = path;
            path = null;
        } else {
            head = path.substring(0, slash);
            path = path.substring(slash + 1);
        }

        IMenuEntry next;
        if (".".equals(head)) {
            next = this;
        } else {
            if (createIfNotExisted)
                next = this.getOrCreate(head);
            else
                next = this.get(head);
        }

        if (path == null)
            return next;

        return next.resolve(path, createIfNotExisted);
    }

    @Override
    public boolean resolveMerge(String parentMenuPath, IMenuItem menuItem) {
        IMenuEntry parentMenu = resolve(parentMenuPath, true);

        IMenuEntry newChild = parentMenu.create(menuItem.getName());
        if (newChild == null)
            return false;

        newChild.populate(menuItem);
        return true;
    }

    @Override
    public boolean resolveMerge(String parentMenuPath, IMenuEntry menuEntry) {
        IMenuEntry parentMenu = resolve(parentMenuPath, true);

        IMenuEntry newChild = parentMenu.create(menuEntry.getName());
        if (newChild == null)
            return false;

        newChild.populate(menuEntry);
        return true;
    }

    protected void format(PrettyPrintStream out) {
        out.println(name + " -> " + getAction());
        out.enter();
        for (Entry<String, IMenuEntry> child : children.entrySet()) {
            // String childName = child.getKey();
            MenuEntry childEntry = (MenuEntry) child.getValue(); // XXX interface?
            childEntry.format(out);
        }
        out.leave();
    }

    @Override
    public String toString() {
        PrettyPrintStream out = new PrettyPrintStream();
        format(out);
        return out.toString();
    }

}
