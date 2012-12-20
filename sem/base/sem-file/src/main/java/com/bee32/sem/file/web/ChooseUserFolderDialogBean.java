package com.bee32.sem.file.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.file.dto.UserFolderDto;
import com.bee32.sem.file.entity.UserFolder;
import com.bee32.sem.misc.ChooseTreeEntityDialogBean;

public class ChooseUserFolderDialogBean
        extends ChooseTreeEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseUserFolderDialogBean.class);

    public ChooseUserFolderDialogBean() {
        super(UserFolder.class, UserFolderDto.class, 0);
    }

}
