package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;
import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.lang.Strings;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.dto.UserFileTagnameDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.file.util.UserFileCriteria;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

@Component
@Scope("view")
public class UserFileAdmin
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    List<SelectItem> userFileTags;
    /* checkbox ids */
    List<String> selectedTags;
    List<SelectItem> selectedTagItems = new ArrayList<SelectItem>();
    LazyDataModel<UserFileDto> userFiles;

    UserFileDto activeFile = new UserFileDto().create();
    int activeIndex;
    List<String> selectedTagsToAdd;
    String activeTagId;
    UserFileTagnameDto activeTag = new UserFileTagnameDto().create();
    UserFileTagnameDto newTag = new UserFileTagnameDto().create();

    String filePattern;
    int selectedUserId = 0;

    public UserFileAdmin() {
        refreshList();
        refreshCount();
        refreshTags();
    }


    public void refreshList() {
        List<Long> idList = new ArrayList<Long>();
        boolean attachment = false;
        if (selectedTags != null) {
            for (String tagId : selectedTags) {
                long id = Long.parseLong(tagId);
                if (id > 0)
                    idList.add(id);
                else
                    attachment = true;
            }
        }

        EntityDataModelOptions<UserFile, UserFileDto> options = new EntityDataModelOptions<UserFile, UserFileDto>(//
                UserFile.class, UserFileDto.class, UserFileDto.TAGS, //
                UserFileCriteria.patternMatch(filePattern),
                UserFileCriteria.switchOwner(selectedUserId),//
                UserFileCriteria.withAnyTagIn(idList),//
                UserFileCriteria.isAttachment(attachment),
                EntityCriteria.ownedByCurrentUser(),
                Order.desc("lastModified"));
        userFiles = UIHelper.<UserFile, UserFileDto> buildLazyDataModel(options);

        refreshCount();
    }

    void refreshCount() {
        List<Long> idList = new ArrayList<Long>();
        boolean attachment = false;
        if (selectedTags != null) {
            for (String tagId : selectedTags) {
                long id = Long.parseLong(tagId);
                if (id > 0)
                    idList.add(id);
                else
                    attachment = true;
            }
        }

        int count = serviceFor(UserFile.class).count(UserFileCriteria.patternMatch(filePattern),
                UserFileCriteria.switchOwner(selectedUserId),//
                UserFileCriteria.withAnyTagIn(idList),//
                UserFileCriteria.isAttachment(attachment),
                EntityCriteria.ownedByCurrentUser());
        userFiles.setRowCount(count);
    }

    public void refreshTags() {
        List<UserFileTagname> tags = serviceFor(UserFileTagname.class).list(EntityCriteria.ownedByCurrentUser());
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (UserFileTagname tag : tags)
            items.add(new SelectItem(tag.getId(), tag.getName()));
        userFileTags = items;
    }

    public void doSelectTags() {
        Iterator<SelectItem> iterator = activeFile.getTagItems().iterator();
        while (iterator.hasNext()) {
            SelectItem item = iterator.next();
            String tempTagId = Long.toString((Long) item.getValue());
            if (selectedTagsToAdd.contains(tempTagId)) {
                selectedTagsToAdd.remove(tempTagId);
            }
        }

        List<SelectItem> itemList = new ArrayList<SelectItem>();
        for (String tagId : selectedTagsToAdd) {
            UserFileTagname tagename = serviceFor(UserFileTagname.class).getOrFail(Long.parseLong(tagId));
            UserFileTagnameDto dto = DTOs.marshal(UserFileTagnameDto.class, tagename);
            itemList.add(new SelectItem(dto.getId(), dto.getName()));
        }
        activeFile.addTagItems(itemList);
    }

    public void removeActiveTag() {
        if (!Strings.isEmpty(activeTagId)) {
            int tempIndex = 0;
            List<SelectItem> items = activeFile.getTagItems();
            for (SelectItem item : items) {
                long tagId = (Long) item.getValue();
                long tempId = Long.parseLong(activeTagId);
                if (tagId == tempId) {
                    tempIndex = items.indexOf(item);
                    activeFile.removeTagItem(tempIndex);
                    break;
                }
            }
            activeTagId = null;
        }
    }

    public void createNewTag() {
        newTag = new UserFileTagnameDto().create();
    }

    public void deleteTag() {
        if (activeTag.getId() == null) {
            uiLogger.warn("请选择要删除的标签!");
            return;
        }
        try {
            serviceFor(UserFileTagname.class).deleteById(activeTag.getId());
            refreshTags();
            uiLogger.info("删除标签成功!");
        } catch (Exception e) {
            uiLogger.error("删除标签失败:" + e.getMessage(), e);
        }
    }

    /** save UserFileTagname it's uneditable */
    public void saveOrUpdateTag() {
        UserFileTagname tag = newTag.unmarshal();
        try {
            serviceFor(UserFileTagname.class).saveOrUpdate(tag);
            SelectItem item = new SelectItem(tag.getId(), newTag.getName());
            userFileTags.add(item);
            uiLogger.info("保存标签成功!");
        } catch (Exception e) {
            uiLogger.error("保存标签失败:" + e.getMessage(), e);
        }
    }

    public void addMessage() {

    }

    public void editUserFile() {
        Set<UserFileTagname> set = new HashSet<UserFileTagname>();
        List<SelectItem> items = activeFile.getTagItems();
        for (SelectItem item : items)
            set.add(serviceFor(UserFileTagname.class).getOrFail((Long) item.getValue()));

        UserFile file = activeFile.unmarshal();
        file.setTags(set);

        String fileName = activeFile.getPrefixName() + activeFile.getExtensionName();
        file.setName(fileName);
        try {
            serviceFor(UserFile.class).saveOrUpdate(file);
            uiLogger.info("编辑附件" + activeFile.getName() + "成功");
        } catch (Exception e) {
            uiLogger.error("编辑附件失败:" + e.getMessage(), e);
        }
    }

    public void removeFile() {

        if (activeFile == null) {
            uiLogger.error("没有选中文件.");
            return;
        }

        try {
            serviceFor(UserFile.class).deleteById(activeFile.getId());
            uiLogger.info("删除附件:" + activeFile.getName() + "成功");
            refreshCount();
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
        userFile.setPath(upFile.getFileName());

        try {
            FileBlob fileBlob = FileBlob.commit(tempFile, true);

            userFile.setFileBlob(fileBlob);
            userFile.setLabel("无标题");

            serviceFor(FileBlob.class).saveOrUpdate(fileBlob);
            serviceFor(UserFile.class).save(userFile);

            uiLogger.info("上传成功:" + fileName);
            refreshCount();

        } catch (Exception e) {
            uiLogger.error("远程文件保存失败:" + fileName, e);
            return;
        }
    }

    public List<SelectItem> getUsersWithFile() {
        List<SelectItem> selectItemList = new ArrayList<SelectItem>();
        List<User> users = serviceFor(User.class).list();
        List<UserDto> userDtos = DTOs.marshalList(UserDto.class, users);
        for (UserDto dto : userDtos) {
            SelectItem item = new SelectItem(dto.getId(), dto.getDisplayName());
            selectItemList.add(item);
        }
        return selectItemList;
    }

    public List<String> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<String> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public UserFileDto getActiveFile() {
        return activeFile;
    }

    public void setActiveFile(UserFileDto activeFile) {
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
        this.selectedTagsToAdd = new ArrayList<String>(selectedTagsToAdd);
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

    public String getFilePattern() {
        return filePattern;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern.trim();
    }

    public int getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public LazyDataModel<UserFileDto> getUserFiles() {
        return userFiles;
    }

    public void setUserFiles(LazyDataModel<UserFileDto> userFiles) {
        this.userFiles = userFiles;
    }
}
