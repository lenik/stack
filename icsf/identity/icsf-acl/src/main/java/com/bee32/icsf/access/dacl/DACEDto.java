package com.bee32.icsf.access.dacl;

import java.util.Map;

import javax.free.ParseException;

import com.bee32.icsf.access.Permission;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.ox1.principal.PrincipalDto;

public class DACEDto
        extends EntityDto<DACE, Long> {

    private static final long serialVersionUID = 1L;

    DACLDto dacl;
    PrincipalDto principal;
    Permission permission;

    public DACEDto() {
        super();
    }

    public DACEDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(DACE source) {
        dacl = new DACLDto().ref(source.getDacl());
        principal = mref(PrincipalDto.class, source.getPrincipal());
        permission = source.getPermission().clone();
    }

    @Override
    protected void _unmarshalTo(DACE target) {
        merge(target, "dacl", dacl);
        merge(target, "principal", principal);
        target.setPermission(permission);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        int daclId = map.getInt("dacl.id");
        dacl = new DACLDto().ref(daclId);

        int principalId = map.getInt("principal.id");
        principal = new PrincipalDto().ref(principalId);

        String mode = map.getString("permission");
        permission = new Permission(mode);
    }

    @Override
    protected void _export(Map<String, Object> map) {
        map.put("dacl.id", dacl.getId());
        map.put("principal.id", principal.getId());
        map.put("permission", permission.getModeString());
    }

    public Boolean getAdmin() {
        return permission.getAdmin();
    }

    public void setAdmin(Boolean f) {
        permission.setAdmin(f);
    }

    public Boolean getReadable() {
        return permission.getReadable();
    }

    public void setReadable(Boolean f) {
        permission.setReadable(f);
    }

    public Boolean getWritable() {
        return permission.getWritable();
    }

    public void setWritable(Boolean f) {
        permission.setWritable(f);
    }

    public Boolean getExecutable() {
        return permission.getExecutable();
    }

    public void setExecutable(Boolean f) {
        permission.setExecutable(f);
    }

    public Boolean getListable() {
        return permission.getListable();
    }

    public void setListable(Boolean f) {
        permission.setListable(f);
    }

    public Boolean getCreatable() {
        return permission.getCreatable();
    }

    public void setCreatable(Boolean f) {
        permission.setCreatable(f);
    }

    public Boolean getDeletable() {
        return permission.getDeletable();
    }

    public void setDeletable(Boolean f) {
        permission.setDeletable(f);
    }

}
