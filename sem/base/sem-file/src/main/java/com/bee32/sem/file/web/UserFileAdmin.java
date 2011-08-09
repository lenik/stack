package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.file.util.UserFileCriteria;

public class UserFileAdmin
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    List<Long> selectedTags;
    List<UserFileDto> userFileList;

    public UserFileAdmin() {
        initUserFile();
    }

    public void initUserFile() {
        List<UserFile> userFiles = serviceFor(UserFile.class).list(UserFileCriteria.ownerByCurrentUser());
        userFileList = DTOs.marshalList(UserFileDto.class, userFiles);
    }

    public List<SelectItem> getUserFileTags() {
        List<UserFileTagname> tags = serviceFor(UserFileTagname.class).list(UserFileCriteria.ownerByCurrentUser());
        //TODO
        return null;
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

            serviceFor(FileBlob.class).saveOrUpdate(fileBlob);
            serviceFor(UserFile.class).save(userFile);

        } catch (Exception e) {
            uiLogger.error("远程文件保存失败:" + fileName, e);
            return;
        }

        userFileList.add(DTOs.marshal(UserFileDto.class, userFile));

        uiLogger.info("上传成功:" + fileName);
    }

    public List<UserFileDto> getUserFileList() {
        return userFileList;
    }

    public void setUserFileList(List<UserFileDto> userFileList) {
        this.userFileList = userFileList;
    }

}
