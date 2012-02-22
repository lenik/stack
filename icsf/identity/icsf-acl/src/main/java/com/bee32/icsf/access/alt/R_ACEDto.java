package com.bee32.icsf.access.alt;

import java.beans.Transient;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.resource.AccessPoint;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.EntityResource;
import com.bee32.plover.orm.util.EntityDto;

public class R_ACEDto
        extends EntityDto<R_ACE, Long> {

    private static final long serialVersionUID = 1L;

    String qualifiedName;
    PrincipalDto principal;
    Permission permission;

    @Override
    protected void _marshal(R_ACE source) {
        qualifiedName = source.getQualifiedName();
        principal = mref(PrincipalDto.class, source.getPrincipal());
        permission = source.getPermission().clone();
    }

    @Override
    protected void _unmarshalTo(R_ACE target) {
        target.setQualifiedName(qualifiedName);
        merge(target, "principal", principal);
        target.setPermission(permission.clone());
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    @NotNull
    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        if (qualifiedName == null)
            throw new NullPointerException("qualifiedName");
        this.qualifiedName = qualifiedName;
    }

    @Transient
    public Resource getResource() {
        if (qualifiedName == null)
            return null; // Unexpected.
        else
            return ResourceRegistry.query(qualifiedName);
    }

    public void setResource(Resource resource) {
        if (resource == null)
            throw new NullPointerException("resource");
        this.qualifiedName = ResourceRegistry.qualify(resource);
    }

    public PrincipalDto getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalDto principal) {
        if (principal == null)
            throw new NullPointerException("principal");
        this.principal = principal;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        this.permission = permission;
    }

    public String getCatalogName() {
        Resource resource = getResource();
        if (!(resource instanceof EntityResource))
            return null;
        EntityResource res = (EntityResource) resource;
        ClassCatalog catalog = res.getCatalog();
        String catalogName = catalog.getAppearance().getDisplayName();
        return catalogName;
    }

    public String getEntityName() {
        Resource resource = getResource();
        if (!(resource instanceof EntityResource))
            return null;
        EntityResource res = (EntityResource) resource;
        Class<?> entityClass = res.getEntityClass();
        if (entityClass == null)
            return null;
        String entityName = res.getAppearance().getDisplayName();
        return entityName;
    }

    public String getAccessPointName() {
        Resource resource = getResource();
        if (!(resource instanceof AccessPoint))
            return null;
        AccessPoint accessPoint = (AccessPoint) resource;
        String accessPointName = accessPoint.getAppearance().getDisplayName();
        return accessPointName;
    }

}
