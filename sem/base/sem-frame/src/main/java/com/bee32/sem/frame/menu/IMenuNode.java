package com.bee32.sem.frame.menu;

import java.util.Collection;

public interface IMenuNode
        extends IMenuEntry, Iterable<IMenuNode> {

    int size();

    boolean isEmpty();

    boolean contains(String childName);

    IMenuNode get(String childName);

    Collection<IMenuNode> getChildren();

    /**
     * Create a new child if it's not existed.
     *
     * @param childName
     *            Name for the new created child node.
     * @return Returns <code>null</code> if child with same name is existed.
     */
    IMenuNode create(String childName);

    IMenuNode getOrCreate(String childName);

    boolean add(IMenuNode childNode);

    boolean remove(String childName);

    void clear();

    /**
     * Populate with a given menu item, without any child.
     *
     * @param menuEntry
     *            The source menu item.
     * @return This menu node.
     */
    IMenuNode populate(IMenuEntry menuEntry);

    /**
     * Populate with a given menu node and all its children.
     *
     * @param menuNode
     *            The source node.
     * @return This menu node.
     */
    IMenuNode populate(IMenuNode menuNode);

    /**
     * Resolve the menu path.
     *
     * @param path
     *            The menu path to be resolved.
     * @return The resolved node, <code>null</code> if not existed.
     */
    IMenuNode resolve(String path);

    /**
     * Resolve the menu path, create any intermediate nodes if <code>true</code> value of
     * <code>createIfNotExisted</code> is specified.
     *
     * @param path
     *            The menu path to be resolved.
     * @param createIfNotExisted
     *            Whether to create intermediate entries
     * @return The resolved node.
     */
    IMenuNode resolve(String path, boolean createIfNotExisted);

    /**
     * Add a menu node within specific parent menu.
     *
     * This is equiv. to
     *
     * <pre>
     * resolve(parentMenuPath, true).create(menuEntry.getName()).populate(menuEntry)
     * </pre>
     *
     * @return <code>false</code> if a menu of same path exists.
     */
    boolean resolveMerge(String parentMenuPath, IMenuEntry menuEntry);

    /**
     * Add a menu node and all its children within specific parent menu.
     *
     * This is equiv. to
     *
     * <pre>
     * resolve(parentMenuPath, true).create(menuEntry.getName()).populate(menuNode)
     * </pre>
     *
     * @return <code>false</code> if a menu of same path exists.
     */
    boolean resolveMerge(String parentMenuPath, IMenuNode menuNode);

}
