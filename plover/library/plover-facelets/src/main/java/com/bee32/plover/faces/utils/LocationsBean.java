package com.bee32.plover.faces.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.free.UnexpectedException;
import javax.inject.Named;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.rtx.LocationVmap;
import com.bee32.plover.servlet.rtx.YesMap;

/**
 * e.g., #{location.WEB_APP}
 */
@Component
@Named("location")
@Lazy
public class LocationsBean
        extends YesMap<LocationVmap> {

    static final Map<String, LocationVmap> locations = new HashMap<String, LocationVmap>();

    static {
        for (Field field : ILocationConstants.class.getFields()) {

            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers))
                continue;

            if (!Location.class.isAssignableFrom(field.getType()))
                continue;

            String name = field.getName();
            Location locationContext;
            try {
                locationContext = (Location) field.get(null);
            } catch (Exception e) {
                throw new UnexpectedException(e.getMessage(), e);
            }

            LocationVmap wrapper = new LocationVmap(locationContext);
            locations.put(name, wrapper);
        } // for field
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public LocationVmap get(Object key) {
        return locations.get(key);
    }

}
