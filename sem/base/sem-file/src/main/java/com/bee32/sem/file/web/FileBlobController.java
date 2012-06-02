package com.bee32.sem.file.web;

import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.sem.file.SEMFileModule;
import com.bee32.sem.file.dto.FileBlobDto;
import com.bee32.sem.file.entity.FileBlob;

@RequestMapping(FileBlobController.PREFIX + "/*")
public class FileBlobController
        extends BasicEntityController<FileBlob, String, FileBlobDto> {

    public static final String PREFIX = SEMFileModule.PREFIX + "/blob";

    @Override
    protected void initController()
            throws BeansException {
        super.initController();

        addHandler("view", /*       */new FileBlobViewHandler(entityType));
        addHandler("download", /*   */new FileBlobViewHandler(entityType));
    }

    @Override
    protected void fillDataRow(DataTableDxo tab, FileBlobDto dto) {
        tab.push(dto.getDigest());
        tab.push(dto.getLabel());
        tab.push(dto.getCreatedDate());
        tab.push(dto.getLastModified());
    }

}
