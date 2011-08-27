package com.bee32.sem.file.dto;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.file.entity.UserFileTagname;

public class UserFileTagnameDto
        extends UIEntityDto<UserFileTagname, Long> {

    private static final long serialVersionUID = 1L;

    String tag;

    public UserFileTagnameDto() {
        super();
    }

    public UserFileTagnameDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(UserFileTagname source) {
        tag = source.getTag();
    }

    @Override
    protected void _unmarshalTo(UserFileTagname target) {
        target.setTag(tag);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        tag = map.getString(tag);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        if (tag == null)
            throw new NullPointerException("tag");
        this.tag = tag;
    }

    @Override
    protected Serializable naturalId() {
        if (tag == null)
            return new DummyId(this);
        return tag;
    }

}
