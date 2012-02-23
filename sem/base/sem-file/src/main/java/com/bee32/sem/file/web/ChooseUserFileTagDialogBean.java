package com.bee32.sem.file.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseUserFileTagDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseUserFileTagDialogBean.class);

    String header = "Please choose a user file tag..."; // NLS: 选择用户或组

    public ChooseUserFileTagDialogBean() {
        super(UserFileTagname.class, UserFileTagnameDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
