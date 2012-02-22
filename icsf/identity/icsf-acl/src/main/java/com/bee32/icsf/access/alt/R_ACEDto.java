package com.bee32.icsf.access.alt;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public class R_ACEDto
        extends EntityDto<R_ACE, Long> {

    private static final long serialVersionUID = 1L;

    Resource resource;
    PrincipalDto principal;
    Permission permission;

    @Override
    protected void _marshal(R_ACE source) {
        resource = source.getResource();
        principal = mref(PrincipalDto.class, source.getPrincipal());
        permission = source.getPermission().clone();
    }

    @Override
    protected void _unmarshalTo(R_ACE target) {
        target.setResource(resource);
        merge(target, "principal", principal);
        target.setPermission(permission.clone());
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        if (resource == null)
            throw new NullPointerException("resource");
        this.resource = resource;
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

}
