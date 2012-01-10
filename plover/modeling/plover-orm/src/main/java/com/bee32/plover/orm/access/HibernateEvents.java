package com.bee32.plover.orm.access;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.event.AutoFlushEventListener;
import org.hibernate.event.DeleteEventListener;
import org.hibernate.event.DirtyCheckEventListener;
import org.hibernate.event.EvictEventListener;
import org.hibernate.event.FlushEntityEventListener;
import org.hibernate.event.FlushEventListener;
import org.hibernate.event.InitializeCollectionEventListener;
import org.hibernate.event.LoadEventListener;
import org.hibernate.event.LockEventListener;
import org.hibernate.event.MergeEventListener;
import org.hibernate.event.PersistEventListener;
import org.hibernate.event.PostCollectionRecreateEventListener;
import org.hibernate.event.PostCollectionRemoveEventListener;
import org.hibernate.event.PostCollectionUpdateEventListener;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostLoadEventListener;
import org.hibernate.event.PostUpdateEventListener;
import org.hibernate.event.PreCollectionRecreateEventListener;
import org.hibernate.event.PreCollectionRemoveEventListener;
import org.hibernate.event.PreCollectionUpdateEventListener;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreLoadEventListener;
import org.hibernate.event.PreUpdateEventListener;
import org.hibernate.event.RefreshEventListener;
import org.hibernate.event.ReplicateEventListener;
import org.hibernate.event.SaveOrUpdateEventListener;

public class HibernateEvents {

    static Map<String, Class<?>> eventTypes = new HashMap<String, Class<?>>();

    // static Map<Class<?>, String> eventNames = new HashMap<Class<?>, String>();

    public static void declare(String name, Class<?> type) {
        Class<?> oldType = eventTypes.get(name);
        if (oldType != null)
            throw new IllegalArgumentException("Event name duplicated: " + name + ": " + oldType + " -> " + type);
        eventTypes.put(name, type);
        // eventNames.put(type, name);
    }

    public static Class<?> getEventType(String eventName) {
        return eventTypes.get(eventName);
    }

    static {
        declare("auto-flush", AutoFlushEventListener.class);
        declare("merge", MergeEventListener.class);
        declare("create", PersistEventListener.class);
        declare("create-onflush", PersistEventListener.class);
        declare("delete", DeleteEventListener.class);
        declare("dirty-check", DirtyCheckEventListener.class);
        declare("evict", EvictEventListener.class);
        declare("flush", FlushEventListener.class);
        declare("flush-entity", FlushEntityEventListener.class);
        declare("load", LoadEventListener.class);
        declare("load-collection", InitializeCollectionEventListener.class);
        declare("lock", LockEventListener.class);
        declare("refresh", RefreshEventListener.class);
        declare("replicate", ReplicateEventListener.class);
        declare("save-update", SaveOrUpdateEventListener.class);
        declare("save", SaveOrUpdateEventListener.class);
        declare("update", SaveOrUpdateEventListener.class);
        declare("pre-load", PreLoadEventListener.class);
        declare("pre-update", PreUpdateEventListener.class);
        declare("pre-delete", PreDeleteEventListener.class);
        declare("pre-insert", PreInsertEventListener.class);
        declare("pre-collection-recreate", PreCollectionRecreateEventListener.class);
        declare("pre-collection-remove", PreCollectionRemoveEventListener.class);
        declare("pre-collection-update", PreCollectionUpdateEventListener.class);
        declare("post-load", PostLoadEventListener.class);
        declare("post-update", PostUpdateEventListener.class);
        declare("post-delete", PostDeleteEventListener.class);
        declare("post-insert", PostInsertEventListener.class);
        declare("post-commit-update", PostUpdateEventListener.class);
        declare("post-commit-delete", PostDeleteEventListener.class);
        declare("post-commit-insert", PostInsertEventListener.class);
        declare("post-collection-recreate", PostCollectionRecreateEventListener.class);
        declare("post-collection-remove", PostCollectionRemoveEventListener.class);
        declare("post-collection-update", PostCollectionUpdateEventListener.class);
    }

}
