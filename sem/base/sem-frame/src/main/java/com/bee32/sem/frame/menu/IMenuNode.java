package com.bee32.sem.frame.menu;

public interface IMenuNode
        extends IMenuEntry, Iterable<IMenuNode> {

    int size();

    boolean isEmpty();

    boolean contains(String childName);

    IMenuNode get(String childName);

    /**
     * Create a new child if it's not existed.
     *
     * @param childName
     *            Name for the new created child entry.
     * @return Returns <code>null</code> if child with same name is existed.
     */
    IMenuNode create(String childName);

    IMenuNode getOrCreate(String childName);

    boolean add(IMenuNode childEntry);

    boolean remove(String childName);

    void clear();

    /**
     * Populate with a given menu item, without any child.
     *
     * @param menuItem
     *            The source menu item.
     * @return This menu entry.
     */
    IMenuNode populate(IMenuEntry menuItem);

    /**
     * Populate with a given menu entry and all its children.
     *
     * @param menuEntry
     *            The source entry.
     * @return This menu entry.
     */
    IMenuNode populate(IMenuNode menuEntry);

    /**
     * Resolve the menu path.
     *
     * @param path
     *            The menu path to be resolved.
     * @return The resolved entry, <code>null</code> if not existed.
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
     * @return The resolved entry.
     */
    IMenuNode resolve(String path, boolean createIfNotExisted);

    /**
     * Add a menu entry within specific parent menu.
     *
     * This is equiv. to
     *
     * <pre>
     * resolve(parentMenuPath, true).create(menuItem.getName()).populate(menuItem)
     * </pre>
     *
     * @return <code>false</code> if a menu of same path exists.
     */
    boolean resolveMerge(String parentMenuPath, IMenuEntry menuItem);

    /**
     * Add a menu entry and all its children within specific parent menu.
     *
     * This is equiv. to
     *
     * <pre>
     * resolve(parentMenuPath, true).create(menuItem.getName()).populate(menuEntry)
     * </pre>
     *
     * @return <code>false</code> if a menu of same path exists.
     */
    boolean resolveMerge(String parentMenuPath, IMenuNode menuEntry);

}
