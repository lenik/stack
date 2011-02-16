package com.bee32.icsf.access.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayACL
        extends AbstractACL {

    private List<Entry> entries;

    public ArrayACL() {
        this.entries = new ArrayList<Entry>();
    }

    public ArrayACL(Collection<? extends Entry> entries) {
        if (entries == null)
            this.entries = new ArrayList<Entry>();
        else
            this.entries = new ArrayList<Entry>(entries);
    }

    @Override
    public Collection<? extends Entry> getEntries() {
        return entries;
    }

    @Override
    public void add(Entry entry) {
        entries.add(entry);
    }

    @Override
    public boolean remove(Entry entry) {
        return entries.remove(entry);
    }

    /**
     * 在指定位置插入一个权限条目。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    public void add(int index, Entry entry) {
        entries.add(index, entry);
    }

    /**
     * 删除指定位置的权限条目。
     *
     * @throws IndexOutOfBoundsException
     */
    public void remove(int index) {
        entries.remove(index);
    }

}
