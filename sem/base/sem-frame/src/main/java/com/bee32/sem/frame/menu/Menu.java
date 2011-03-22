package com.bee32.sem.frame.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bee32.plover.arch.Component;

public class Menu
        extends Component
        implements IMenu {

    private List<IMenuItem> items;

    public Menu(String name) {
        super(name);
        this.items = new ArrayList<IMenuItem>();
    }

    public Menu(String name, List<IMenuItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public Iterator<IMenuItem> iterator() {
        return items.iterator();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean contains(IMenuItem menuItem) {
        return items.contains(menuItem);
    }

    @Override
    public IMenuItem get(int index) {
        return items.get(index);
    }

    @Override
    public int indexOf(IMenuItem menuItem) {
        return items.indexOf(menuItem);
    }

    @Override
    public boolean add(IMenuItem menuItem) {
        return items.add(menuItem);
    }

    @Override
    public void add(int index, IMenuItem menuItem) {
        items.add(index, menuItem);
    }

    @Override
    public boolean remove(IMenuItem menuItem) {
        return items.remove(menuItem);
    }

    @Override
    public IMenuItem remove(int index) {
        return items.remove(index);
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public boolean contribute(String menuPath, IMenu menu) {

        return false;
    }

    @Override
    public boolean contribute(String menuPath, IMenuItem menuItem) {
        return false;
    }

}
