package com.bee32.plover.orm.access;

import java.util.Collection;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.engine.EntityEntry;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;

import com.bee32.plover.orm.entity.Entity;

public class TimestampEntityProcessor
        extends AbstractEntityProcessor
        implements SaveOrUpdateEventListener {

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<String> getInterestingEvents() {
        return listOf("save-update");
    }

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event)
            throws HibernateException {
        EntityEntry entry = event.getEntry();
        if (entry != null) {
            Object loadedValue = entry.getLoadedValue("lastModified");
            System.out.println("Loaded-value: " + loadedValue);
        }

        Object obj = event.getObject();
        if (obj instanceof Entity<?>) {
            Entity<?> en = (Entity<?>) obj;
            en.setLastModified(new Date());
        }
    }

}
