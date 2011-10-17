package com.bee32.sem.frame.menu;

public interface IMenuBuilder<T> {

    boolean isShowAll();

    void setShowAll(boolean showAll);

    T buildMenubar(IMenuNode virtualRoot);

}
