package com.bee32.zebra.oa.file.impl;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 描述文件、档案的相关信息。
 * 
 * @label 文件/档案
 * 
 * @rel tag/?schema=9: 管理文件标签
 * @rel att/?schema=9: 管理文件属性
 * @rel org/: 管理企、事业组织
 * @rel person/: 管理联系人
 */
public class FileInfoManager
        extends FooManager {

    FileInfoMapper mapper;

    public FileInfoManager() {
        super(FileInfo.class);
        mapper = VhostDataService.getInstance().getMapper(FileInfoMapper.class);
    }

    public FileInfoMapper getMapper() {
        return mapper;
    }

}
