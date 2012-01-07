package com.bee32.plover.restful.resource;

import java.util.Collections;
import java.util.List;

import com.bee32.plover.rtx.location.Location;

public abstract class AbstractObjectPageDirectory
        implements IObjectPageDirectory {

    @Override
    public final List<Location> getPagesForView(String viewName) {
        return getPagesForView(viewName, Collections.<String, Object> emptyMap());
    }

    @Override
    public final List<Location> getPagesForOperation(String operationName) {
        return getPagesForOperation(operationName, Collections.<String, Object> emptyMap());
    }

}
