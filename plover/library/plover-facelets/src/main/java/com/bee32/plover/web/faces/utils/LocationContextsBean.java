package com.bee32.plover.web.faces.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.free.UnexpectedException;
import javax.inject.Named;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.servlet.context.LocationContext;
import com.bee32.plover.servlet.context.LocationContextConstants;

/**
 * e.g., #{location.WEB_APP}
 */
@Lazy
@Component
@Named("location")
public class LocationContextsBean
        extends YesMap {

    static final Map<String, LocationContextCM> locations = new HashMap<String, LocationContextCM>();

    static {
        for (Field field : LocationContextConstants.class.getFields()) {

            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers))
                continue;

            if (!LocationContext.class.isAssignableFrom(field.getType()))
                continue;

            String name = field.getName();
            LocationContext locationContext;
            try {
                locationContext = (LocationContext) field.get(null);
            } catch (Exception e) {
                throw new UnexpectedException(e.getMessage(), e);
            }

            LocationContextCM wrapper = new LocationContextCM(locationContext);
            locations.put(name, wrapper);
        } // for field
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public Object get(Object key) {
        return locations.get(key);
    }

}
