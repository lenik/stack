package com.bee32.icsf.login;

import java.util.Collection;

import javax.free.UnexpectedException;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.access.AbstractEntityProcessor;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;
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

    static class ctx
            extends DefaultDataAssembledContext {
    }

    @Override
    public Collection<String> getInterestingEvents() {
        return listOf("save-update");
    }

    public static User getContextUser() {
        UserDto me = SessionUser.getInstance().getUserOpt();

        if (me == null || me.isNull()) {
            // Not logged-in yet, fallback to admin.
            Users users = ctx.bean.getBean(Users.class);
            return users.admin;
        }

        Integer userId = me.getId();
        if (userId == null)
            throw new UnexpectedException("Session user doesn't have an ID ?");

        User _user = ctx.data.getRef(User.class, userId);
        return _user;
    }

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event)
            throws HibernateException {
        Object obj = event.getObject();
        if (obj instanceof CEntity<?>) {
            CEntity<?> c = (CEntity<?>) obj;
            injectOwner(c);
        }
    }

    void injectOwner(CEntity<?> entity) {
        User owner = entity.getOwner();

        // Ignore if the owner is already specified.
        if (owner != null)
            return;

        // EntityFlags ef = EntityAccessor.getFlags(owner); ...?
        User newOwner = getContextUser();
        if (newOwner == null)
            return;

        if (newOwner.getId() == null) {
            if (newOwner != entity) {
                logger.warn("Create C-Entity without context user: " + entity);
                return;
            }
        }

        if (logger.isTraceEnabled())
            logger.trace("Set owner of new entity: " + newOwner.getName());

        CEntityAccessor.setOwner(entity, newOwner);
    }

}
