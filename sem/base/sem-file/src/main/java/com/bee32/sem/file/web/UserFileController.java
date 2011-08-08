package com.bee32.sem.file.web;

import java.io.File;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.sem.file.SEMFileModule;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.UserFile;

@RequestMapping(UserFileController.PREFIX + "/*")
public class UserFileController
        extends BasicEntityController<UserFile, Long, UserFileDto> {

    public static final String PREFIX = SEMFileModule.PREFIX + "/file";

    @Override
    protected void initController()
            throws BeansException {
        super.initController();

        addHandler("view", /*       */new UserFileViewHandler(entityType));
        addHandler("download", /*   */new UserFileViewHandler(entityType));
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, UserFileDto dto) {
        tab.push(dto.getFileName());
        tab.push(dto.getSubject());
        tab.push(dto.getCreatedDate());
        tab.push(dto.getLastModified());
    }

    void upload(File home) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(10240000); // 设置内存缓冲区，超过后写入临时文件
        factory.setRepository(home);
        ServletFileUpload upload = new ServletFileUpload(factory);
    }

}
