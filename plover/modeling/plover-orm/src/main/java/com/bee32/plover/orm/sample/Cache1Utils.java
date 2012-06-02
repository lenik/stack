package com.bee32.plover.orm.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.engine.EntityEntry;
import org.hibernate.engine.EntityKey;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.StatefulPersistenceContext;
import org.hibernate.impl.SessionImpl;

import com.bee32.plover.orm.entity.Entity;

public class Cache1Utils {

    /**
     * Session.evict() effects: {@link PersistenceContext#removeEntry(Object)},
     * {@link PersistenceContext#removeEntity(EntityKey)}
     */
    public static List<Entity<?>> getCachedEntities(Session session) {
        SessionImpl sessionImpl = (SessionImpl) session;
        PersistenceContext _pctx = sessionImpl.getPersistenceContext();
        StatefulPersistenceContext pctx = (StatefulPersistenceContext) _pctx;
        Map<Entity<?>, EntityEntry> entityEntries = pctx.getEntityEntries();

        // StringBuilder sb = new StringBuilder();
        List<Entity<?>> list = new ArrayList<Entity<?>>();
        for (Entry<Entity<?>, EntityEntry> ent : entityEntries.entrySet()) {
            Entity<?> entity = ent.getKey();
            // EntityEntry ee = ent.getValue();
            // sb.append(entity);
            list.add(entity);
        }
        // return sb.toString();
        return list;
    }

}
