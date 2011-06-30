package com.bee32.icsf.access.shield;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bee32.icsf.access.AccessControlException;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.alt.R_ACLService;
import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityResource;
import com.bee32.plover.orm.entity.EntityResourceNS;
import com.bee32.plover.servlet.util.ThreadHttpContext;

@Service
@Primary
/* @Lazy */@Scope("prototype")
public class AclEasTxWrapper<E extends Entity<? extends K>, K extends Serializable>
        extends EasTxWrapperCat<E, K>
        implements InitializingBean {

    static Logger logger = LoggerFactory.getLogger(AclEasTxWrapper.class);

    @Inject
    ScannedResourceRegistry registry;

    @Inject
    R_ACLService aclService;

    IResourceNamespace entityNS;

    public AclEasTxWrapper() {
        super();
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        entityNS = registry.getNamespace(EntityResourceNS.NS);
    }

    @Override
    protected void require(int bits) {
        Permission requiredPermission = new Permission(bits);

        IUserPrincipal currentUser = null;

        HttpSession session = ThreadHttpContext.getSession();
        if (session != null) {
            currentUser = SessionLoginInfo.getCurrentUser(session);
            // if (currentUser ==null)
            // currentUser = User.ANONYMOUS;
        }

        if (currentUser == null)
            return;

        Class<? extends E> entityType = getEntityType();
        String entityResName = EntityResource.getEntityName(entityType);
        Resource entityResource = entityNS.getResource(entityResName);

        if (entityResource == null) {
            logger.warn("Unknown entity resource: " + entityType);
            return;
        }

        Permission grantedPermission = aclService.getPermission(entityResource, currentUser);

        if (!grantedPermission.implies(requiredPermission)) {
            String message = String.format(//
                    "User %s has insufficient permission to resource %s. " + //
                            "Required permission is %s, but user has granted %s", //
                    currentUser.getDisplayName(), //
                    entityResource.getAppearance().getDisplayName(), // /
                    requiredPermission, //
                    grantedPermission);
            throw new AccessControlException(message);
        }
    }

}
