package com.bee32.plover.rtx.location;

public interface IContextLocationResolver {

    /**
     * Get the relative location to the given one.
     *
     * @param contextKey
     *            Non-<code>null</code> context key.
     * @param targetLocation
     *            Where the returned location is relocated to, must be non-<code>null</code>.
     * @return <code>null</code> If specified <code>key</code> is unknown.
     */
    String getRelativeLocation(Location context, String targetLocation);

}
