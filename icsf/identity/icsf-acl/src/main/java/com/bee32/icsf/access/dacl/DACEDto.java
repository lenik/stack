package com.bee32.icsf.access.dacl;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.dto.AbstractPrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;

public class DACEDto
        extends EntityDto<DACE, Long> {

    private static final long serialVersionUID = 1L;

    DACLDto dacl;
    AbstractPrincipalDto<?> principal;
    Permission permission;

    public DACEDto() {
        super();
    }

    public DACEDto(int selection) {
        super(selection);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void _marshal(DACE source) {
        dacl = new DACLDto().ref(source.getDacl());
        principal = mref(AbstractPrincipalDto.class, 0, source.getPrincipal());
        permission = source.getPermission();
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
        throw new NotImplementedException();
    }

}
