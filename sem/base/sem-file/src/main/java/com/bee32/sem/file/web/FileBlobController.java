package com.bee32.sem.file.web;

import java.io.File;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.ext.util.BasicEntityController;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.sem.file.SEMFileModule;
import com.bee32.sem.file.blob.FileBlob;
import com.bee32.sem.file.dto.FileBlobDto;

@RequestMapping(FileBlobController.PREFIX + "/*")
public class FileBlobController
        extends BasicEntityController<FileBlob, String, FileBlobDto> {

    public static final String PREFIX = SEMFileModule.PREFIX + "/blob";

    void upload(File home) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(10240000); // 设置内存缓冲区，超过后写入临时文件
        factory.setRepository(home);
        ServletFileUpload upload = new ServletFileUpload(factory);
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, FileBlobDto dto) {
    }

}
