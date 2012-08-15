package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;

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
            uiLogger.error("传送文件 " + fileName + " 失败", e);
            return;
        }

        UserFile userFile = new UserFile();
        userFile.setPath(upFile.getFileName());

        try {
            FileBlob fileBlob = FileBlob.commit(tempFile, true);

            userFile.setFileBlob(fileBlob);

            DATA(FileBlob.class).saveOrUpdate(fileBlob);
            DATA(UserFile.class).save(userFile);

        } catch (Exception e) {
            uiLogger.error("存储文件 " + fileName + " 失败", e);
            return;
        }

        uploadedFiles.add(userFile);

        uiLogger.info("上传文件 " + fileName + " 成功");
    }

    public List<UserFile> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<UserFile> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

}
