package com.bee32.sem.file.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.Identity;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.file.entity.UserFileTagname;

public class UserFileTagnameDto
        extends UIEntityDto<UserFileTagname, Integer> {

    private static final long serialVersionUID = 1L;

    String name;

    public UserFileTagnameDto() {
        super();
    }

    public UserFileTagnameDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(UserFileTagname source) {
        name = source.getName();
    }

    @Override
    protected void _unmarshalTo(UserFileTagname target) {
        target.setName(name);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        name = map.getString("name");
    }

    @NLength(max = UserFileTagname.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = TextUtil.normalizeSpace(name);
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new Identity(this);
        return name;
    }

}
