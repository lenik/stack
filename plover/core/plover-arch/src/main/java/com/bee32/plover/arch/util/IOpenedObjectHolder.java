package com.bee32.plover.arch.util;

import java.util.List;

public interface IOpenedObjectHolder {

    boolean isOpened();

    <T> List<T> getOpenedObjects();

    void setOpenedObjects(List<?> openedObjects);

    <T> T getOpenedObject();

    void setOpenedObject(Object openedObject);

    void addObjectOpenListener(IObjectOpenListener listener);

    void removeObjectOpenListener(IObjectOpenListener listener);

}
