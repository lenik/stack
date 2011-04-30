package com.bee32.icsf.principal.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.arch.util.Mask32;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;

public class AbstractPrincipalDto<E extends Principal>
        extends EntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    public static final int EXT = 1;
    public static final int USERS = 2;
    public static final int ROLES = 4;
    public static final int GROUPS = 8;
    public static final int IMPS = 16;

    public static final Mask32 DEPTH_MASK = new Mask32(0xff000000);

    public static int depthOf(int depth) {
        return DEPTH_MASK.compose(depth);
    }

    protected final int depth = depthOf(selection.bits);

    String name;
    String fullName;
    String description;

    public AbstractPrincipalDto() {
        super();
    }

    public AbstractPrincipalDto(E source) {
        super(source);
    }

    public AbstractPrincipalDto(int selection) {
        super(selection);
    }

    public AbstractPrincipalDto(int selection, E source) {
        super(selection, source);
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

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        this.name = map.getString("name");
        this.fullName = map.getString("fullName");
        this.description = map.getString("description");
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

}
