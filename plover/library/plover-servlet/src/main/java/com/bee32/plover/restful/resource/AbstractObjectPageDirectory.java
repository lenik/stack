package com.bee32.plover.restful.resource;

import java.util.Collections;

import com.bee32.plover.rtx.location.Location;

public abstract class AbstractObjectPageDirectory
        implements IObjectPageDirectory {

    @Override
    public final Location getPageForView(String viewName) {
        return getPageForView(viewName, Collections.<String, Object> emptyMap());
    }

    @Override
    public final Location getPageForOperation(String operationName) {
        return getPageForOperation(operationName, Collections.<String, Object> emptyMap());
    }

}
