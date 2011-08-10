package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;
import javax.free.TempFile;

import org.primefaces.component.selectmanycheckbox.SelectManyCheckbox;
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

@Component
@Scope("view")
public class UserFileAdmin
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final String CHECKBOX_TAGS = "main:checkbox_tagId";

    List<String> selectedTags;
    List<UserFileDto> userFileList;
    Long uploadTag;
    String fileSubject;
    String fileName;

    public UserFileAdmin() {
        initUserFile();
    }

    public void addMessage() {
        SelectManyCheckbox checkboxes = (SelectManyCheckbox) findComponent(CHECKBOX_TAGS);
        List<UIComponent> children = checkboxes.getChildren();
// UISelectItem all = (UISelectItem) children.get(0);
// UISelectItems otherTags = (UISelectItems) children.get(1);
        String s1 = "";
        for (UIComponent c : children) {
            s1 += c.getClass().toString();
        }

        int childCount = checkboxes.getChildCount();
        String s = "";
        for (String l : selectedTags) {
            s += l;
        }
        uiLogger.info(fileName + fileSubject);
    }

    public void initUserFile() {
        List<UserFile> userFiles = serviceFor(UserFile.class).list(UserFileCriteria.ownerByCurrentUser());
        userFileList = DTOs.marshalList(UserFileDto.class, userFiles);
    }

    public List<SelectItem> getUserFileTags() {
        List<UserFileTagname> tags = serviceFor(UserFileTagname.class).list(UserFileCriteria.ownerByCurrentUser());
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (UserFileTagname tag : tags)
            items.add(new SelectItem(tag.getId(), tag.getTag()));
        return items;
    }

    public void handleFileUpload(FileUploadEvent event) {

// if (uploadTag == null) {
// uiLogger.error("请选择附件分类!");
// return;
// }
        uiLogger.info(fileSubject + fileName + uploadTag);

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

}
