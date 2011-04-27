package com.bee32.plover.servlet.context;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class Locations
        implements ILocationConstants {

    static final Map<String, Location> locations;
    static {
        locations = new HashMap<String, Location>();

        for (ILocationProvider locationProvider : ServiceLoader.load(ILocationProvider.class)) {
            Map<String, Location> each = locationProvider.getLocations();
            locations.putAll(each);
        }
    }

    public static Location getLocationContext(String name) {
        return locations.get(name);
    }

    public static Location join(String contextName, String relativePath) {
        Location locationContext = getLocationContext(contextName);
        if (locationContext == null)
            throw new IllegalArgumentException("Invalid location context name: " + contextName);

        return locationContext.join(relativePath);
    }

    public static Location parse(String qualifiedLocation) {
        int delim = qualifiedLocation.indexOf("::");
        if (delim != -1) {
            String contextName = qualifiedLocation.substring(0, delim);
            String relativePath = qualifiedLocation.substring(delim + 2);
            return join(contextName, relativePath);
        }

        String location = qualifiedLocation;
        if (location.contains("://"))
            return URL.join(location);

        // throw new IllegalUsageException("Unqualified location: " + qualifiedLocation);
        return WEB_APP.join(location);
    }

}
