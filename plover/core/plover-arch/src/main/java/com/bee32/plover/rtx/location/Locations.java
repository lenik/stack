package com.bee32.plover.rtx.location;

public class Locations
        implements ILocationConstants {

    /**
     * Get the "root location" by location context name.
     *
     * @return <code>null</code> if the context is not defined.
     */
    public static Location getLocationContext(String name) {
        return Location.contextNames.get(name);
    }

    public static Location join(String contextName, String relativePath) {
        Location locationContext = getLocationContext(contextName);
        if (locationContext == null)
            throw new IllegalArgumentException("Invalid location context name: " + contextName);

        return locationContext.join(relativePath);
    }

    /**
     * @example Servlet-Context::/parent/child/dir/test.png
     */
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

    public static String qualify(Location location) {
        String qualified = location.getQualified();
        return qualified;
    }

}
