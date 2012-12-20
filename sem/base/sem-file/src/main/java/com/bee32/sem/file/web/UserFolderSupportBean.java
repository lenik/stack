package com.bee32.sem.file.web;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.dto.UserFolderDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class UserFolderSupportBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected final UserFolderTreeModel folderTree;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> UserFolderSupportBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
        folderTree = createFolderTree();
    }

    protected UserFolderTreeModel createFolderTree() {
        return new UserFolderTreeModel();
    }

    public UserFolderTreeModel getFolderTree() {
        return folderTree;
    }

    protected void onCreateFile(UserFileDto file) {
        Integer folderId = file.getFolder().getId();
        UserFolderDto folder = folderTree.getIndex().get(folderId);
        if (folder != null)
            folder.setFileCount(folder.getFileCount() + 1);
    }

    protected void onDeleteFile(UserFileDto file) {
        Integer folderId = file.getFolder().getId();
        UserFolderDto folder = folderTree.getIndex().get(folderId);
        if (folder != null)
            folder.setFileCount(folder.getFileCount() - 1);
    }

}
