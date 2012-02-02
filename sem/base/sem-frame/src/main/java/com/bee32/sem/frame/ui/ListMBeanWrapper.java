package com.bee32.sem.frame.ui;

import java.util.List;

public class ListMBeanWrapper<T>
        extends ListMBean<T> {

    private static final long serialVersionUID = 1L;
    List<T> list;

    ListMBeanWrapper(Class<T> elementType, List<T> list) {
        super(elementType, null);
        if (list == null)
            throw new NullPointerException("list");
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        if (list == null)
            throw new NullPointerException("list");
        this.list = list;
    }

    public static <T> ListMBeanWrapper<T> decorate(List<T> list, Class<T> elementType) {
        return new ListMBeanWrapper<T>(elementType, list);
    }

}
