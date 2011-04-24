package com.bee32.icsf.principal.dto;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class PrincipalDto<E extends Principal>
        extends EntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    public static final int USERS = 1;
    public static final int ROLES = 2;
    public static final int GROUPS = 4;
    public static final int IMPS = 8;

    String name;
    String fullName;
    String description;

    public PrincipalDto() {
        super();
    }

    public PrincipalDto(E source) {
        super(source);
    }

    public PrincipalDto(int selection) {
        super(selection);
    }

    public PrincipalDto(E source, int selection) {
        super(source, selection);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDisplayName() {
        return fullName != null ? fullName : name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected void _marshal(E source) {
        name = source.getName();
        fullName = source.getFullName();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, E target) {
        target.setName(name);
        target.setFullName(fullName);
        target.setDescription(description);
    }

}
