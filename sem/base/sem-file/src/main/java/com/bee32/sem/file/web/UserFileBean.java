package com.bee32.sem.file.web;

import java.util.List;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.misc.SimpleEntityViewBean;

@ForEntity(UserFile.class)
public class UserFileBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    UserFileTagnameDto selectedTag;

    public UserFileBean() {
        super(UserFile.class, UserFileDto.class, UserFileDto.TAGS);
    }

    public Object getChooseTagDialogListener() {
        return new ChooseUserFileTagDialogListener() {
            @Override
            protected void select(List<?> selection) {
                for (Object item : selection)
                    addTag((UserFileTagnameDto) item);
            }
        };
    }

    public void addTag(UserFileTagnameDto tag) {
        UserFileDto userFile = getActiveObject();
        userFile.getTags().add(tag);
    }

    public void removeTag() {
        UserFileDto userFile = getActiveObject();
        userFile.getTags().remove(selectedTag);
    }

    public UserFileTagnameDto getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(UserFileTagnameDto selectedTag) {
        this.selectedTag = selectedTag;
    }

}
