package com.bee32.plover.orm.access;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.free.Strings;

import org.hibernate.event.*;

public class HibernateEvents {

    static Map<String, Class<?>> eventTypes = new LinkedHashMap<String, Class<?>>();

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

    public static Object[] getEventListeners(EventListeners eventListeners, String name) {
        switch (name) {
        case "auto-flush":
            return eventListeners.getAutoFlushEventListeners();
        case "merge":
            return eventListeners.getMergeEventListeners();
        case "create":
            return eventListeners.getPersistEventListeners();
        case "create-onflush":
            return eventListeners.getPersistOnFlushEventListeners();
        case "delete":
            return eventListeners.getDeleteEventListeners();
        case "dirty-check":
            return eventListeners.getDirtyCheckEventListeners();
        case "evict":
            return eventListeners.getEvictEventListeners();
        case "flush":
            return eventListeners.getFlushEventListeners();
        case "flush-entity":
            return eventListeners.getFlushEntityEventListeners();
        case "load":
            return eventListeners.getLoadEventListeners();
        case "load-collection":
            return eventListeners.getInitializeCollectionEventListeners();
        case "lock":
            return eventListeners.getLockEventListeners();
        case "refresh":
            return eventListeners.getRefreshEventListeners();
        case "replicate":
            return eventListeners.getReplicateEventListeners();
        case "save-update":
            return eventListeners.getSaveOrUpdateEventListeners();
        case "save":
            return eventListeners.getSaveEventListeners();
        case "update":
            return eventListeners.getUpdateEventListeners();
        case "pre-load":
            return eventListeners.getPreLoadEventListeners();
        case "pre-update":
            return eventListeners.getPreUpdateEventListeners();
        case "pre-delete":
            return eventListeners.getPreDeleteEventListeners();
        case "pre-insert":
            return eventListeners.getPreInsertEventListeners();
        case "pre-collection-recreate":
            return eventListeners.getPreCollectionRecreateEventListeners();
        case "pre-collection-remove":
            return eventListeners.getPreCollectionRemoveEventListeners();
        case "pre-collection-update":
            return eventListeners.getPreCollectionUpdateEventListeners();
        case "post-load":
            return eventListeners.getPostLoadEventListeners();
        case "post-update":
            return eventListeners.getPostUpdateEventListeners();
        case "post-delete":
            return eventListeners.getPostDeleteEventListeners();
        case "post-insert":
            return eventListeners.getPostInsertEventListeners();
        case "post-commit-update":
            return eventListeners.getPostCommitUpdateEventListeners();
        case "post-commit-delete":
            return eventListeners.getPostCommitDeleteEventListeners();
        case "post-commit-insert":
            return eventListeners.getPostCommitInsertEventListeners();
        case "post-collection-recreate":
            return eventListeners.getPostCollectionRecreateEventListeners();
        case "post-collection-remove":
            return eventListeners.getPostCollectionRemoveEventListeners();
        case "post-collection-update":
            return eventListeners.getPostCollectionUpdateEventListeners();
        }
        throw new IllegalArgumentException("Bad event name: " + name);
    }

