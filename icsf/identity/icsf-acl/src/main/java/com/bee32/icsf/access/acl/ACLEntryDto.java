package com.bee32.icsf.access.acl;

import java.util.Map;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.validation.NotNullId;

public class ACLEntryDto
        extends EntityDto<ACLEntry, Long> {

    private static final long serialVersionUID = 1L;

    ACLDto acl;
    PrincipalDto principal;
    Permission permission;

    public ACLEntryDto() {
        super();
    }

    public ACLEntryDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(ACLEntry source) {
        acl = mref(ACLDto.class, source.getACL());
        principal = mref(PrincipalDto.class, source.getPrincipal());
        permission = source.getPermission().clone();
    }

    @Override
    protected void _unmarshalTo(ACLEntry target) {
        merge(target, "ACL", acl);
        merge(target, "principal", principal);
        target.setPermission(permission);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        int aclId = map.getInt("acl.id");
        acl = new ACLDto().ref(aclId);

        int principalId = map.getInt("principal.id");
        principal = new PrincipalDto().ref(principalId);

        String mode = map.getString("permission");
        permission = new Permission(mode);
    }

    @Override
    protected void _export(Map<String, Object> map) {
        map.put("acl.id", acl.getId());
        map.put("principal.id", principal.getId());
        map.put("permission", permission.getModeString());
    }

    @NotNullId
    public ACLDto getACL() {
        return acl;
    }

    public void setACL(ACLDto acl) {
        if (acl == null)
            throw new NullPointerException("acl");
        this.acl = acl;
    }

    @NotNullId
    public PrincipalDto getPrincipal() {
        return principal;
    }

    public void setPrincipal(PrincipalDto principal) {
        this.principal = principal;
    }

    @NotNull
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        if (permission == null)
            throw new NullPointerException("permission");
        this.permission = permission;
    }

}
