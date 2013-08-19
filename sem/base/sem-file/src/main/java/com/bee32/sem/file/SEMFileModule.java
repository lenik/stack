package com.bee32.sem.file;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.entity.UserFileTagname;
import com.bee32.sem.file.entity.UserFolder;
import com.bee32.sem.module.EnterpriseModule;

/**
 * SEM 文件模块
 *
 * <p lang="en">
 * SEM File Module
 */
@Oid({ 3, 15, SEMOids.Base, SEMOids.base.File })
public class SEMFileModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/1/6";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(UserFile.class, "file");
        declareEntityPages(UserFileTagname.class, "fileTag");
        declareEntityPages(UserFolder.class, "folder");
    }

}
