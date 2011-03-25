package com.bee32.icsf.access.acl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ArrayACL
        extends AbstractACL {

    private List<Entry> entries = new ArrayList<Entry>();

    public ArrayACL() {
    }

    public ArrayACL(Collection<? extends Entry> entries) {
        if (entries != null)
            for (Entry entry : entries)
                this.add(entry);
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public final List<Entry> getEntries() {
        return entries;
    }

    @Override
    public final void add(Entry entry) {
        entries.add(entry);
        onEntryChanged(entry, false);
    }

    @Override
    public final boolean remove(Entry entry) {
        boolean removed = entries.remove(entry);
        if (removed)
            onEntryChanged(entry, true);
        return removed;
    }

    /**
     * 在指定位置插入一个权限条目。
     *
     * @throws UnsupportedOperationException
     *             如果本 ACL 是只读的。
     */
    public final void add(int index, Entry entry) {
        entries.add(index, entry);
        onEntryChanged(entry, false);
    }

    /**
     * 删除指定位置的权限条目。
     *
     * @throws IndexOutOfBoundsException
     */
    public final void remove(int index) {
        Entry removedEntry = entries.remove(index);
        onEntryChanged(removedEntry, true);
    }

    protected abstract void onEntryChanged(Entry entry, boolean removed);

}
