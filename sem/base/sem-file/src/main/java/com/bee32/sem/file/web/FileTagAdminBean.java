package com.bee32.sem.file.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(UserFileTagname.class)
public class FileTagAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public FileTagAdminBean() {
        super(UserFileTagname.class, UserFileTagnameDto.class, 0);
    }

}
