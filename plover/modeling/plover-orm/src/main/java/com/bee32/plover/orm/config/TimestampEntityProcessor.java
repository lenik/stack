package com.bee32.plover.orm.config;

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
    public Collection<?> getEventListeners() {
        return listOf(this);
    }

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event)
            throws HibernateException {
        Object entity = event.getEntity();
        EntityEntry entry = event.getEntry();
        if (entity instanceof Entity<?>) {
            Entity<?> en = (Entity<?>) entity;
            en.setLastModified(new Date());

            Object loadedValue = entry.getLoadedValue("lastModified");
        }
    }

}