    public static void setEventListeners(EventListeners eventListeners, String name, Object[] listeners) {
        switch (name) {
        case "auto-flush":
            eventListeners.setAutoFlushEventListeners((AutoFlushEventListener[]) listeners);
            break;
        case "merge":
            eventListeners.setMergeEventListeners((MergeEventListener[]) listeners);
            break;
        case "create":
            eventListeners.setPersistEventListeners((PersistEventListener[]) listeners);
            break;
        case "create-onflush":
            eventListeners.setPersistOnFlushEventListeners((PersistEventListener[]) listeners);
            break;
        case "delete":
            eventListeners.setDeleteEventListeners((DeleteEventListener[]) listeners);
            break;
        case "dirty-check":
            eventListeners.setDirtyCheckEventListeners((DirtyCheckEventListener[]) listeners);
            break;
        case "evict":
            eventListeners.setEvictEventListeners((EvictEventListener[]) listeners);
            break;
        case "flush":
            eventListeners.setFlushEventListeners((FlushEventListener[]) listeners);
            break;
        case "flush-entity":
            eventListeners.setFlushEntityEventListeners((FlushEntityEventListener[]) listeners);
            break;
        case "load":
            eventListeners.setLoadEventListeners((LoadEventListener[]) listeners);
            break;
        case "load-collection":
            eventListeners.setInitializeCollectionEventListeners((InitializeCollectionEventListener[]) listeners);
            break;
        case "lock":
            eventListeners.setLockEventListeners((LockEventListener[]) listeners);
            break;
        case "refresh":
            eventListeners.setRefreshEventListeners((RefreshEventListener[]) listeners);
            break;
        case "replicate":
            eventListeners.setReplicateEventListeners((ReplicateEventListener[]) listeners);
            break;
        case "save-update":
            eventListeners.setSaveOrUpdateEventListeners((SaveOrUpdateEventListener[]) listeners);
            break;
        case "save":
            eventListeners.setSaveEventListeners((SaveOrUpdateEventListener[]) listeners);
            break;
        case "update":
            eventListeners.setUpdateEventListeners((SaveOrUpdateEventListener[]) listeners);
            break;
        case "pre-load":
            eventListeners.setPreLoadEventListeners((PreLoadEventListener[]) listeners);
            break;
        case "pre-update":
            eventListeners.setPreUpdateEventListeners((PreUpdateEventListener[]) listeners);
            break;
        case "pre-delete":
            eventListeners.setPreDeleteEventListeners((PreDeleteEventListener[]) listeners);
            break;
        case "pre-insert":
            eventListeners.setPreInsertEventListeners((PreInsertEventListener[]) listeners);
            break;
        case "pre-collection-recreate":
            eventListeners.setPreCollectionRecreateEventListeners((PreCollectionRecreateEventListener[]) listeners);
            break;
        case "pre-collection-remove":
            eventListeners.setPreCollectionRemoveEventListeners((PreCollectionRemoveEventListener[]) listeners);
            break;
        case "pre-collection-update":
            eventListeners.setPreCollectionUpdateEventListeners((PreCollectionUpdateEventListener[]) listeners);
            break;
        case "post-load":
            eventListeners.setPostLoadEventListeners((PostLoadEventListener[]) listeners);
            break;
        case "post-update":
            eventListeners.setPostUpdateEventListeners((PostUpdateEventListener[]) listeners);
            break;
        case "post-delete":
            eventListeners.setPostDeleteEventListeners((PostDeleteEventListener[]) listeners);
            break;
        case "post-insert":
            eventListeners.setPostInsertEventListeners((PostInsertEventListener[]) listeners);
            break;
        case "post-commit-update":
            eventListeners.setPostCommitUpdateEventListeners((PostUpdateEventListener[]) listeners);
            break;
        case "post-commit-delete":
            eventListeners.setPostCommitDeleteEventListeners((PostDeleteEventListener[]) listeners);
            break;
        case "post-commit-insert":
            eventListeners.setPostCommitInsertEventListeners((PostInsertEventListener[]) listeners);
            break;
        case "post-collection-recreate":
            eventListeners.setPostCollectionRecreateEventListeners((PostCollectionRecreateEventListener[]) listeners);
            break;
        case "post-collection-remove":
            eventListeners.setPostCollectionRemoveEventListeners((PostCollectionRemoveEventListener[]) listeners);
            break;
        case "post-collection-update":
            eventListeners.setPostCollectionUpdateEventListeners((PostCollectionUpdateEventListener[]) listeners);
            break;
        default:
            throw new IllegalArgumentException("Bad event name: " + name);
        }
    }

    public static void main(String[] args) {
        for (String name : eventTypes.keySet()) {
            StringBuilder capName = new StringBuilder();
            String[] words = name.split("-");
            for (int i = 0; i < words.length; i++) {
                String word = Strings.ucfirst(words[i]);
                capName.append(word);
            }
            Class<?> type = eventTypes.get(name);
            System.out.println("case \"" + name + "\": eventListeners.set" + capName + "EventListeners( ("
                    + type.getSimpleName() + "[]) listeners);break; ");
        }
    }

}
