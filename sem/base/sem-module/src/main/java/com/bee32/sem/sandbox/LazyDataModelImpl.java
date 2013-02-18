package com.bee32.sem.sandbox;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;

public abstract class LazyDataModelImpl<T>
        extends LazyDataModel<T>
        implements Selectable<T> {

    private static final long serialVersionUID = 1L;

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    T selection;
    List<T> pageCache;
    Integer countCache;

    protected boolean isAutoRefreshCount() {
        return false;
    }

    /**
     * Load a single row.
     *
     * @param rowIndex
     *            0-based row index.
     * @return <code>null</code> if no row available at the specified row index.
     */
    public final T load(int rowIndex) {
        return load(rowIndex, null, SortOrder.ASCENDING);
    }

    /**
     * Load a single row.
     *
     * @param rowIndex
     *            0-based row index.
     * @return <code>null</code> if no row available at the specified row index.
     */
    public final T load(int rowIndex, String sortField, SortOrder sortOrder) {
        if (rowIndex < 0)
            throw new IllegalArgumentException("rowIndex can't be negative.");
        List<T> list = load(rowIndex, 1, sortField, sortOrder, Collections.<String, String> emptyMap());
        if (list.isEmpty())
            return null;
        // assert list.size() == 1;
        return list.get(0);
    }

    public List<T> loadAll() {
        return loadAll(null, SortOrder.ASCENDING);
    }

    public List<T> loadAll(String sortField, SortOrder sortOrder) {
        return load(-1, -1, sortField, sortOrder, Collections.<String, String> emptyMap());
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        List<?> internalList = loadImpl(first, pageSize, sortField, sortOrder, filters);

        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) internalList;

        postLoad(list);

        // if (dtos.size() < pageSize)
        // countCache = dtos.size();
        return pageCache = list;
    }

    protected void postLoad(List<T> data) {
    }

    @Override
    public int getRowIndex() {
        return super.getRowIndex();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (getPageSize() == 0)
            return; // throw new IllegalStateException("pageSize isn't initialized.");
        super.setRowIndex(rowIndex);
    }

    @Override
    public int getRowCount() {
        int count;
        if (isAutoRefreshCount() || countCache == null)
            count = cachedCount();
        else {
            count = countCache;
        }
        return count;
    }

    @Override
    public void setRowCount(int rowCount) {
        countCache = rowCount;
    }

    public void refreshRowCount() {
        if (!isAutoRefreshCount())
            cachedCount();
    }

    protected int cachedCount() {
        countCache = count();
        return countCache;
    }

    protected int count() {
        return countImpl();
    }

    @Override
    public T getSelection() {
        return selection;
    }

    @Override
    public void setSelection(T selection) {
        this.selection = selection;
    }

    @Override
    public boolean isSelected() {
        return selection != null;
    }

    @Override
    public void deselect() {
        selection = null;
    }

    @Override
    public abstract String getRowKey(T object);

    @Override
    public T getRowData(String rowKey) {
        if (rowKey == null)
            throw new NullPointerException("rowKey");
        if (pageCache == null) {
            new FacesUILogger(false).warn("Data not loaded!");
            return null;
        }

        for (T item : pageCache) {
            String itemKey = getRowKey(item);
            if (rowKey.equals(itemKey))
                return item;
        }
        return null;
    }

    /**
     * @param first
     *            0-based row index.
     */
    protected abstract List<?> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters);

    protected abstract int countImpl();

}
