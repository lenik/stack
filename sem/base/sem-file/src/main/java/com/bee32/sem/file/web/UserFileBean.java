package com.bee32.sem.file.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.util.UserFileCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.web.ChoosePrincipalDialogListener;

@ForEntity(UserFile.class)
public class UserFileBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    String namePattern;
    UserFileTagnameDto selectedTag;

    public UserFileBean() {
        super(UserFile.class, UserFileDto.class, UserFileDto.TAGS);
    }

    public void createUserFile(IncomingFile incomingFile) {
        File localFile;
        try {
            localFile = incomingFile.saveToLocalFile();
        } catch (IOException e) {
            uiLogger.error("传输文件失败: " + incomingFile.getFileName(), e);
            return;
        }

        FileBlob fileBlob;
        try {
            fileBlob = FileBlob.commit(localFile, true);
        } catch (IOException e) {
            uiLogger.error("无法将接收的文件保存至存储系统", e);
            return;
        }

        // assert fileBlob.getLength() == incomingFile.getSize();
        try {
            fileBlob.setContentType(incomingFile.getContentType());
            asFor(FileBlob.class).saveOrUpdate(fileBlob);

            UserFile userFile = new UserFile();
            userFile.setName(incomingFile.getFileName());
            userFile.setFileBlob(fileBlob);
            asFor(UserFile.class).saveOrUpdate(userFile);
        } catch (Exception e) {
            uiLogger.error("无法保存文件基本信息", e);
            return;
        }
        uiLogger.info("保存文件成功。");
    }

    public String getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public void addNamedLikeRestriction() {
        addSearchFragment("名称含有 " + namePattern, UserFileCriteria.namedLike(namePattern));
    }

    public void addOwnerRestriction(PrincipalDto owner) {
    }

    public void addTag(UserFileTagnameDto tag) {
        UserFileDto userFile = getActiveObject();
        userFile.getTags().add(tag);
    }

    public void removeTag() {
        UserFileDto userFile = getActiveObject();
        List<UserFileTagnameDto> tags = userFile.getTags();
        tags.remove(selectedTag);
    }

    public UserFileTagnameDto getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(UserFileTagnameDto selectedTag) {
        this.selectedTag = selectedTag;
    }

    public Object getCreateUserFileAdapter() {
        return new UploadFileBlobDialogListener() {
            @Override
            protected void process(List<IncomingFile> incomingFiles)
                    throws IOException {
                for (IncomingFile incomingFile : incomingFiles)
                    createUserFile(incomingFile);
            }
        };
    }

    public Object getAddOwnerRestrictionAdapter() {
        return new ChoosePrincipalDialogListener() {
            @Override
            protected void process(List<?> selection) {
                for (Object item : selection)
                    addOwnerRestriction((PrincipalDto) item);
            }
        };
    }

    public Object getAddTagAdapter() {
        return new ChooseUserFileTagDialogListener() {
            @Override
            protected void process(List<?> selection) {
                for (Object item : selection)
                    addTag((UserFileTagnameDto) item);
            }
        };
    }

}
