package com.bee32.plover.servlet.context;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.free.UnexpectedException;

public class LocationConstantsProvider
        implements ILocationProvider {

    @Override
    public Map<String, Location> getLocations() {

        Map<String, Location> locations = new HashMap<String, Location>();

        for (Field field : ILocationConstants.class.getFields()) {

            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers))
                continue;

            if (!Location.class.isAssignableFrom(field.getType()))
                continue;

            String name = field.getName();
            Location location;
            try {
                location = (Location) field.get(null);
            } catch (Exception e) {
                throw new UnexpectedException(e.getMessage(), e);
            }

            locations.put(name, location);
        } // for field

        return locations;
    }

}
