package com.bee32.sem.file.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.free.TempFile;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;

@Component
@Scope("view")
public class FileUploadBean extends EntityViewBean {

	private static final long serialVersionUID = 1L;

	public void handleFileUpload(FileUploadEvent event) {
	FacesMessage msg = null;

		UploadedFile upFile = event.getFile();
		File tempFile;
		try {
			tempFile = TempFile.createTempFile();
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
			fileOutputStream.write(upFile.getContents());
			fileOutputStream.close();

			FileBlob fileBlob = FileBlob.commit(tempFile, true);

			UserFile userFile = new UserFile();
			userFile.setFileBlob(fileBlob);
			userFile.setOrigPath(upFile.getFileName());

			User currUser = (User) SessionLoginInfo.requireCurrentUser();
			userFile.setOwner(currUser);

			serviceFor(FileBlob.class).saveOrUpdate(fileBlob);
			serviceFor(UserFile.class).save(userFile);

			msg = new FacesMessage("上传成功", event.getFile().getFileName() + "上传成功");

		} catch (IOException e) {
			msg = new FacesMessage("上传失败", event.getFile().getFileName() + "上传失败");
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
