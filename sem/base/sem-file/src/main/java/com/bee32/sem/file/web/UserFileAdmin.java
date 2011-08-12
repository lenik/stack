package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;
import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.file.util.UserFileCriteria;
import com.bee32.sem.misc.EntityCriteria;

@Component
@Scope("view")
public class UserFileAdmin
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final String CHECKBOX_TAGS = "main:checkbox_tagId";

    List<String> selectedTags;
    List<UserFileDto> userFileList;
    UserFileDto activeFile = new UserFileDto().create();
    Long uploadTag;
    String fileSubject;
    String fileName;
    int activeIndex;

    public UserFileAdmin() {
        initUserFile();
    }

    public void addMessage() {
        List<Long> idList = new ArrayList<Long>();
        for (String tagId : selectedTags)
            idList.add(Long.parseLong(tagId));
        List<UserFile> files = serviceFor(UserFile.class).list(UserFileCriteria.withAnyTagIn(idList));
        userFileList = DTOs.marshalList(UserFileDto.class, UserFileDto.ACTIVETAG, files);
    }

    public void initUserFile() {
        List<UserFile> userFiles = serviceFor(UserFile.class).list(EntityCriteria.ownedByCurrentUser());
        userFileList = DTOs.marshalList(UserFileDto.class, UserFileDto.ACTIVETAG, userFiles);
    }

    public void editUserFile() {
        UserFileTagname uft = serviceFor(UserFileTagname.class).getOrFail(activeFile.getActiveTag().getId());
        Set<UserFileTagname> set = new HashSet<UserFileTagname>(Arrays.asList(uft));
        UserFile file = activeFile.unmarshal();
        file.setTags(set);
        try {
            serviceFor(UserFile.class).saveOrUpdate(file);
            uiLogger.info("编辑附件" + activeFile.getFileName() + "成功");
        } catch (Exception e) {
            uiLogger.error("编辑附件失败:" + e.getMessage(), e);
        }
        userFileList.set(activeIndex, activeFile);
    }

    public void removeFile() {
        try {
            serviceFor(UserFile.class).deleteById(activeFile.getId());
            uiLogger.info("删除附件:" + activeFile.getFileName() + "成功");
            userFileList.remove(activeFile);
        } catch (Exception e) {
            uiLogger.error("删除附件失败:" + e.getMessage(), e);
        }
    }

    public List<SelectItem> getUserFileTags() {
        List<UserFileTagname> tags = serviceFor(UserFileTagname.class).list(EntityCriteria.ownedByCurrentUser());
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (UserFileTagname tag : tags)
            items.add(new SelectItem(tag.getId(), tag.getTag()));
        return items;
    }

    public void handleFileUpload(FileUploadEvent event) {

        String fileName = event.getFile().getFileName();
        UploadedFile upFile = event.getFile();

        File tempFile;
        try {
            tempFile = TempFile.createTempFile();
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(upFile.getContents());
            fileOutputStream.close();
        } catch (Exception e) {
            uiLogger.error("传送失败:" + fileName, e);
            return;
        }

        UserFile userFile = new UserFile();
        userFile.setOrigPath(upFile.getFileName());

        User currUser = (User) SessionLoginInfo.getUser();
        userFile.setOwnerId(currUser.getId());

        try {
            FileBlob fileBlob = FileBlob.commit(tempFile, true);

            userFile.setFileBlob(fileBlob);
            userFile.setFileName("未命名");
            userFile.setSubject("无标题");

            serviceFor(FileBlob.class).saveOrUpdate(fileBlob);
            serviceFor(UserFile.class).save(userFile);

        } catch (Exception e) {
            uiLogger.error("远程文件保存失败:" + fileName, e);
            return;
        }

        userFileList.add(DTOs.marshal(UserFileDto.class, userFile));

        uiLogger.info("上传成功:" + fileName);
    }

    public List<String> getSelectedTags() {
        return selectedTags;
    }

    public List<UserFileDto> getUserFileList() {
        return userFileList;
    }

    public Long getUploadTag() {
        return uploadTag;
    }

    public String getFileSubject() {
        return fileSubject;
    }

    public String getFileName() {
        return fileName;
    }

    public void setSelectedTags(List<String> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public void setUserFileList(List<UserFileDto> userFileList) {
        this.userFileList = userFileList;
    }

    public void setUploadTag(Long uploadTag) {
        this.uploadTag = uploadTag;
    }

    public void setFileSubject(String fileSubject) {
        this.fileSubject = fileSubject;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UserFileDto getActiveFile() {
        return activeFile;
    }

    public void setActiveFile(UserFileDto activeFile) {
        activeIndex = userFileList.indexOf(activeFile);
        this.activeFile = reload(activeFile);
    }

}
