package com.bee32.icsf.access.shield;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
import com.bee32.icsf.access.DefaultPermission;
import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.UnauthorizedAccessException;
import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLEntry;
import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACLService;
import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.icsf.principal.UserOption;
import com.bee32.icsf.principal.UserPreference;
import com.bee32.icsf.principal.Users;
import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.spring.Prototype;
import com.bee32.plover.orm.builtin.PloverConf;
import com.bee32.plover.orm.config.SiteSessionFactoryBean;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityResource;
import com.bee32.plover.orm.entity.EntityResourceNS;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.meta.EntityColumn;
import com.bee32.plover.ox1.meta.EntityInfo;
import com.bee32.plover.site.scope.PerSite;

/**
 * ACL??????????????????????????????????????????
 *
 * ????????????????????????????????? ACL ?????????
 */
@Service
@PerSite
@Primary
@Prototype
@Order(0)
public class AclEasTxWrapper<E extends Entity<? extends K>, K extends Serializable>
        extends EasTxWrapperCat<E, K>
        implements InitializingBean {

    static Logger logger = LoggerFactory.getLogger(AclEasTxWrapper.class);

    static boolean ACL_DISABLED = false;
    static {
        File home = new File(SystemProperties.getUserHome());
        File aclFile = new File(home, ".data/acl");
        if (aclFile.exists()) {
            try {
                String s = new JavaioFile(aclFile).forRead().readTextContents().trim();
                if ("0".equals(s))
                    ACL_DISABLED = true;
            } catch (IOException e) {
            }
        }
    }

    @Inject
    ScannedResourceRegistry registry;
    @Inject
    R_ACLService aclService;
    @Inject
    Users users;

    IResourceNamespace entityNS;

    public AclEasTxWrapper() {
        super();
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        entityNS = registry.getNamespace(EntityResourceNS.NS);
    }

    public static Map<Class<?>, Permission> defaults = new HashMap<>();
    static final Permission R_X = new Permission(Permission.R_X);
    static final Permission RWX = new Permission(Permission.RWX);
    static final Permission NULL_PERM = R_X;

    static {
        defaults.put(PloverConf.class, R_X);
        defaults.put(EntityInfo.class, R_X);
        defaults.put(EntityColumn.class, R_X);

        defaults.put(Principal.class, R_X);
        defaults.put(User.class, R_X);
        defaults.put(Role.class, R_X);
        defaults.put(Group.class, R_X);
        defaults.put(UserOption.class, RWX);
        defaults.put(UserPreference.class, RWX);

        defaults.put(UserPassword.class, RWX);
        defaults.put(ACL.class, RWX);
        defaults.put(ACLEntry.class, RWX);
        defaults.put(R_ACE.class, R_X);

        PersistenceUnit unit = SiteSessionFactoryBean.getForceUnit();
        for (Class<?> entityType : unit.getClasses()) {
            DefaultPermission _defaultPermission = entityType.getAnnotation(DefaultPermission.class);
            if (_defaultPermission != null) {
                Permission defaultPermission = new Permission(_defaultPermission.value());
                defaults.put(entityType, defaultPermission);
            }
        }
    }

    @Override
    protected void require(Class<? extends Entity<?>> entityType, Collection<? extends E> entities,
            Permission requiredPermission) {
        if (aclService == null)
            return;
        if (ACL_DISABLED) // global disabled
            return;

        AclOptionManager aclOptionManager = AclOptionManager.getInstance();
        AclOptionStack aclOptionStack = aclOptionManager.getAclOptionStack();
        AclOption aclOption = aclOptionStack.peek();
        if (!aclOption.isEnabled())
            return;

        // Damn.. could be proxy classes..??
        entityType = (Class<? extends Entity<?>>) ClassUtil.skipProxies(entityType);

        Permission defaultPerm = defaults.get(entityType);
        boolean readOnly = requiredPermission.getAllowBits() == Permission.READ;
        if (readOnly && defaultPerm != null && defaultPerm.isReadable())
            return;

        UserDto currentUser = SessionUser.getInstance().getUserOpt();
        if (currentUser == null || currentUser.isNull())
            // currentUser = User.ANONYMOUS;
            return;

        Set<Integer> imset = SessionUser.getInstance().getImIdSet();
        Integer adminRoleId = users.adminRole.getId();
        if (imset.contains(adminRoleId))
            return;

        PersistenceUnit unit = SiteSessionFactoryBean.getForceUnit();
        Map<Class<?>, ClassCatalog> invMap = unit.getInvMap();
        ClassCatalog catalog = invMap.get(entityType);
        if (catalog == null)
            throw new IllegalStateException("Catalog unknown for entity type: " + entityType);

        String entityResName = EntityResource.getLocalName(catalog, entityType);
        Resource entityResource = entityNS.getResource(entityResName);

        if (entityResource == null) {
            logger.warn("Unknown entity resource: " + entityType);
            return;
        }

        Permission grantedPerm = aclService.getPermission(entityResource, imset);
        if (defaultPerm != null)
            grantedPerm = grantedPerm.add(defaultPerm);

        if (!grantedPerm.implies(requiredPermission)) {
            String message = String.format(//
                    "User %s has insufficient permission to resource %s. " + //
                            "Required permission is %s, but user has granted %s", //
                    currentUser.getDisplayName(), //
                    entityResource.getAppearance().getDisplayName() + "(" + entityType + ")", // /
                    requiredPermission, //
                    grantedPerm);
            throw new UnauthorizedAccessException(message, entityType, requiredPermission);
        }

        if (entities == null)
            return;

        // ?????????????????????

        // ?????????????????????????????????????????????
        if (grantedPerm.isAdmin())
            return;

        Set<Integer> effectiveAcls = SessionUser.getInstance().getACLs(requiredPermission);
        for (E entity : entities)
            if (entity instanceof CEntity<?>) {
                CEntity<?> ce = (CEntity<?>) entity;
                User owner = ce.getOwner();
                String ownerName = owner == null ? "???????????????" : owner.getDisplayName();
                Integer acl = ce.getAclId();

                // ???????????????ACL, ??????????????????????????????????????????????????????
                if (owner == null || imset.contains(owner.getId()))
                    continue;

                // ??????????????????ACL?????????????????????null ????????? r--, ??? NULL_PERM.
                if (acl == null) {
                    if (NULL_PERM.implies(requiredPermission))
                        continue;
                }

                if (effectiveAcls.contains(acl))
                    continue;

                String message = String.format(//
                        // "User %s can't access %s. " + //
                        // "ACL rejected, required permission is %s", //
                        "????????????????????????(?????? %s)????????? %s ??????????????? %s - %s???" + //
                                "????????????????????? %s ??????????????????????????????????????????????????????", //
                        acl == null ? "???????????????" : acl, //
                        currentUser.getDisplayName(), //
                        ClassUtil.getParameterizedTypeName(entity) + ": " + ce.getEntryLabel(), //
                        requiredPermission.getReadableString(), //
                        ownerName);
                AccessControlException e = new AccessControlException(message);
                e.setResourceType(entityType);
                e.setRequiredPermission(requiredPermission);
                throw e;
            }
    }

}
