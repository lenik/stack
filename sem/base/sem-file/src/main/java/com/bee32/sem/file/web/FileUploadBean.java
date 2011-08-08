package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;

@Scope("view")
public class FileUploadBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    public List<UserFile> uploadedFiles = new ArrayList<UserFile>();

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

        uploadedFiles.add(userFile);

        uiLogger.info("上传成功:" + fileName);
    }

    public List<UserFile> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<UserFile> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

}
