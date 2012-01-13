package com.bee32.plover.orm.access;

import java.util.Collection;

import org.hibernate.event.PreDeleteEvent;
import org.hibernate.event.PreDeleteEventListener;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;

public class LockingEntityProcessor
        extends AbstractEntityProcessor
        implements PreUpdateEventListener, PreDeleteEventListener {

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<String> getInterestingEvents() {
        return listOf("pre-update", "pre-delete");
    }

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        Integer efIndex = getEfIndex(event.getPersister());
        if (efIndex == null)
            return false;

        int ef = (int) event.getDeletedState()[efIndex];
        EntityFlags oldFlags = new EntityFlags(ef);
        if (oldFlags.isLocked()) {
            Entity<?> entity = (Entity<?>) event.getEntity();
            EntityFlags flagsOverride = EntityAccessor.getFlags(entity);
            if (!flagsOverride.isUnlockReq())
                throw new EntityLockedException("Entity is locked", event.getEntity());
        }

        if (oldFlags.isUserLock())
            throw new EntityLockedException("Entity is locked by user", event.getEntity());

        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        System.err.println("Pre-Update: " + event.getEntity());
        Integer efIndex = getEfIndex(event.getPersister());
        if (efIndex == null)
            return false;

        // since "ef" exists, it must be a plover Entity.
        Entity<?> entity = (Entity<?>) event.getEntity();

        Object[] oldState = event.getOldState();
        int oldEf = oldState == null ? 0 : (int) oldState[efIndex];
        EntityFlags oldFlags = new EntityFlags(oldEf);
        EntityFlags newFlags = EntityAccessor.getFlags(entity);

        // Refresh entity lock by lock-predicates.
        if (EntityAccessor.isLocked(entity)) {
            newFlags.setLocked(true);
        }

        // the system lock must be unlocked first.
        if (oldFlags.isLocked()) {
            if (newFlags.isUnlockReq()) {
                // Process unlock-request.
                newFlags.setLocked(false);
                newFlags.setUnlockReq(false);
            } else {
                throw new EntityLockedException("Entity is locked", event.getEntity());
            }
        }

        // the user lock is changable by user.
        if (oldFlags.isUserLock() && newFlags.isUserLock())
            throw new EntityLockedException("Entity is locked by user", event.getEntity());

        /**
         * NOTICE: You should never write to newState[], which would cause twice updates.
         */
        return false;
    }

    static final String ENTITY_FLAGS_PROPERTY = "ef";

    static Integer getEfIndex(EntityPersister persister) {
        Integer propertyIndex = persister.getEntityMetamodel().getPropertyIndex(ENTITY_FLAGS_PROPERTY);
        return propertyIndex;
    }

    static String getEntityStr(Object obj) {
        if (obj instanceof Entity<?>) {
            Entity<?> entity = (Entity<?>) obj;
            return entity.getEntryLabel();
        } else
            return String.valueOf(obj);
    }

}
