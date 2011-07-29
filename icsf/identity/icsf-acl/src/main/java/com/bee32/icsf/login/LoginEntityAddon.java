package com.bee32.icsf.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.AbstractEntityLifecycleAddon;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;

public class LoginEntityAddon
        extends AbstractEntityLifecycleAddon {

    static Logger logger = LoggerFactory.getLogger(LoginEntityAddon.class);

    static final User dummy = new User("dummy");
    static {
        EntityAccessor.setId(dummy, -1);
    }

    User getCurrentUser() {
        User user = (User) SessionLoginInfo.getUserOpt();
        if (user != null) {
            assert user.getId() != null;
            return user;
        }

        if (User.admin == null)
            return dummy;

        Integer adminId = User.admin.getId();
        if (adminId == null)
            return dummy;

        return User.admin;
    }

    @Override
    public void entityCreate(Entity<?> entity) {
        super.entityCreate(entity);

        // Ignore if the owner is already specified.
        if (entity.getOwnerId() != 0)
            return;

        User owner = getCurrentUser();
        logger.debug("Set owner of new entity: " + owner.getDisplayName());
        entity.setOwnerId(owner.getId());
    }

}
