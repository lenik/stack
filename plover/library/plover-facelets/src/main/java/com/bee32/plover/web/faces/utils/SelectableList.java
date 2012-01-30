package com.bee32.plover.web.faces.utils;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class SelectableList<T>
        extends ListDataModel<T>
        implements SelectableDataModel<T> {

    List<T> list;

    SelectableList(List<T> list) {
        super(list);
        this.list = list;
    }

    @Override
    public String getRowKey(T object) {
        int index = list.indexOf(object);
        return String.valueOf(index);
    }

    @Override
    public T getRowData(String rowKey) {
        int index = Integer.parseInt(rowKey);
        if (index == -1)
            return null;
        else
            return list.get(index);
    }

    public static <T> SelectableList<T> decorate(List<T> list) {
        return new SelectableList<>(list);
    }

}
