package com.bee32.icsf.principal;

import java.util.Locale;

import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.validation.constraints.Size;

import com.bee32.plover.arch.util.Mask32;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.tree.TreeEntityDto;

public class PrincipalDto
        extends TreeEntityDto<Principal, Integer, PrincipalDto> {

    private static final long serialVersionUID = 1L;

    // 800000, 400000 used by TreeEntityDto.
    public static final int IMPS = 0x200000;

    public static final Mask32 DEPTH_MASK = new Mask32(0x000F0000);

    public static int depthOf(int depth) {
        return DEPTH_MASK.compose(depth);
    }

    protected final int depth = DEPTH_MASK.extract(selection.bits);

    String name;
    String fullName;
    String description;

    public PrincipalDto() {
        super();
    }

    public PrincipalDto(int fmask) {
        super(fmask);
    }

    @Override
    protected boolean isUniqueChildren() {
        return true;
    }

    @Override
    protected void _marshal(Principal source) {
        name = source.getName();
        fullName = source.getFullName();
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(Principal target) {
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

    @Size(min = 3, max = Principal.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null)
            name = name.toLowerCase(Locale.ROOT);
        this.name = name;
    }

    @Size(min = 2, max = Principal.FULLNAME_LENGTH)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDisplayName() {
        if (fullName != null && !fullName.isEmpty())
            return fullName;
        return getName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
