package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;

import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;

public class UserFileUploadDialogBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

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
        } catch (Exception e) {
            uiLogger.error("远程文件保存失败:" + fileName, e);
            return;
        }
    }

}
