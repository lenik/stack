package com.bee32.icsf.access.shield;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.free.JavaioFile;
import javax.free.Order;
import javax.free.SystemProperties;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bee32.icsf.access.AccessControlException;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.alt.R_ACLService;
import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityResource;
import com.bee32.plover.orm.entity.EntityResourceNS;
import com.bee32.plover.site.scope.PerSite;

@Service
@Primary
@PerSite
@Order(0)
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

    static boolean enabled = true;
    static {
        File home = new File(SystemProperties.getUserHome());
        File aclFile = new File(home, ".data/acl");
        if (aclFile.exists()) {
            try {
                String s = new JavaioFile(aclFile).forRead().readTextContents().trim();
                if ("0".equals(s))
                    enabled = false;
            } catch (IOException e) {
            }
        }
    }

    @Override
    protected void require(int permissionBits) {
        if (!enabled || aclService == null)
            return;

        Permission requiredPermission = new Permission(permissionBits);

        User currentUser = SessionUser.getInstance().getInternalUserOpt();
        if (currentUser == null)
            // currentUser = User.ANONYMOUS;
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
                    entityResource.getAppearance().getDisplayName() + "(" + entityType + ")", // /
                    requiredPermission, //
                    grantedPermission);
            throw new AccessControlException(message);
        }
    }

}
