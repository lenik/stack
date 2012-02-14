package com.bee32.icsf.principal;

import java.util.Locale;

import javax.free.ParseException;
import javax.free.TypeConvertException;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.arch.util.Mask32;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
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
    }

    @Override
    protected void _unmarshalTo(Principal target) {
        target.setName(name);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        this.name = map.getString("name");
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

    @NLength(min = 2, max = Principal.LABEL_LENGTH)
    public String getFullName() {
        return getLabel();
    }

    public void setFullName(String fullName) {
        setLabel(fullName);
    }

    public String getDisplayName() {
        if (!StringUtils.isEmpty(getFullName()))
            return getFullName();
        else
            return getName();
    }

}
