package com.bee32.plover.web.faces.utils;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.servlet.context.ContextLocation;
import com.bee32.plover.servlet.context.LocationContext;
import com.bee32.plover.servlet.util.ThreadServletContext;

public class LocationContextCM
        extends VerbMap {

    private final LocationContext locationContext;

    public LocationContextCM(LocationContext locationContext) {
        if (locationContext == null)
            throw new NullPointerException("locationContext");
        this.locationContext = locationContext;
    }

    @Override
    protected int getRequiredArguments(Object verb) {
        return 0;
    }

    @Override
    protected Object execute(Object verb) {
        String location = String.valueOf(verb);
        ContextLocation cl = locationContext.join(location);

        HttpServletRequest request = ThreadServletContext.requireRequest();

        return cl.resolve(request);
    }

}
