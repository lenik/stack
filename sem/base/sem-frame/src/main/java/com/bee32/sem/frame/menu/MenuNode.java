package com.bee32.sem.frame.menu;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.frame.action.IAction;

public class MenuNode
        extends MenuEntry
        implements IMenuNode {

    private Map<String, IMenuNode> children = new LinkedHashMap<String, IMenuNode>();

    public MenuNode(String name) {
        super(name);
    }

    public MenuNode(String name, IAction action) {
        super(name, action);
    }

    @Override
    public int size() {
        return children.size();
    }

    @Override
    public Iterator<IMenuNode> iterator() {
        return children.values().iterator();
    }

    @Override
    public boolean isEmpty() {
        return children.isEmpty();
    }

    protected boolean isLocalBarren() {
        return getAction() == null;
    }

    @Override
    public boolean isBarren() {
        boolean barren = isLocalBarren();
        if (!barren) // local-not-barren
            return false;

        if (isEmpty()) // local-barren && empty.
            return true;

        for (Entry<String, IMenuNode> entry : children.entrySet()) {
            IMenuNode childNode = entry.getValue();
            if (!childNode.isBarren()) {
                barren = false;
                break;
            }
        }
        return barren;
    }

    @Override
    public boolean contains(String childName) {
        return children.containsKey(childName);
    }

    @Override
    public IMenuNode get(String childName) {
        return children.get(childName);
    }

    @Override
    public Collection<IMenuNode> getChildren() {
        return children.values();
    }

    @Override
    public synchronized IMenuNode create(String childName) {
        if (children.containsKey(childName))
            return null;

        MenuNode newChildNode = new MenuNode(childName);

        add(newChildNode);

        return newChildNode;
    }

    @Override
    public synchronized IMenuNode getOrCreate(String childName) {
        IMenuNode childNode = get(childName);

        if (childNode == null)
            childNode = create(childName);

        return childNode;
    }

    @Override
    public synchronized boolean add(IMenuNode childNode) {
        if (childNode == null)
            throw new NullPointerException("childNode");

        String childName = childNode.getName();
        if (children.containsKey(childName))
            return false;

        children.put(childName, childNode);
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
    public MenuNode populate(IMenuEntry menuEntry) {
        super.populate(menuEntry);
        return this;
    }

    @Override
    public MenuNode populate(IMenuNode menuNode) {
        populate((IMenuEntry) menuNode);

        for (IMenuNode childNode : menuNode) {
            String childName = childNode.getName();
            getOrCreate(childName).populate(childNode);
        }

        return this;
    }

    @Override
    public IMenuNode resolve(String path) {
        return resolve(path, false);
    }

    @Override
    public IMenuNode resolve(String path, boolean createIfNotExisted) {
        int slash = path.indexOf('/');
        String head;
        if (slash == -1) {
            head = path;
            path = null;
        } else {
            head = path.substring(0, slash);
            path = path.substring(slash + 1);
        }

        IMenuNode next;
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
    public boolean resolveMerge(String parentMenuPath, IMenuEntry menuEntry) {
        IMenuNode parentMenu = resolve(parentMenuPath, true);

        IMenuNode newChild = parentMenu.getOrCreate(menuEntry.getName());
        assert newChild != null;

        newChild.populate(menuEntry);
        return true;
    }

    @Override
    public boolean resolveMerge(String parentMenuPath, IMenuNode menuNode) {
        IMenuNode parentMenu = resolve(parentMenuPath, true);

        String nodeName = menuNode.getName();
        IMenuNode existing = parentMenu.get(nodeName);
        IMenuNode target;

        if (existing != null) {
            String existingDisp = existing.getAppearance().getDisplayName();
            if (existingDisp != null) {
                // Don't throw anything here.
                return false;
            }
            target = existing;
        } else {
            target = parentMenu.create(nodeName);
        }

        target.populate(menuNode);
        return true;
    }

    protected void format(PrettyPrintStream out) {
        String displayName = getAppearance().getDisplayName();
        if (displayName == null)
            displayName = "\\" + name;

        out.println(displayName + " -> " + getAction());
        out.enter();
        for (Entry<String, IMenuNode> child : children.entrySet()) {
            // String childName = child.getKey();
            MenuNode childNode = (MenuNode) child.getValue();
            childNode.format(out);
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
