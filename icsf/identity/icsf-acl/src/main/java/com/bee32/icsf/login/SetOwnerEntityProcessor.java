package com.bee32.icsf.login;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.config.AbstractEntityProcessor;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.c.CEntityAccessor;

/**
 * Inject current logon user to entity.owner.
 */
public class SetOwnerEntityProcessor
        extends AbstractEntityProcessor
        implements SaveOrUpdateEventListener {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(SetOwnerEntityProcessor.class);

    @Override
    public Collection<?> getEventListeners() {
        return listOf(this);
    }

    public static User getContextUser() {
        User user = SessionUser.getInstance().getInternalUserOpt();
        if (user != null)
            return user;
        else
            return User.admin;
    }

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event)
            throws HibernateException {
        Object entity = event.getEntity();
        if (entity instanceof CEntity<?>) {
            CEntity<?> c = (CEntity<?>) entity;
            injectOwner(c);
        }
    }

    void injectOwner(CEntity<?> entity) {
        User owner = entity.getOwner();

        // Ignore if the owner is already specified.
        if (owner != null)
            return;

        // EntityFlags ef = EntityAccessor.getFlags(owner); ...?
        owner = getContextUser();
        if (owner == null)
            return;

        if (logger.isTraceEnabled())
            logger.trace("Set owner of new entity: " + owner.getName());

        CEntityAccessor.setOwner(entity, owner);
    }

}
