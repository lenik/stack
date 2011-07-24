package com.bee32.sem.file.dto;

import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.file.entity.UserFileTagname;

public class UserFileTagnameDto
        extends UIEntityDto<UserFileTagname, Long> {

    private static final long serialVersionUID = 1L;

    UserDto owner;
    String tag;

    public UserFileTagnameDto() {
        super();
    }

    public UserFileTagnameDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(UserFileTagname source) {
        owner = mref(UserDto.class, source.getOwner());
        tag = source.getTag();
    }

    @Override
    protected void _unmarshalTo(UserFileTagname target) {
        merge(target, "owner", owner);
        target.setTag(tag);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        tag = map.getString(tag);
    }

    @Override
    protected Boolean naturalEquals(EntityDto<UserFileTagname, Long> other) {
        UserFileTagnameDto o = (UserFileTagnameDto) other;

        if (!owner.equals(o.owner))
            return false;

        if (!tag.equals(o.tag))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += owner.hashCode();
        hash += tag.hashCode();
        return hash;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        if (owner == null)
            throw new NullPointerException("owner");
        this.owner = owner;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        if (tag == null)
            throw new NullPointerException("tag");
        this.tag = tag;
    }

}
