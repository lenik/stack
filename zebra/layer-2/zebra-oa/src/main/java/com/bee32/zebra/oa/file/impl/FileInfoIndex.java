package com.bee32.zebra.oa.file.impl;

import net.bodz.bas.meta.decl.ObjectType;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 描述文件、档案的相关信息。
 * 
 * @label 文件/档案
 * 
 * @rel tag/?tagv=5: 管理文件标签
 * @rel att/?schema=9: 管理文件属性
 * @rel org/: 管理企、事业组织
 * @rel person/: 管理联系人
 */
@ObjectType(FileInfo.class)
public class FileInfoIndex
        extends QuickIndex<FileInfo> {

}
