package com.bee32.zebra.oa.cloudfs.impl;

import com.bee32.zebra.tk.foo.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 
 * @label 云端目录
 * 
 * @rel tag/: 管理标签
 * 
 * @see <a href=""></a>
 * @see <a href=""></a>
 * @see <a href=""></a>
 */
public class CloudDirManager
        extends FooManager {

    CloudDirMapper mapper;

    public CloudDirManager() {
        mapper = VhostDataService.getInstance().getMapper(CloudDirMapper.class);
    }

    public CloudDirMapper getMapper() {
        return mapper;
    }

}
