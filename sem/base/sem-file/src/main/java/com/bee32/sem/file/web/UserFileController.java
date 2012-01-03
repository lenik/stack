package com.bee32.sem.file.web;

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
        tab.push(dto.getName());
        tab.push(dto.getLabel());
        tab.push(dto.getCreatedDate());
        tab.push(dto.getLastModified());
    }

}
