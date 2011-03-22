package com.bee32.sem.frame.menu;

import java.util.Iterator;

import com.bee32.plover.arch.IComponent;

public interface IMenu
        extends IComponent, Iterable<IMenuItem> {

    int size();

    Iterator<IMenuItem> iterator();

    boolean isEmpty();

    boolean contains(IMenuItem menuItem);

    IMenuItem get(int index);

    int indexOf(IMenuItem menuItem);

    boolean add(IMenuItem menuItem);

    void add(int index, IMenuItem menuItem);

    boolean remove(IMenuItem menuItem);

    IMenuItem remove(int index);

    void clear();

    /**
     * Add a menu at th especified path.
     * <p>
     * The path is a list of menu entry names separated by '/'.
     *
     * @return <code>false</code> if a menu of same path exists.
     */
    boolean contribute(String menuPath, IMenu menu);

    /**
     * Add a menu item at th especified path.
     * <p>
     * The path is a list of menu entry names separated by '/'.
     *
     * @return <code>false</code> if a menu of same path exists.
     */
    boolean contribute(String menuPath, IMenuItem menuItem);

}
