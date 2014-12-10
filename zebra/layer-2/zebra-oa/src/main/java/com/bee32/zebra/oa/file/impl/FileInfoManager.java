package com.bee32.zebra.oa.file.impl;

import com.bee32.zebra.tk.sql.VhostDataService;
import com.tinylily.repr.CoEntityManager;

/**
 * TITLE
 * 
 * @label LABEL
 * 
 * @rel HREF1: TEXT1
 * 
 * @see <a href="HREF2">TEXT2</a>
 */
public class FileInfoManager
        extends CoEntityManager {

    FileInfoMapper mapper;

    public FileInfoManager() {
        mapper = VhostDataService.getInstance().getMapper(FileInfoMapper.class);
    }

    public FileInfoMapper getMapper() {
        return mapper;
    }

}
