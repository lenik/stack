package com.bee32.plover.restful.resource;

import java.util.Collections;

import com.bee32.plover.rtx.location.Location;

public abstract class AbstractObjectPageDirectory
        implements IObjectPageDirectory {

    public AbstractObjectPageDirectory() {
    }

    public Location getPageForView(String viewName) {
        return getPageForView(viewName, Collections.<String, Object> emptyMap());
    }

    public Location getPageForOperation(String operationName) {
        return getPageForOperation(operationName, Collections.<String, Object> emptyMap());
    }

}
