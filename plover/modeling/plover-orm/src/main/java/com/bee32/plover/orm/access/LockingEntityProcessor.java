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
        EntityFlags flags = new EntityFlags(ef);
        if (flags.isLocked())
            throw new EntityLockedException("Entity is locked", event.getEntity());
        if (flags.isUserLock())
            throw new EntityLockedException("Entity is locked by user", event.getEntity());

        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        Integer efIndex = getEfIndex(event.getPersister());
        if (efIndex == null)
            return false;

        Object[] oldState = event.getOldState();
        Object[] newState = event.getState();
        int oldEf = oldState == null ? 0 : (int) oldState[efIndex];
        int newEf = newState == null ? 0 : (int) newState[efIndex];
        EntityFlags oldFlags = new EntityFlags(oldEf);
        EntityFlags newFlags = new EntityFlags(newEf);

        Object entity = event.getEntity();
        if (entity instanceof Entity<?>) {
            // Refresh entity lock by lock-predicates.
            Entity<?> en = (Entity<?>) entity;
            if (EntityAccessor.isLocked(en))
                newFlags.setLocked(true);
        }

        // the system lock must be unlocked first.
        if (oldFlags.isLocked()) {
            if (newFlags.isUnlockReq()) {
                newFlags.setLocked(false);
                newFlags.setUnlockReq(false);
            } else {
                throw new EntityLockedException("Entity is locked", event.getEntity());
            }
        }

        // the user lock is changable by user.
        if (oldFlags.isUserLock() && newFlags.isUserLock())
            throw new EntityLockedException("Entity is locked by user", event.getEntity());

        newState[efIndex] = newFlags.bits;
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
