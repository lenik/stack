package com.bee32.sem.file.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.file.dto.UserFolderDto;
import com.bee32.sem.file.entity.UserFolder;
import com.bee32.sem.misc.TreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(UserFolder.class)
public class UserFolderAdminBean
        extends TreeEntityViewBean {

    private static final long serialVersionUID = 1L;


    public UserFolderAdminBean() {
        super(UserFolder.class, UserFolderDto.class, 0);
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        super.postUpdate(uMap);
        BEAN(ChooseUserFolderDialogBean.class).refreshTree();
    }
}
