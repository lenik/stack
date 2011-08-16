package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
import com.bee32.sem.file.dto.UserFileTagnameDto;
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

    List<SelectItem> userFileTags;
    /* checkbox ids */
    List<String> selectedTags;
    List<SelectItem> selectedTagItems = new ArrayList<SelectItem>();
    List<UserFileDto> userFileList;
    UserFileDto activeFile = new UserFileDto().create();
    String fileSubject;
    String fileName;
    int activeIndex;
    List<String> selectedTagsToAdd;
    String activeTagId;
    UserFileTagnameDto activeTag = new UserFileTagnameDto().create();
    UserFileTagnameDto newTag = new UserFileTagnameDto().create();

    public UserFileAdmin() {
        initUserFile();
        refreshTags();
    }

    public void setActiveFile() {
        // XXX
    }

    public void refreshTags() {
        List<UserFileTagname> tags = serviceFor(UserFileTagname.class).list(EntityCriteria.ownedByCurrentUser());
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (UserFileTagname tag : tags)
            items.add(new SelectItem(tag.getId(), tag.getTag()));
        userFileTags = items;
    }

    public void doSelectTags() {

        List<SelectItem> temp = activeFile.getTagItems();
        for(SelectItem item : temp){
            item.getValue();
        }
        List<SelectItem> itemList = new ArrayList<SelectItem>();
        for (String tagId : selectedTagsToAdd) {
            UserFileTagname tagename = serviceFor(UserFileTagname.class).getOrFail(Long.parseLong(tagId));
            UserFileTagnameDto dto = DTOs.marshal(UserFileTagnameDto.class, tagename);
            itemList.add(new SelectItem(dto.getId(), dto.getTag()));
        }
        activeFile.setTagItems(itemList);
    }

    public void removeActiveTag() {
        int tempIndex = 0;
        for (SelectItem item : selectedTagItems) {
            long tagId = (Long) item.getValue();
            long tempId = Long.parseLong(activeTagId);
            if (tagId == tempId)
                tempIndex = selectedTagItems.indexOf(item);
        }
        selectedTagItems.remove(tempIndex);
    }

    public void createNewTag() {
        newTag = new UserFileTagnameDto().create();
    }

    public void deleteTag() {
        try {
            serviceFor(UserFileTagname.class).deleteById(activeTag.getId());
            refreshTags();
            uiLogger.info("删除标签成功!");
        } catch (Exception e) {
            uiLogger.error("删除标签失败:" + e.getMessage(), e);
        }
    }

    public void saveOrUpdateTag() {
        UserFileTagname tag = newTag.unmarshal();
        try {
            serviceFor(UserFileTagname.class).saveOrUpdate(tag);
            SelectItem item = new SelectItem(tag.getId(), newTag.getTag());
            userFileTags.add(item);
            uiLogger.info("保存标签成功!");
        } catch (Exception e) {
            uiLogger.error("保存标签失败:" + e.getMessage(), e);
        }
    }

    public void addMessage() {
        List<Long> idList = new ArrayList<Long>();
        for (String tagId : selectedTags)
            idList.add(Long.parseLong(tagId));
        List<UserFile> files = serviceFor(UserFile.class).list(UserFileCriteria.withAnyTagIn(idList));
        userFileList = DTOs.marshalList(UserFileDto.class, UserFileDto.TAGS, files);
    }

    public void initUserFile() {
        List<UserFile> userFiles = serviceFor(UserFile.class).list(EntityCriteria.ownedByCurrentUser());
        userFileList = DTOs.marshalList(UserFileDto.class, UserFileDto.TAGS, userFiles);
    }

    public void editUserFile() {

        Set<UserFileTagname> set = new HashSet<UserFileTagname>();
        for (SelectItem item : selectedTagItems)
            set.add(serviceFor(UserFileTagname.class).getOrFail((Long) item.getValue()));

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

    public UserFileTagnameDto getActiveTag() {
        return activeTag;
    }

    public void setActiveTag(UserFileTagnameDto activeTag) {
        this.activeTag = activeTag;
    }

    public List<String> getSelectedTagsToAdd() {
        return selectedTagsToAdd;
    }

    public void setSelectedTagsToAdd(List<String> selectedTagsToAdd) {
        this.selectedTagsToAdd = selectedTagsToAdd;
    }

    public List<SelectItem> getUserFileTags() {
        return userFileTags;
    }

    public UserFileTagnameDto getNewTag() {
        return newTag;
    }

    public void setNewTag(UserFileTagnameDto newTag) {
        this.newTag = newTag;
    }

    public List<SelectItem> getSelectedTagItems() {
        return selectedTagItems;
    }

    public void setSelectedTagItems(List<SelectItem> selectedTagItems) {
        this.selectedTagItems = selectedTagItems;
    }

    public String getActiveTagId() {
        return activeTagId;
    }

    public void setActiveTagId(String acitveTagId) {
        this.activeTagId = acitveTagId;
    }

}
