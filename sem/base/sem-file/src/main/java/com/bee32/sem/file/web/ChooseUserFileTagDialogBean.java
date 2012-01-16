package com.bee32.sem.file.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseUserFileTagDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseUserFileTagDialogBean.class);

    String caption = "Please choose a user file tag..."; // NLS: 选择用户或组

    public ChooseUserFileTagDialogBean() {
        super(UserFileTagname.class, UserFileTagnameDto.class, 0);
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
